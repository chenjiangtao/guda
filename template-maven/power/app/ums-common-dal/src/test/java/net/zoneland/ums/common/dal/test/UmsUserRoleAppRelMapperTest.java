/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.dal.test;

import net.zoneland.ums.common.dal.UmsMsgDraftMapper;
import net.zoneland.ums.common.dal.UmsUserRoleAppRelMapper;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 *
 * @author XuFan
 * @version $Id: UmsUserRoleAppRelMapperTest.java, v 0.1 Aug 15, 2012 2:22:10 PM XuFan Exp $
 */
@TransactionConfiguration(defaultRollback = false)
public class UmsUserRoleAppRelMapperTest extends BaseDaoTest {
    @Autowired
    private UmsUserRoleAppRelMapper userRoleAppRelMapper;
    @Autowired
    private UmsMsgDraftMapper       umsMsgDraftMapper;

    @Test
    public void testSearchAllAppNum() {
        /*String userId = "anonymous";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("start", 7);
        map.put("end", 2 * 6);
        List<UmsMsgDraft> msgDraft = umsMsgDraftMapper.selectByUserId(map);
        System.out.println(msgDraft.size());*/
    }

    public UmsUserRoleAppRelMapper getUserRoleAppRelMapper() {
        return userRoleAppRelMapper;
    }

    public void setUserRoleAppRelMapper(UmsUserRoleAppRelMapper userRoleAppRelMapper) {
        this.userRoleAppRelMapper = userRoleAppRelMapper == null ? null : userRoleAppRelMapper;
    }

    public UmsMsgDraftMapper getUmsMsgDraftMapper() {
        return umsMsgDraftMapper;
    }

    public void setUmsMsgDraftMapper(UmsMsgDraftMapper umsMsgDraftMapper) {
        this.umsMsgDraftMapper = umsMsgDraftMapper == null ? null : umsMsgDraftMapper;
    }
    
}
