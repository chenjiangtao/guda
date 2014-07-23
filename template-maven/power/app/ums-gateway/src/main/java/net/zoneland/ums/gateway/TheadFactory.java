/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package net.zoneland.ums.gateway;

import java.util.concurrent.atomic.AtomicInteger;

import net.zoneland.ums.common.util.enums.GateEnum;
import net.zoneland.ums.gateway.http.facade.SendThread;

/**
 *
 * @author gang
 * @version $Id: TheadFactory.java, v 0.1 2013-4-12 上午10:30:02 gang Exp $
 */
public class TheadFactory {

    private static AtomicInteger sgipTheadCount = new AtomicInteger(0);

    private static AtomicInteger smgpTheadCount = new AtomicInteger(0);

    public static final int      maxCount       = 15;

    public static SendThread newSendThread(MessageFactory messageFactory, Message msg,
                                           GateEnum gateEnum) {
        if (GateEnum.SGIP == gateEnum) {
            if (sgipTheadCount.get() > maxCount) {
                return null;
            } else {
                sgipTheadCount.addAndGet(1);
                return new SendThread(messageFactory, msg);
            }
        } else {
            if (smgpTheadCount.get() > maxCount) {
                return null;
            } else {
                smgpTheadCount.addAndGet(1);
                return new SendThread(messageFactory, msg);
            }
        }
    }

    public static void releaseThread(GateEnum gateEnum) {
        if (GateEnum.SGIP == gateEnum) {
            sgipTheadCount.decrementAndGet();
        } else {
            smgpTheadCount.decrementAndGet();
        }
    }

}
