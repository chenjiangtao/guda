/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package net.zoneland.ums.gateway.cmpp;

import net.zoneland.ums.gateway.MethodTimeoutException;
import net.zoneland.ums.gateway.ThreadMonitor;

/**
 * 
 * @author gang
 * @version $Id: TestB.java, v 0.1 2013-2-20 下午1:21:43 gang Exp $
 */
public class TestB {

    public String ready() {
        ThreadMonitor m = new ThreadMonitor(Thread.currentThread());
        m.start();
        if (true)
            throw new MethodTimeoutException("cc");
        try {
            System.out.println("thread...sleep");
            Thread.sleep(5 * 1000);
            System.out.println("wake up thread...sleep");
        } catch (InterruptedException e) {
            throw new MethodTimeoutException("mm");
        }
        m.stop();
        return "abcccc";
    }

}
