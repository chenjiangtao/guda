/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.foodoon.monitor.test;

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

    public static final int defaultJettyPort = 8080;

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

    protected WebAppContext createWebapp() {
        WebAppContext webapp = new WebAppContext();
        webapp.setDescriptor(getWebDescriptor());
        webapp.setResourceBase(getAppRoot() + "/htdocs");
        webapp.setContextPath("/");
        //webapp.setDefaultsDescriptor(getWebDefDescriptor());
        //HashUserRealm myrealm = null;
        //        try {
        //            myrealm = new HashUserRealm("MyRealm", getRealm());
        //        } catch (IOException e) {
        //            e.printStackTrace();
        //        }
        //        if (myrealm != null) {
        //            webapp.getSecurityHandler().setUserRealm(myrealm);
        //        }
        //  webapp.setParentLoaderPriority(true);
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
        return getAppRoot() + File.separatorChar + "assembly" + File.separatorChar + "config"
               + File.separatorChar + "web-temp.xml";
    }

    private String getWebDefDescriptor() {
        return getAppRoot() + File.separatorChar + "assembly" + File.separatorChar + "config"
               + File.separatorChar + "webdefault.xml";
    }

    private String getRealm() {
        return getAppRoot() + File.separatorChar + "assembly" + File.separatorChar + "config"
               + File.separatorChar + "realm.properties";
    }

    protected String getAppName() {
        return ConfigrationFactory.getConfigration().getAppName();
    }

    protected String getAppRoot() {
        return ConfigrationFactory.getConfigration().getAppRoot();
    }

    protected String getHtdocsRoot() {
        return ConfigrationFactory.getConfigration().getHtdocsRoot();
    }

}
