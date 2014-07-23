/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.service.json.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.zoneland.ums.common.dal.dataobject.UmsMsgTemplate;
import net.zoneland.ums.common.util.GsonHelper;

import org.apache.commons.codec.binary.Base64;

import com.google.gson.Gson;

/**
 * 
 * @author gag
 * @version $Id: TestJson.java, v 0.1 2012-8-27 下午5:33:09 gag Exp $
 */
public class TestJson {

    public static void main(String[] args) {

        Map<String, MsgStat> map = new HashMap<String, MsgStat>();
        map.put("201201", new MsgStat());
        map.put("201202", new MsgStat());
        Map<String, Map<String, MsgStat>> map2 = new HashMap<String, Map<String, MsgStat>>();
        map2.put("org001", map);
        map2.put("org023", map);

        Gson gson = new Gson();

        //转换成json
        String json = gson.toJson(map2);

        // HashMap mm = gson.fromJson(json, HashMap<String.class,HashMap.class>);
        // System.out.println(mm.get("营销"));

        //输出结果
        List<UmsMsgTemplate> list = new ArrayList<UmsMsgTemplate>();
        UmsMsgTemplate t = new UmsMsgTemplate();
        t.setAppId("1000");
        t.setContent("模版测试");
        t.setEndTime(new Date());
        t.setErrorAction("1");
        t.setGmtCreated(new Date());
        t.setGmtModified(new Date());
        t.setId("uuid");
        t.setMsgType("1");
        t.setPriority(1);
        t.setRecvComments("所有用电用户");
        t.setStartTime(new Date());
        t.setSubAppId("20");
        t.setTemplateId("y001");
        t.setType("1");
        t.setValidTimeScope("8:00-23:00");
        list.add(t);
        list.add(t);
        json = GsonHelper.gson().toJson(list);
        System.out.println(json);
        System.out.println(new String(Base64.encodeBase64URLSafe("wangyong".getBytes())));
        System.out.println(new String(Base64.decodeBase64("d2FuZ3lvbmc")));

    }

}
