/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.tiaotiaogift.common.util.enums;

/**
 * 
 * @author foodoon
 * @version $Id: NotifyEnum.java, v 0.1 2013-8-1 上午8:45:12 foodoon Exp $
 */
public enum NotifyEnum {

    /**  */
    ItemSkuZeroStock("ItemSkuZeroStock", "商品卖空提醒", ""),
    /**  */
    TradeCreate("TradeCreate", "交易创建提醒", ""),
    /**  */
    TradeBuyerPay("TradeBuyerPay", "买家付款提醒", ""), /**  */
    TradeSellerShip("TradeSellerShip", "发货提醒", ""), /**  */
    TradeSuccess("TradeSuccess", "交易成功提醒", ""), /**  */
    TradeTimeoutRemind("TradeTimeoutRemind", "交易超时提醒", ""), /**  */
    TradeRated("TradeRated", "交易评价变更提醒", "");

    private String name;
    private String code;
    private String describe;

    private NotifyEnum(String code, String name, String describe) {
        this.name = name;
        this.code = code;
        this.describe = describe;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

}
