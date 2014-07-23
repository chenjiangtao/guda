/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.msg.process.service;

import java.util.HashSet;
import java.util.Map;

/**
 *解析接收方.
 * @author wangyong
 * @version $Id: ResolveUser.java, v 0.1 2012-8-14 上午10:22:53 wangyong Exp $
 */
public interface ResolveUserService {

    /**
     *获得所有接收方</br>
     *使用map方式存放正确的接收方与错误的接收方</br>
     *map.put("correctUserList",List<String> correctUserIdList);</br>
     *map.put("errorUserList",List<String> errorUserIdList);</br>
     *正确的是指手机号跟有用户标识的ID，其他都认为是错误的接收方。
     * @param recvId
     * @return
     */
    public Map<String, HashSet<String>> getRecvIdList(String recvId);

}
