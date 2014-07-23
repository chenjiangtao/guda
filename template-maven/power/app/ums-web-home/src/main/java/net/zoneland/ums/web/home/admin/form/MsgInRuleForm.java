/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.web.home.admin.form;

import java.util.Date;

import javax.validation.constraints.Size;

import net.zoneland.ums.biz.msg.ObjectBuilder;
import net.zoneland.ums.common.dal.bo.UmsMsgInRuleBo;
import net.zoneland.ums.common.dal.dataobject.UmsMsgInRule;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 供上行规则前端显示表单
 * 
 * @author yangjuanying
 * @version $Id: MsgInRuleForm.java, v 0.1 2012-10-13 下午9:04:53 yangjuanying Exp $
 */
public class MsgInRuleForm {

    @Size(max = 36, message = "长度不能超过36位")
    private String id;

    @NotEmpty(message = "关键字内容不能为空")
    @Size(max = 60, message = "内容关键字长度不能大于60个字符(20个汉字)")
    private String word;

    @NotEmpty(message = "请选择应用")
    private String appId;

    @Size(max = 12, message = "应用名长度不能超过12位")
    private String appName;

    private String subAppId;

    private Date   gmtCreated;

    private Date   gmtModified;

    /**
     * Getter method for property <tt>word</tt>.
     * 
     * @return property value of word
     */
    public String getWord() {
        return word;
    }

    /**
     * Setter method for property <tt>word</tt>.
     * 
     * @param word value to be assigned to property word
     */
    public void setWord(String word) {
        this.word = StringUtils.trim(word);
    }

    /**
     * Getter method for property <tt>appId</tt>.
     * 
     * @return property value of appId
     */
    public String getAppId() {
        return appId;
    }

    /**
     * Setter method for property <tt>appId</tt>.
     * 
     * @param appId value to be assigned to property appId
     */
    public void setAppId(String appId) {
        this.appId = StringUtils.trim(appId);
    }

    /**
     * Getter method for property <tt>subAppId</tt>.
     * 
     * @return property value of subAppId
     */
    public String getSubAppId() {
        return subAppId;
    }

    /**
     * Setter method for property <tt>subAppId</tt>.
     * 
     * @param subAppId value to be assigned to property subAppId
     */
    public void setSubAppId(String subAppId) {
        this.subAppId = StringUtils.trim(subAppId);
    }

    /**
     * Getter method for property <tt>gmtModified</tt>.
     * 
     * @return property value of gmtModified
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * Setter method for property <tt>gmtModified</tt>.
     * 
     * @param gmtModified value to be assigned to property gmtModified
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * Getter method for property <tt>gmtCreated</tt>.
     * 
     * @return property value of gmtCreated
     */
    public Date getGmtCreated() {
        return gmtCreated;
    }

    /**
     * Setter method for property <tt>gmtCreated</tt>.
     * 
     * @param gmtCreated value to be assigned to property gmtCreated
     */
    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    /**
     * Getter method for property <tt>id</tt>.
     * 
     * @return property value of id
     */
    public String getId() {
        return id;
    }

    /**
     * Setter method for property <tt>id</tt>.
     * 
     * @param id value to be assigned to property id
     */
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public UmsMsgInRuleBo cloneUmsMsgInRuleBo() {
        UmsMsgInRuleBo umsMsgInRuleBo = new UmsMsgInRuleBo();
        ObjectBuilder.copyObject(this, umsMsgInRuleBo);
        return umsMsgInRuleBo;
    }

    /**
     * Getter method for property <tt>appName</tt>.
     * 
     * @return property value of appName
     */
    public String getAppName() {
        return appName;
    }

    /**
     * Setter method for property <tt>appName</tt>.
     * 
     * @param appName value to be assigned to property appName
     */
    public void setAppName(String appName) {
        this.appName = StringUtils.trim(appName);
    }

    /**
     * 
     * @return
     */
    public UmsMsgInRule cloneUmsMsgInRule() {
        UmsMsgInRule umsMsgInRule = new UmsMsgInRule();
        ObjectBuilder.copyObject(this, umsMsgInRule);
        return umsMsgInRule;
    }

}
