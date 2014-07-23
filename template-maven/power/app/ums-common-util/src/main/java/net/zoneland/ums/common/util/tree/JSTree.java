/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.util.tree;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * JSTree的辅助类
 * @author wangyong
 * @version $Id: JSTREE.java, v 0.1 2012-8-10 下午3:18:30 wangyong Exp $
 */
public class JSTree implements Serializable {

    /**  */
    private static final long   serialVersionUID = 1L;

    private String              data;                 // 树节点显示名称
    private Map<String, String> attr;                 // 节点的属性
    private String              state;                // 节点状态，closed open

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Map<String, String> getAttr() {
        return attr;
    }

    public void setAttr(Map<String, String> attr) {
        this.attr = attr;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
