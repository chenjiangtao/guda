/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.util;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 序列号产生帮助类
 * @author gag
 * @version $Id: SerialNoHelper.java, v 0.1 2012-8-24 下午1:38:54 gag Exp $
 */
public class SerialNoHelper {

    private static Random        random         = new Random();

    public static final int      MAX_NUMBER     = 1000000;

    private static AtomicInteger replyNO        = new AtomicInteger(0);

    private static AtomicInteger serialNO       = new AtomicInteger(0);

    private static int           serial         = random.nextInt(100) * MAX_NUMBER / 100;

    private static String        serial_pattern = String.valueOf(random.nextInt(10)) + "%07d";

    /**
     * 保证一定的时间段内，序列号是唯一的。长度为8位以下
     * 集群内同一个时间内产生相同序列的可能性为1/100*1/
     * @return
     */
    public static int nextSerial() {
        if (serialNO.get() < MAX_NUMBER) {
            return serial + serialNO.getAndIncrement();
        } else {
            serialNO.set(0);
            return serial + serialNO.getAndIncrement();
        }

    }

    /**
     * 产生9位随机序列号
     * @return
     */
    public static String nextReplyNo() {
        return String.format(serial_pattern, getReply());
    }

    private static int getReply() {
        if (replyNO.get() < 10000000) {
            return replyNO.getAndIncrement();
        } else {
            replyNO.set(0);
            return replyNO.getAndIncrement();
        }

    }

}
