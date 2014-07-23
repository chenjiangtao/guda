/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.config.util;

import java.util.List;

import net.zoneland.ums.common.dal.dataobject.UmsAppInfo;
import net.zoneland.ums.common.dal.dataobject.UmsUserInfo;

/**
 * 权限配置信息对象
 * 
 * @author yangjuanying
 * @version $Id: RoleConfigAssignment.java, v 0.1 2012-9-4 上午10:08:54 yangjuanying Exp $
 */
public class RoleConfigAssignment {
    private UmsUserInfo      umsUserInfo;       // 用户表信息
    private String           orgName;           // 组织名
    private List<UmsAppInfo> umsAppInfos;       // 应用信息表集合
    private String           curRoleName;       // 当前点击的所要分配权限的用户的角色名
    private List<String>     selectappIds;      // 已选择的应用ID集合
    private List<UmsAppInfo> appStartWithABCD;  // 以应用名第一个汉字首字母是A或B或C或D开头的集合
    private List<UmsAppInfo> appStartWithEFGH;  // 以应用名第一个汉字首字母是E或F或G或H开头的集合
    private List<UmsAppInfo> appStartWithIJK;   // 以应用名第一个汉字首字母是I或J或K开头的集合
    private List<UmsAppInfo> appStartWithLMN;   // 以应用名第一个汉字首字母是L或M或N开头的集合
    private List<UmsAppInfo> appStartWithOPQR;  // 以应用名第一个汉字首字母是O或P或Q或R开头的集合
    private List<UmsAppInfo> appStartWithSTUV;  // 以应用名第一个汉字首字母是S或T或U或V开头的集合
    private List<UmsAppInfo> appStartWithWXYZ;  // 以应用名第一个汉字首字母是W或X或Y或Z开头的集合
    private List<UmsAppInfo> appStartWithOthers; // 以应用名第一个汉字首字母是非字母的集合

    /**
     * 
     */
    public RoleConfigAssignment() {
        super();
    }

    /**
     * @param umsUserInfo
     * @param orgName
     * @param curRoleName
     */
    public RoleConfigAssignment(UmsUserInfo umsUserInfo, String orgName, String curRoleName) {
        super();
        this.umsUserInfo = umsUserInfo;
        this.orgName = orgName;
        this.curRoleName = curRoleName;
    }

    /**
     * @param umsUserInfo
     * @param orgName
     * @param umsAppInfos
     * @param curRoleName
     * @param selectappIds
     * @param appStartWithABCD
     * @param appStartWithEFGH
     * @param appStartWithIJK
     * @param appStartWithLMN
     * @param appStartWithOPQR
     * @param appStartWithSTUV
     * @param appStartWithWXYZ
     * @param appStartWithOthers
     */
    public RoleConfigAssignment(UmsUserInfo umsUserInfo, String orgName,
                                List<UmsAppInfo> umsAppInfos, String curRoleName,
                                List<String> selectappIds, List<UmsAppInfo> appStartWithABCD,
                                List<UmsAppInfo> appStartWithEFGH,
                                List<UmsAppInfo> appStartWithIJK, List<UmsAppInfo> appStartWithLMN,
                                List<UmsAppInfo> appStartWithOPQR,
                                List<UmsAppInfo> appStartWithSTUV,
                                List<UmsAppInfo> appStartWithWXYZ,
                                List<UmsAppInfo> appStartWithOthers) {
        super();
        this.umsUserInfo = umsUserInfo;
        this.orgName = orgName;
        this.umsAppInfos = umsAppInfos;
        this.curRoleName = curRoleName;
        this.selectappIds = selectappIds;
        this.appStartWithABCD = appStartWithABCD;
        this.appStartWithEFGH = appStartWithEFGH;
        this.appStartWithIJK = appStartWithIJK;
        this.appStartWithLMN = appStartWithLMN;
        this.appStartWithOPQR = appStartWithOPQR;
        this.appStartWithSTUV = appStartWithSTUV;
        this.appStartWithWXYZ = appStartWithWXYZ;
        this.appStartWithOthers = appStartWithOthers;
    }

    /**
     * Getter method for property <tt>selectappIds</tt>.
     * 
     * @return property value of selectappIds
     */
    public List<String> getSelectappIds() {
        return selectappIds;
    }

    /**
     * Setter method for property <tt>selectappIds</tt>.
     * 
     * @param selectappIds value to be assigned to property selectappIds
     */
    public void setSelectappIds(List<String> selectappIds) {
        this.selectappIds = selectappIds;
    }

    /**
     * Getter method for property <tt>umsUserInfo</tt>.
     * 
     * @return property value of umsUserInfo
     */
    public UmsUserInfo getUmsUserInfo() {
        return umsUserInfo;
    }

    /**
     * Setter method for property <tt>umsUserInfo</tt>.
     * 
     * @param umsUserInfo value to be assigned to property umsUserInfo
     */
    public void setUmsUserInfo(UmsUserInfo umsUserInfo) {
        this.umsUserInfo = umsUserInfo;
    }

    /**
     * Getter method for property <tt>orgName</tt>.
     * 
     * @return property value of orgName
     */
    public String getOrgName() {
        return orgName;
    }

    /**
     * Setter method for property <tt>orgName</tt>.
     * 
     * @param orgName value to be assigned to property orgName
     */
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    /**
     * Getter method for property <tt>umsAppInfos</tt>.
     * 
     * @return property value of umsAppInfos
     */
    public List<UmsAppInfo> getUmsAppInfos() {
        return umsAppInfos;
    }

