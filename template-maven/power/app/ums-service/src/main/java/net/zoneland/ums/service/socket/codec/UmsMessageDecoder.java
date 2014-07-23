/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.service.socket.codec;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import net.zoneland.ums.biz.msg.socket.ServiceRequest;
import net.zoneland.ums.common.util.Counter;
import net.zoneland.ums.service.socket.RequestHandler;
import net.zoneland.ums.service.socket.rule.RequestParserHelper;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.demux.MessageDecoder;
import org.apache.mina.filter.codec.demux.MessageDecoderResult;

/**
 * 
 * @author gag
 * @version $Id: UmsMessageDecoder.java, v 0.1 2012-10-15 下午2:11:32 gag Exp $
 */
public class UmsMessageDecoder implements MessageDecoder {
    private static final Logger  logger  = Logger.getLogger(RequestHandler.class);

    private static final Counter counter = new Counter("socket");

    private final Charset        charset;

    public UmsMessageDecoder(String charset) {
        this.charset = Charset.forName(charset);
    }

    /** 
     * @see org.apache.mina.filter.codec.demux.MessageDecoder#decodable(org.apache.mina.core.session.IoSession, org.apache.mina.core.buffer.IoBuffer)
     */
    public MessageDecoderResult decodable(IoSession session, IoBuffer in) {
        if (in.remaining() < 5) {
            return MessageDecoderResult.NEED_DATA;
        }

        byte[] bLeng = new byte[5];
        in.get(bLeng);
        String msg = new String(bLeng);
        int len = getLen(msg);
        if (len == -1 || len > 20000) {
            InetSocketAddress clientsa = (InetSocketAddress) session.getRemoteAddress();
            logger.error("data error." + clientsa.getHostName());
            return MessageDecoderResult.NOT_OK;
        }
        if (in.remaining() >= len) {
            return MessageDecoderResult.OK;
        }
        return MessageDecoderResult.NEED_DATA;

    }

    private int getLen(String msg) {
        if (msg == null) {
            return 0;
        }
        msg = msg.trim();
        int len = -1;
        try {
            len = Integer.parseInt(msg);
        } catch (Exception e) {

        }
        return len;
    }

    /** 
     * @see org.apache.mina.filter.codec.demux.MessageDecoder#decode(org.apache.mina.core.session.IoSession, org.apache.mina.core.buffer.IoBuffer, org.apache.mina.filter.codec.ProtocolDecoderOutput)
     */
    public MessageDecoderResult decode(IoSession session, IoBuffer buf, ProtocolDecoderOutput out)
                                                                                                  throws Exception {

        byte[] bLeng = new byte[5];
        buf.get(bLeng);
        String msg = new String(bLeng);
        int len = getLen(msg);
        if (len == -1 || len > 10000) {
            InetSocketAddress clientsa = (InetSocketAddress) session.getRemoteAddress();
            logger.error("data error." + clientsa.getHostName());
            return MessageDecoderResult.NOT_OK;
        }
        if (len <= 4) {
            return MessageDecoderResult.NEED_DATA;
        }
        String msgCode = buf.getString(4, charset.newDecoder());
        byte[] body = new byte[len - 4];
        buf.get(body, 0, len - 4);
        DecodeResult dr = new DecodeResult();
        ServiceRequest sr = null;
        sr = RequestParserHelper.parseRequest(msgCode, body);
        if (sr == null) {
            return MessageDecoderResult.NOT_OK;
        }
        counter.count();

        try {
            InetSocketAddress clientsa = (InetSocketAddress) session.getRemoteAddress();
            InetSocketAddress serversa = (InetSocketAddress) session.getRemoteAddress();
            if (clientsa != null) {
                sr.setClientIp(clientsa.getHostName());
                sr.setClientPort(clientsa.getPort());
            }
            if (serversa != null) {
                sr.setServerPort(serversa.getPort());
            }
        } catch (Exception e) {

        }

        dr.setServiceRequest(sr);
        dr.setSuccess(true);
        out.write(dr);
        return MessageDecoderResult.OK;
    }

    /** 
     * @see org.apache.mina.filter.codec.demux.MessageDecoder#finishDecode(org.apache.mina.core.session.IoSession, org.apache.mina.filter.codec.ProtocolDecoderOutput)
     */
    public void finishDecode(IoSession session, ProtocolDecoderOutput out) throws Exception {
    }

}
