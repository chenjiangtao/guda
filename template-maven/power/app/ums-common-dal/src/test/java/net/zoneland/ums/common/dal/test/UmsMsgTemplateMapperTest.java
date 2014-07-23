/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.dal.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.zoneland.ums.common.dal.UmsMsgTemplateMapper;
import net.zoneland.ums.common.dal.dataobject.UmsMsgTemplate;
import net.zoneland.ums.common.util.page.PageResult;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * 
 * @author Administrator
 * @version $Id: UmsMsgTemplateMapperTest.java, v 0.1 2012-10-15 上午9:20:46 Administrator Exp $
 */
@TransactionConfiguration(defaultRollback = false)
public class UmsMsgTemplateMapperTest extends BaseDaoTest {

    @Autowired
    private UmsMsgTemplateMapper umsMsgTemplateMapper;

    public void testFindByTempId() {
        UmsMsgTemplate umsMsgTemplate = umsMsgTemplateMapper.findByTempId("1234");
        System.out.println(umsMsgTemplate);
    }

    public void testGetCountByRequirement() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("tempId", "123");
        map.put("appId", "12");
        int count = umsMsgTemplateMapper.getCountByRequirement(map);
        System.out.println(count);
    }

    public void testSearchByPage() {
        int pageId = 1;
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("tempId", "123");
        params.put("appId", "12");
        int total = umsMsgTemplateMapper.getCountByRequirement(params);
        PageResult<UmsMsgTemplate> result = new PageResult<UmsMsgTemplate>(total, pageId);
        params.put("orderBy", "GMT_MODIFIED");
        params.put("first", result.getFirstrecode());
        params.put("end", result.getEndrecode());
        List<UmsMsgTemplate> list = umsMsgTemplateMapper.searchByPage(params);
        System.out.println(list);
    }

    @Test
    public void testSearchByAppId() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("appId", null);
        params.put("subAppId", null);
        params.put("templateId", "1004");
        List<UmsMsgTemplate> list = umsMsgTemplateMapper.searchByAppId(params);
        System.out.println(list.size());
    }
}
