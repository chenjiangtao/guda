/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.msg.in;

/**
 * 
 * @author gag
 * @version $Id: LockAppService.java, v 0.1 2012-12-29 上午8:10:52 gag Exp $
 */
public interface LockAppService {

    boolean lockApp(String appId);

    boolean releaseApp(String appId);

}
