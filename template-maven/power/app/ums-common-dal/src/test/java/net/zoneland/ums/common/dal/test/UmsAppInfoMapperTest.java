/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.dal.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.zoneland.ums.common.dal.UmsAppInfoMapper;
import net.zoneland.ums.common.dal.UmsAppSubMapper;
import net.zoneland.ums.common.dal.dataobject.UmsAppInfo;
import net.zoneland.ums.common.util.MD5;
import net.zoneland.ums.common.util.helper.StringHelper;
import net.zoneland.ums.common.util.page.PageResult;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * 
 * @author gag
 * @version $Id: UmsAppInfoMapperTest.java, v 0.1 2012-8-13 上午11:25:35 gag Exp $
 */
@TransactionConfiguration(defaultRollback = false)
public class UmsAppInfoMapperTest extends BaseDaoTest {

    @Autowired
    private UmsAppInfoMapper umsAppInfoMapper;
    @Autowired
    private UmsAppSubMapper  umsAppSubMapper;

    //@Test
    public void testInsert() {
        // for (int i = 72; i < 150; ++i) {
        UmsAppInfo u = new UmsAppInfo();
        u.setId(UUID.randomUUID().toString());
        u.setAppCode("4567");
        u.setAppName("1111");
        u.setAppId("1002");
        u.setGmtCreated(new Date());
        u.setGmtModified(new Date());
        u.setIp("127.0.0.1");
        u.setStatus("1");
        u.setPassword(MD5.md5("1111"));
        u.setUsername("username");
        System.out.println("新：" + umsAppInfoMapper.insert(u));
        // }

    }

    public void testSelectByPrimaryKey() {

        System.out.println("新："
                           + umsAppInfoMapper.selectByPrimaryKey(
                               "6ae80c0d-9d0f-4222-b894-d7433af2ffc4").getPassword());

    }

    //@Test
    public void testSelectAll() {
        List<UmsAppInfo> umsAppInfos = umsAppInfoMapper.selectAll();
        System.out.println(umsAppInfos.size());
    }

    //@Test
    public void testSearchAppByPage() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("appName", "n");
        params.put("status", "");
        int total = umsAppInfoMapper.getAppNum(params);
        PageResult<UmsAppInfo> result = new PageResult<UmsAppInfo>(total, 1);

