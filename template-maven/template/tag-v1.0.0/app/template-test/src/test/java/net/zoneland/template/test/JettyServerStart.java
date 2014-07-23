/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.template.test;

import net.zoneland.mvc.runtime.core.jetty.JettyServer;

/**
 * 
 * @author gag
 * @version $Id: JettyTestServer.java, v 0.1 2012-4-26 обнГ9:19:14 gag Exp $
 */
public class JettyServerStart extends JettyServer {

    public static void main(String[] args) throws Exception {
        JettyServer jetty = new JettyServer();
        jetty.start();
    }
}
