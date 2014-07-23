/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.msg.in.impl;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import net.zoneland.ums.biz.msg.in.LockAppService;
import net.zoneland.ums.common.dal.UmsLockAppMapper;
import net.zoneland.ums.common.dal.dataobject.UmsLockApp;

import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author gag
 * @version $Id: LockAppServiceImpl.java, v 0.1 2012-12-29 上午8:11:08 gag Exp $
 */
public class LockAppServiceImpl implements LockAppService, Serializable {

    /**  */
    private static final long serialVersionUID = -8845323956055943797L;
    @Autowired
    private UmsLockAppMapper  umsLockAppMapper;

    /**
     * @see net.zoneland.ums.biz.msg.in.LockAppService#lockApp(java.lang.String)
     */
    public boolean lockApp(String appId) {
        UmsLockApp app = new UmsLockApp();
        app.setAppId(appId);
        String host = null;
        try {
            host = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            host = "localhost";
        }
        app.setHost(host);
        app.setGmtCreated(new Date());
        try {
            return umsLockAppMapper.insert(app) == 1;
        } catch (Exception e) {

        }
        return false;

    }

    /**
     * @see net.zoneland.ums.biz.msg.in.LockAppService#releaseApp(java.lang.String)
     */
    public boolean releaseApp(String appId) {
        return umsLockAppMapper.deleteByPrimaryKey(appId) == 1;
    }

}
