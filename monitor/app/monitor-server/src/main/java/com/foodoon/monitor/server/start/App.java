/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.foodoon.monitor.server.start;

import java.io.File;
import java.io.IOException;

/**
 * 
 * @author gang
 * @version $Id: App.java, v 0.1 2013-4-22 上午9:52:28 gang Exp $
 */
public class App {

    public static String root_dir;

    public static String root_properties;

    public static void set() throws IOException {

        String dir = System.getProperty("user.dir");
        File f = new File(dir);
        //File pf = f.getParentFile();
        root_dir = f.getAbsolutePath();
        root_properties = root_dir + File.separator + "monitor-server.properties";

    }

}
