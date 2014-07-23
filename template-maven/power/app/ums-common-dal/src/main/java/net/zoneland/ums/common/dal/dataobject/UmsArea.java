/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.dal.dataobject;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 单位表
 * 
 * @author yangjuanying
 * @version $Id: UmsArea.java, v 0.1 2012-10-18 上午9:21:38 yangjuanying Exp $
 */
public class UmsArea {

    private String  id;

    private String  areaCode;

    private String  areaName;

    private String  parentId;

    private Integer order;

    private Date    gmtCreated;

    public UmsArea() {
        super();
    }

    /**
     * @param areaCode
     * @param areaName
     * @param parentId
     */
    public UmsArea(String areaCode, String areaName, String parentId) {
        super();
        this.areaCode = areaCode;
        this.areaName = areaName;
        this.parentId = parentId;
    }

    /**
     * Getter method for property <tt>areaCode</tt>.
     * 
     * @return property value of areaCode
     */
    public String getAreaCode() {
        return areaCode;
    }

    /**
     * Setter method for property <tt>areaCode</tt>.
     * 
     * @param areaCode
     *            value to be assigned to property areaCode
     */
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
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
     * @param id
     *            value to be assigned to property id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter method for property <tt>areaName</tt>.
     * 
     * @return property value of areaName
     */
    public String getAreaName() {
        return areaName;
    }

    /**
     * Setter method for property <tt>areaName</tt>.
     * 
     * @param areaName
     *            value to be assigned to property areaName
     */
    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    /**
     * Getter method for property <tt>parentId</tt>.
     * 
     * @return property value of parentId
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * Setter method for property <tt>parentId</tt>.
     * 
     * @param parentId
     *            value to be assigned to property parentId
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /**
     * Getter method for property <tt>order</tt>.
     * 
     * @return property value of order
     */
    public Integer getOrder() {
        return order;
    }

    /**
     * Setter method for property <tt>order</tt>.
     * 
     * @param order
     *            value to be assigned to property order
     */
    public void setOrder(Integer order) {
        this.order = order;
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
     * @param gmtCreated
     *            value to be assigned to property gmtCreated
     */
    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    /** 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((areaCode == null) ? 0 : areaCode.hashCode());
        return result;
    }

    /** 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UmsArea other = (UmsArea) obj;
        if (areaCode == null) {
            if (other.areaCode != null)
                return false;
        } else if (!areaCode.equals(other.areaCode))
            return false;
        return true;
    }
}
