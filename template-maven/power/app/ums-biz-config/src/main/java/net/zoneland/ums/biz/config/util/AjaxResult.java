/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.config.util;

/**
 * Ajax 调用时放回的对象
 * @author yangjuanying
 * @version $Id: AjaxResult.java, v 0.1 2012-9-3 下午02:33:40 yangjuanying Exp $
 */
public class AjaxResult {
    private boolean result;
    private String  info;
    private String  curUserRole; // 供【分配角色】界面回调显示当前分配的用户角色信息
    private Object  resultObject;

    /**
     * Getter method for property <tt>curUserRole</tt>.
     * 
     * @return property value of curUserRole
     */
    public String getCurUserRole() {
        return curUserRole;
    }

    /**
     * Setter method for property <tt>curUserRole</tt>.
     * 
     * @param curUserRole value to be assigned to property curUserRole
     */
    public void setCurUserRole(String curUserRole) {
        this.curUserRole = curUserRole;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    /**
     * Getter method for property <tt>resultObject</tt>.
     * 
     * @return property value of resultObject
     */
    public Object getResultObject() {
        return resultObject;
    }

    /**
     * Setter method for property <tt>resultObject</tt>.
     * 
     * @param resultObject value to be assigned to property resultObject
     */
    public void setResultObject(Object resultObject) {
        this.resultObject = resultObject;
    }

}
