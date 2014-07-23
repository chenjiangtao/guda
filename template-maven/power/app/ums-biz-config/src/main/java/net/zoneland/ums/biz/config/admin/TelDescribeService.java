/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.config.admin;

/**
 * 
 * @author gag
 * @version $Id: TelDescribeService.java, v 0.1 2012-8-22 下午4:39:01 gag Exp $
 */
public interface TelDescribeService {

    /**
     * 根据手机前7位判断是否为非浙江省号码
     * @param tel
     * @return
     */
    boolean isOutProvince(String tel);

}
