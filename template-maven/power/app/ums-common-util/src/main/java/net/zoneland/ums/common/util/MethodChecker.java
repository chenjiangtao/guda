/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package net.zoneland.ums.common.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author gag
 * @version $Id: MethodChecker.java, v 0.1 2013-1-5 下午5:58:38 gag Exp $
 */
public class MethodChecker {

    private final static Logger                logger    = LoggerFactory.getLogger("GATEWAY-STAT");

    private static Map<String, ControlElement> map       = new HashMap<String, ControlElement>();

    private int                                threshold = 2;
    private final int                          interval  = threshold * 1000;

    public MethodChecker() {

    }

    public MethodChecker(int threshold) {
        this.threshold = threshold;
    }

    public void checker(String url, long time) {
        ControlElement controlElement = map.get(url);

        if (controlElement == null) {
            controlElement = new ControlElement();
            map.put(url, controlElement);
        }
        controlElement.addAndStat(url, time);
    }

    class ControlElement {
        private long                startTime;
        private final AtomicInteger count;
        private long                total = 0;
        private long                max   = 0;
        private long                min   = 0;

        public ControlElement() {

            count = new AtomicInteger(1);
            startTime = System.currentTimeMillis();
        }

        public void addAndStat(String url, long time) {
            if (System.currentTimeMillis() - startTime < interval) {
                count.getAndIncrement();
                total += time;
                if (time >= max) {
                    max = time;
                }
                if (min == 0) {
                    min = time;
                }
                if (time <= min) {
                    min = time;
                }
            } else {
                total += time;
                if (time >= max) {
                    max = time;
                }
                if (min == 0) {
                    min = time;
                }
                if (time <= min) {
                    min = time;
                }
                int tempcount = count.getAndIncrement();
                startTime = System.currentTimeMillis();
                count.set(0);
                StringBuilder buf = new StringBuilder();
                if (tempcount != 0) {
                    buf.append("id:[").append(url).append("],count[").append(tempcount)
                        .append("],avg:[").append(total / tempcount).append("],max:[").append(max)
                        .append("],min:[").append(min).append("]");
                } else {
                    buf.append("id:[").append(url).append("],count[").append(tempcount)
                        .append("],avg:[").append(total).append("],max:[").append(max)
                        .append("],min:[").append(min).append("]");
                }
                total = 0;
                logger.info(buf.toString());
            }
        }

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MethodChecker c = new MethodChecker();
        c.checker("123", 200);
        c.checker("124", 100);
        c.checker("124", 100);
        c.checker("123", 100);
        c.checker("123", 100);
        c.checker("123", 100);
        c.checker("123", 100);
        Thread.sleep(2 * 1000);
        c.checker("123", 300);
        c.checker("124", 105);

    }

}
