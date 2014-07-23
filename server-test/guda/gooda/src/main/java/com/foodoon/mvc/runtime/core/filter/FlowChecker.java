/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.foodoon.mvc.runtime.core.filter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.log4j.Logger;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.util.StringUtils;

/**
 * 流量检查，由于这里不需要太精确的数据，因此没有做同步处理，做到大致的流量控制即可
 * @author gag
 * @version $Id: FlowChecker.java, v 0.1 2012-5-26 下午9:10:08 gag Exp $
 */
@ManagedResource(objectName = "bean:name=flowChecker")
public class FlowChecker {

    private final static Logger         logger    = Logger.getLogger(FlowChecker.class);

    private String                      control;

    private Map<String, ControlElement> map       = new HashMap<String, ControlElement>();

    private static final int            threshold = 3;

    public boolean checker(String url) {
        ControlElement controlElement = map.get(url);
        if (controlElement == null) {
            controlElement = map.get("#");
        }
        if (controlElement == null) {
            return true;
        }
        return controlElement.addAndCheck();
    }

    class ControlElement {
        private long          startTime;
        private AtomicInteger count;
        private int           maxCount;

        public ControlElement(int maxCount) {
            this.maxCount = maxCount * threshold;
            count = new AtomicInteger(0);
            startTime = System.currentTimeMillis();
        }

        public boolean addAndCheck() {
            if (System.currentTimeMillis() - startTime < threshold * 1000) {
                if (count.getAndIncrement() > maxCount) {
                    return false;
                }
                return true;
            } else {
                int tempcount = count.getAndIncrement();
                startTime = System.currentTimeMillis();
                count.set(0);
                if (tempcount > maxCount) {
                    return false;
                } else {

                    return true;
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

    /**
     * Setter method for property <tt>control</tt>.
     * 
     * @param control value to be assigned to property control
     */
    @ManagedOperation(description = "设置流量阀值：例如：设置：'#,100'表示对所有url拦截，1秒最多100次请求;'/msg/msg.htm,100;/msg/b.htm,200'")
    public void setControl(String control) {
        this.control = control;
        map.clear();
        if (StringUtils.hasLength(control)) {
            String[] controls = control.split(";");
            for (int i = 0, len = controls.length; i < len; ++i) {
                if (controls[i] != null) {
                    String[] str = controls[i].split(",");
                    if (str.length == 2) {
                        int count = getInt(str[1]);
                        if (count > 0) {
                            String uri = str[0];
                            if (StringUtils.hasLength(uri)) {
                                if (!uri.startsWith("/")) {
                                    uri = "/" + uri;
                                }
                            }
                            map.put(uri, new ControlElement(count));
                            if (logger.isInfoEnabled()) {
                                logger.info("set url:[" + uri + "],count:[" + count + "]");
                            }
                        }
                    }
                }
            }
        }
    }

    private int getInt(String str) {
        if (str == null) {
            return 0;
        }
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {

        }
        return 0;
    }

}
