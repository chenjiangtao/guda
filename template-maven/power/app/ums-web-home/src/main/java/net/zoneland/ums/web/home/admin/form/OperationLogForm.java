/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.web.home.admin.form;

/**
 * 日志查询form
 * @author louguodong
 * @version $Id: OperationLogForm.java, v 0.1 2012-5-15 上午10:04:38 louguodong Exp $
 */
public class OperationLogForm {

    private int    curPage = 1;
    private String operatorId;  //操作员id
    private String startTime;
    private String endTime;
    private String operatorMenu; //模块 如 关键字 黑名单。。。
    private String operatorType; //操作类型 如新增删除等

    private String operatorName; //操作员姓名

    /**
     * Getter method for property <tt>operatorName</tt>.
     *
     * @return property value of operatorName
     */
    public String getOperatorName() {
        return operatorName;
    }

    /**
     * Setter method for property <tt>operatorName</tt>.
     *
     * @param operatorName value to be assigned to property operatorName
     */
    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    /**
     * Getter method for property <tt>operatorId</tt>.
     *
     * @return property value of operatorId
     */
    public String getOperatorId() {
        return operatorId;
    }

    /**
     * Setter method for property <tt>operatorId</tt>.
     *
     * @param operatorId value to be assigned to property operatorId
     */
    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    /**
     * Getter method for property <tt>startTime</tt>.
     *
     * @return property value of startTime
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * Setter method for property <tt>startTime</tt>.
     *
     * @param startTime value to be assigned to property startTime
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * Getter method for property <tt>endTime</tt>.
     *
     * @return property value of endTime
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * Setter method for property <tt>endTime</tt>.
     *
     * @param endTime value to be assigned to property endTime
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getOperatorMenu() {
        return operatorMenu;
    }

    public void setOperatorMenu(String operatorMenu) {
        this.operatorMenu = operatorMenu;
    }

    public String getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(String operatorType) {
        this.operatorType = operatorType;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

}
