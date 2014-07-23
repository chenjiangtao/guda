/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.foodoon.monitor.server.start;

import java.io.File;

import net.zoneland.mvc.runtime.core.config.ConfigrationFactory;

import org.eclipse.jetty.ajp.Ajp13SocketConnector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * 
 * @author gag
 * @version $Id: JettyServer.java, v 0.1 2012-4-26 下午8:18:54 gag Exp $
 */
public class JettyServer {

    public static final int defaultJettyPort = 9090;

    private int             jettyPort        = defaultJettyPort;

    public void start() throws Exception {
        Server server = new Server(jettyPort);
        Ajp13SocketConnector c = new Ajp13SocketConnector();
        c.setPort(9091);
        server.addConnector(c);
        server.setHandler(createWebapp());
        server.start();
        server.join();
    }

    public void start(int httpPort, int ajpPort) throws Exception {
        if (httpPort == 0) {
            httpPort = defaultJettyPort;
        }
        Server server = new Server(httpPort);
        if (ajpPort > 0) {
            Ajp13SocketConnector c = new Ajp13SocketConnector();
            c.setPort(ajpPort);
            server.addConnector(c);
        }
        server.setHandler(createWebapp());
        server.start();
        server.join();
    }

    protected WebAppContext createWebapp() {
        WebAppContext webapp = new WebAppContext();
        System.out.println(getWebDescriptor());
        webapp.setDescriptor(getWebDescriptor());
        webapp.setResourceBase(App.root_dir);
        webapp.setContextPath("/");
        //webapp.setDefaultsDescriptor(getWebDefDescriptor());

        return webapp;
    }

    /**
     * Setter method for property <tt>jettyPort</tt>.
     * 
     * @param jettyPort value to be assigned to property jettyPort
     */
    public void setJettyPort(int jettyPort) {
        this.jettyPort = jettyPort;
    }

    private String getWebDescriptor() {
        return getAppRoot() + File.separatorChar + "WEB-INF" + File.separatorChar + "web.xml";
    }

    private String getWebDefDescriptor() {
        return getAppRoot() + File.separatorChar + "WEB-INF" + File.separatorChar
               + "webdefault.xml";
    }

    protected String getAppName() {
        return ConfigrationFactory.getConfigration().getAppName();
    }

    protected String getAppRoot() {
        return App.root_dir;
    }

    protected String getHtdocsRoot() {
        return ConfigrationFactory.getConfigration().getHtdocsRoot();
    }

}