    /**
     * Setter method for property <tt>umsAppInfos</tt>.
     * 
     * @param umsAppInfos value to be assigned to property umsAppInfos
     */
    public void setUmsAppInfos(List<UmsAppInfo> umsAppInfos) {
        this.umsAppInfos = umsAppInfos;
    }

    /**
     * Getter method for property <tt>curRoleName</tt>.
     * 
     * @return property value of curRoleName
     */
    public String getCurRoleName() {
        return curRoleName;
    }

    /**
     * Setter method for property <tt>curRoleName</tt>.
     * 
     * @param curRoleName value to be assigned to property curRoleName
     */
    public void setCurRoleName(String curRoleName) {
        this.curRoleName = curRoleName;
    }

    /**
     * Getter method for property <tt>appStartWithABCD</tt>.
     * 
     * @return property value of appStartWithABCD
     */
    public List<UmsAppInfo> getAppStartWithABCD() {
        return appStartWithABCD;
    }

    /**
     * Setter method for property <tt>appStartWithABCD</tt>.
     * 
     * @param appStartWithABCD value to be assigned to property appStartWithABCD
     */
    public void setAppStartWithABCD(List<UmsAppInfo> appStartWithABCD) {
        this.appStartWithABCD = appStartWithABCD;
    }

    /**
     * Getter method for property <tt>appStartWithEFGH</tt>.
     * 
     * @return property value of appStartWithEFGH
     */
    public List<UmsAppInfo> getAppStartWithEFGH() {
        return appStartWithEFGH;
    }

    /**
     * Setter method for property <tt>appStartWithEFGH</tt>.
     * 
     * @param appStartWithEFGH value to be assigned to property appStartWithEFGH
     */
    public void setAppStartWithEFGH(List<UmsAppInfo> appStartWithEFGH) {
        this.appStartWithEFGH = appStartWithEFGH;
    }

    /**
     * Getter method for property <tt>appStartWithIJK</tt>.
     * 
     * @return property value of appStartWithIJK
     */
    public List<UmsAppInfo> getAppStartWithIJK() {
        return appStartWithIJK;
    }

    /**
     * Setter method for property <tt>appStartWithIJK</tt>.
     * 
     * @param appStartWithIJK value to be assigned to property appStartWithIJK
     */
    public void setAppStartWithIJK(List<UmsAppInfo> appStartWithIJK) {
        this.appStartWithIJK = appStartWithIJK;
    }

    /**
     * Getter method for property <tt>appStartWithLMN</tt>.
     * 
     * @return property value of appStartWithLMN
     */
    public List<UmsAppInfo> getAppStartWithLMN() {
        return appStartWithLMN;
    }

    /**
     * Setter method for property <tt>appStartWithLMN</tt>.
     * 
     * @param appStartWithLMN value to be assigned to property appStartWithLMN
     */
    public void setAppStartWithLMN(List<UmsAppInfo> appStartWithLMN) {
        this.appStartWithLMN = appStartWithLMN;
    }

    /**
     * Getter method for property <tt>appStartWithOPQR</tt>.
     * 
     * @return property value of appStartWithOPQR
     */
    public List<UmsAppInfo> getAppStartWithOPQR() {
        return appStartWithOPQR;
    }

    /**
     * Setter method for property <tt>appStartWithOPQR</tt>.
     * 
     * @param appStartWithOPQR value to be assigned to property appStartWithOPQR
     */
    public void setAppStartWithOPQR(List<UmsAppInfo> appStartWithOPQR) {
        this.appStartWithOPQR = appStartWithOPQR;
    }

    /**
     * Getter method for property <tt>appStartWithSTUV</tt>.
     * 
     * @return property value of appStartWithSTUV
     */
    public List<UmsAppInfo> getAppStartWithSTUV() {
        return appStartWithSTUV;
    }

    /**
     * Setter method for property <tt>appStartWithSTUV</tt>.
     * 
     * @param appStartWithSTUV value to be assigned to property appStartWithSTUV
     */
    public void setAppStartWithSTUV(List<UmsAppInfo> appStartWithSTUV) {
        this.appStartWithSTUV = appStartWithSTUV;
    }

    /**
     * Getter method for property <tt>appStartWithWXYZ</tt>.
     * 
     * @return property value of appStartWithWXYZ
     */
    public List<UmsAppInfo> getAppStartWithWXYZ() {
        return appStartWithWXYZ;
    }

    /**
     * Setter method for property <tt>appStartWithWXYZ</tt>.
     * 
     * @param appStartWithWXYZ value to be assigned to property appStartWithWXYZ
     */
    public void setAppStartWithWXYZ(List<UmsAppInfo> appStartWithWXYZ) {
        this.appStartWithWXYZ = appStartWithWXYZ;
    }

    /**
     * Getter method for property <tt>appStartWithOthers</tt>.
     * 
     * @return property value of appStartWithOthers
     */
    public List<UmsAppInfo> getAppStartWithOthers() {
        return appStartWithOthers;
    }

    /**
     * Setter method for property <tt>appStartWithOthers</tt>.
     * 
     * @param appStartWithOthers value to be assigned to property appStartWithOthers
     */
    public void setAppStartWithOthers(List<UmsAppInfo> appStartWithOthers) {
        this.appStartWithOthers = appStartWithOthers;
    }

}
