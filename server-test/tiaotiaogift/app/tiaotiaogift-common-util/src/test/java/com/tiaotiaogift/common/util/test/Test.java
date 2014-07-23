/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.tiaotiaogift.common.util.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.tiaotiaogift.common.util.MD5;

/**
 * 
 * @author gag
 * @version $Id: Test.java, v 0.1 2012-4-25 ����11:31:07 gag Exp $
 */
public class Test {

    public static void main(String[] args) {
        String n = null;
        System.out.println(n + "123");
        System.out.println(MD5.md5("nulln9"));
        Gson gson = new Gson();
        Response res = new Response();
        res.setCode("1");
        res.setMsg("success");
        System.out.println(gson.toJson(res));

        List<ResponseMsgIn> ins = new ArrayList<ResponseMsgIn>(2);
        ResponseMsgIn r = new ResponseMsgIn();
        r.setContent("测试短信1");
        r.setGmtCreated(new Date());
        r.setMobile("13512341234");
        ins.add(r);
        r = new ResponseMsgIn();
        r.setContent("测试短信221");
        r.setGmtCreated(new Date());
        r.setMobile("13812341234");
        ins.add(r);
        System.out.println(gson.toJson(ins));
    }
}
