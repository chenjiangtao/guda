/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.util;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.log4j.Logger;
import org.springframework.jmx.export.annotation.ManagedOperation;

/**
 * 
 * @author gag
 * @version $Id: Counter.java, v 0.1 2012-10-11 下午6:19:09 gag Exp $
 */
public class Counter {

    private final static Logger logger         = Logger.getLogger("PERFORM");

    private String              control;

    private ControlElement      controlElement = new ControlElement();

    private static final int    threshold      = 1;

    private static final int    MAX_VALUE      = Integer.MAX_VALUE - 1000;

    public Counter(String name) {
        this.control = name;
    }

    public void count() {
        controlElement.addAndCheck();
    }

    class ControlElement {
        private long          startTime;
        private AtomicInteger count;
        private int           lastCount = 0;

        public ControlElement() {
            count = new AtomicInteger(0);
            startTime = System.currentTimeMillis();
        }

        public void addAndCheck() {
            if (System.currentTimeMillis() - startTime < threshold * 1000) {
                count.getAndIncrement();
            } else {
                startTime = System.currentTimeMillis();
                int tempcount = count.getAndIncrement();
                logger.warn("时间：" + startTime + "请求:[" + control + "],次数:["
                            + (tempcount - lastCount) + "],总数:" + tempcount);
                lastCount = tempcount;
                if (tempcount > MAX_VALUE) {
                    count.set(0);
                    lastCount = 0;
                }

            }
        }

        public String toString() {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
        }
    }

    /**
     * Getter method for property <tt>control</tt>.
     * 
     * @return property value of control
     */
    @ManagedOperation
    public String getControl() {
        return control;
    }

}
