/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.foodoon.monitor.agent.test;

/**
 * 
 * @author gang
 * @version $Id: P.java, v 0.1 2013-4-20 上午9:12:09 gang Exp $
 */
public class P {

    public static void set() {
        String sigar = "E:\\google-code\\foodoon\\foodoon-monitor\\deploy\\sigar-bin\\lib;";
        String str = System.getProperty("java.library.path");
        System.setProperty("java.library.path", str + sigar);

    }

}
