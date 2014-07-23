#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package ${package}.test;


import ${groupId}.mvc.runtime.core.jetty.JettyServer;

/**
 * 
 * @author gag
 * @version ${symbol_dollar}Id: JettyTestServer.java, v 0.1 2012-4-26 ����9:19:14 gag Exp ${symbol_dollar}
 */
public class JettyServerStart extends JettyServer {

    public static void main(String[] args) throws Exception {
        JettyServer jetty = new JettyServer();
        jetty.start();
    }
}