        params.put("first", result.getFirstrecode());
        params.put("end", result.getEndrecode());
        List<UmsAppInfo> res = umsAppInfoMapper.searchAppByPage(params);
        int t = umsAppInfoMapper.getAppNum(params);
        System.out.println(res.size());
        System.out.println(t);
    }

    //@Test
    public void testSearchSelectiveByPage() {
        List<String> appIdList = new ArrayList<String>();
        appIdList.add("appID1");
        appIdList.add("appID2");
        Integer first = 0;
        Integer end = 2;
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("first", first);
        params.put("end", end);
        params.put("appIdList", appIdList);
        System.out.println("--------" + umsAppInfoMapper.searchSelectiveByPage(params));
    }

    public void testSelectAllSubApp() {
        System.out.println(umsAppSubMapper.selectAllSubApp("appID1"));
    }

    public void testUpdate() {
        UmsAppInfo umsAppInfo = new UmsAppInfo();
        umsAppInfo.setPassword("modfiy pass");
        umsAppInfo.setId("6ae80c0d-9d0f-4222-b894-d7433af2ffc4");
        System.out.println(umsAppInfoMapper.updateByPrimaryKeySelective(umsAppInfo));
    }

    public void testselectNum() {
        UmsAppInfo umsAppInfo = new UmsAppInfo();
        umsAppInfo.setStatus("1");
        System.out.println(umsAppInfoMapper.searchAllNum(umsAppInfo));
    }

    public void testselectWithAppIdPassword() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("appId", "ums2");
        map.put("password", "124");
        System.out.println(umsAppInfoMapper.selectWithAppIdPassword(map));
    }

    public void testgetAllApp() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("first", 0);
        map.put("end", 0);
        System.out.println(umsAppInfoMapper.searchAppByPage(map));
    }

    public void testGetAppByNameAndAppId() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", "2b81716e-a358-48df-ba99-b9386e451887");
        map.put("appName", "应用");
        map.put("appId", "1");
        System.out.println(umsAppInfoMapper.getAppByNameAndAppId(map));
    }

    public void testDelete() {
        String id = "";
    }

    @Test
    public void testPinYin4j() {
        int A = 0, B = 0, C = 0, D = 0, E = 0, F = 0, G = 0, H = 0, I = 0, J = 0, K = 0, L = 0, M = 0, N = 0, O = 0, P = 0, Q = 0, R = 0, S = 0, T = 0, U = 0, V = 0, W = 0, X = 0, Y = 0, Z = 0;
        List<UmsAppInfo> umsAppInfos = umsAppInfoMapper.selectAll();
        for (UmsAppInfo umsAppInfo : umsAppInfos) {
            String appHead = StringHelper.getPinYinHeadChar(umsAppInfo.getAppName());
            if (appHead.length() >= 1) {
                String appHeadCharacter = appHead.substring(0, 1);
                if (StringUtils.trim(appHeadCharacter.toUpperCase()).equals("A")) {
                    ++A;
                } else if (StringUtils.trim(appHeadCharacter.toUpperCase()).equals("B")) {
                    ++B;
                } else if (StringUtils.trim(appHeadCharacter.toUpperCase()).equals("C")) {
                    ++C;
                } else if (StringUtils.trim(appHeadCharacter.toUpperCase()).equals("D")) {
                    ++D;
                } else if (StringUtils.trim(appHeadCharacter.toUpperCase()).equals("E")) {
                    ++E;
                } else if (StringUtils.trim(appHeadCharacter.toUpperCase()).equals("F")) {
                    ++F;
                } else if (StringUtils.trim(appHeadCharacter.toUpperCase()).equals("G")) {
                    ++G;
                } else if (StringUtils.trim(appHeadCharacter.toUpperCase()).equals("H")) {
                    ++H;
                } else if (StringUtils.trim(appHeadCharacter.toUpperCase()).equals("I")) {
                    ++I;
                } else if (StringUtils.trim(appHeadCharacter.toUpperCase()).equals("J")) {
                    ++J;
                } else if (StringUtils.trim(appHeadCharacter.toUpperCase()).equals("K")) {
                    ++K;
                } else if (StringUtils.trim(appHeadCharacter.toUpperCase()).equals("L")) {
                    ++L;
                } else if (StringUtils.trim(appHeadCharacter.toUpperCase()).equals("M")) {
                    ++M;
                } else if (StringUtils.trim(appHeadCharacter.toUpperCase()).equals("N")) {
                    ++N;
                } else if (StringUtils.trim(appHeadCharacter.toUpperCase()).equals("O")) {
                    ++O;
                } else if (StringUtils.trim(appHeadCharacter.toUpperCase()).equals("P")) {
                    ++P;
                } else if (StringUtils.trim(appHeadCharacter.toUpperCase()).equals("Q")) {
                    ++Q;
                } else if (StringUtils.trim(appHeadCharacter.toUpperCase()).equals("R")) {
                    ++R;
                } else if (StringUtils.trim(appHeadCharacter.toUpperCase()).equals("S")) {
                    ++S;
                } else if (StringUtils.trim(appHeadCharacter.toUpperCase()).equals("T")) {
                    ++T;
                } else if (StringUtils.trim(appHeadCharacter.toUpperCase()).equals("U")) {
                    ++U;
                } else if (StringUtils.trim(appHeadCharacter.toUpperCase()).equals("V")) {
                    ++V;
                } else if (StringUtils.trim(appHeadCharacter.toUpperCase()).equals("W")) {
                    ++W;
                } else if (StringUtils.trim(appHeadCharacter.toUpperCase()).equals("X")) {
                    ++X;
                } else if (StringUtils.trim(appHeadCharacter.toUpperCase()).equals("Y")) {
                    ++Y;
                } else if (StringUtils.trim(appHeadCharacter.toUpperCase()).equals("Z")) {
                    ++Z;
                }
            }
        }
        System.out.println("Asize:" + A + ",Bsize:" + B + ",Csize:" + C + ",Dsize:" + D + ",Esize:"
                           + E + ",Fsize:" + F + ",Gsize:" + G + ",Hsize:" + H + ",Isize:" + I
                           + ",Jsize:" + J + ",Ksize:" + K + ",Lsize:" + L + ",Msize:" + M
                           + ",Nsize:" + N + ",Osize:" + O + ",Psize:" + P + ",Qsize:" + Q
                           + ",Rsize:" + R + ",Ssize:" + S + ",Tsize:" + T + ",Usize:" + U
                           + ",Vsize:" + V + ",Wsize:" + W + ",Xsize:" + X + ",Ysize:" + Y
                           + ",Zsize:" + Z);
    }

}
