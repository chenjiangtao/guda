/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.service.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.filterchain.IoFilterChainBuilder;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.springframework.util.StringUtils;

/**
 * 
 * @author gang
 * @version $Id: MinaServer.java, v 0.1 2012-9-10 下午10:11:10 gang Exp $
 */
public class MinaServer {

    private static final Logger     logger             = Logger.getLogger(MinaServer.class);

    private IoHandler               handler;

    private IoFilterChainBuilder    filterChainBuilder = new DefaultIoFilterChainBuilder();

    private List<NioSocketAcceptor> acceptors          = new ArrayList<NioSocketAcceptor>();

    private String                  localAddress;

    public void bind() throws IOException {
        if (!StringUtils.hasText(localAddress)) {
            logger.warn("socket服务端口为空");
            return;
        }
        String[] addrs = localAddress.split(",");
        for (int i = 0, len = addrs.length; i < len; ++i) {
            String[] addr = addrs[i].split(":");
            InetSocketAddress a = null;
            if (addr.length == 1) {
                a = new InetSocketAddress(Integer.parseInt(addr[0]));
            } else if (addr.length == 2) {
                a = new InetSocketAddress(addr[0], Integer.parseInt(addr[1]));
            }
            NioSocketAcceptor acceptor = new NioSocketAcceptor(Runtime.getRuntime()
                .availableProcessors() * 10);
            acceptor.setHandler(handler);
            acceptor.getFilterChain().addLast("threadPool",
                new ExecutorFilter(Executors.newCachedThreadPool()));
            acceptor.setFilterChainBuilder(filterChainBuilder);

            acceptor.getSessionConfig().setReceiveBufferSize(100);
            acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 60);
            acceptor.getSessionConfig().setKeepAlive(false);
            acceptor.getSessionConfig().setTcpNoDelay(true);

            acceptor.bind(a);
            acceptors.add(acceptor);
            if (logger.isInfoEnabled()) {
                logger.info("初始化socket服务完成.[" + addrs[i] + "]");
            }
        }
    }

    public void unbind() {
        if (acceptors != null) {
            Iterator<NioSocketAcceptor> it = acceptors.iterator();
            while (it.hasNext()) {
                it.next().unbind();
            }
        }
    }

    public final void setFilterChainBuilder(IoFilterChainBuilder builder) {
        if (builder == null) {
            builder = new DefaultIoFilterChainBuilder();
        }
        filterChainBuilder = builder;
    }

    public void setHandler(IoHandler handler) {
        this.handler = handler;
    }

    public void setAcceptors(List<NioSocketAcceptor> acceptors) {
        this.acceptors = acceptors;
    }

    public void setLocalAddress(String localAddress) {
        this.localAddress = localAddress;
    }

}
