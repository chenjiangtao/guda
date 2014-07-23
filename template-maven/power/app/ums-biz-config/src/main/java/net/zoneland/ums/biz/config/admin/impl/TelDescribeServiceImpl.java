/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.config.admin.impl;

import java.util.Iterator;
import java.util.List;

import net.zoneland.ums.biz.config.admin.TelDescribeService;
import net.zoneland.ums.common.dal.UmsTelDescribeMapper;
import net.zoneland.ums.common.dal.dataobject.UmsTelDescribe;
import net.zoneland.ums.common.util.StringRegUtils;

import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author gag
 * @version $Id: TelDescribeServiceImpl.java, v 0.1 2012-8-22 下午4:40:49 gag Exp $
 */
public class TelDescribeServiceImpl implements TelDescribeService {

    public static final String   PROVINCE = "浙江";

    @Autowired
    private UmsTelDescribeMapper umsTelDescribeMapper;

    /**
     * @see net.zoneland.ums.biz.config.admin.TelDescribeService#isOutProvince(java.lang.String)
     */
    public boolean isOutProvince(String tel) {
        if (tel == null) {
            return true;
        }
        List<UmsTelDescribe> list = umsTelDescribeMapper.selectByTel(tel.substring(0, 7));
        if (StringRegUtils.isMobile(tel)) {
            //因为现在号段中浙江移动是全的，因此如果不存在就认为是省外的
            if (list == null || list.size() == 0) {
                return true;
            }
        } else {
            if (list == null || list.size() == 0) {
                return false;
            }
        }
        Iterator<UmsTelDescribe> it = list.iterator();
        while (it.hasNext()) {
            UmsTelDescribe des = it.next();
            if (des.getProvince() != null && des.getProvince().startsWith(PROVINCE)) {
                return false;
            }
        }
        return true;
    }

    public void setUmsTelDescribeMapper(UmsTelDescribeMapper umsTelDescribeMapper) {
        this.umsTelDescribeMapper = umsTelDescribeMapper;
    }

}
