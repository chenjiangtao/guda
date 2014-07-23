/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.dal.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.zoneland.ums.common.dal.UmsMsgOutMapper;
import net.zoneland.ums.common.dal.UmsMsgOutUcsMapper;
import net.zoneland.ums.common.dal.bo.UmsMsgOutDO;
import net.zoneland.ums.common.dal.bo.UmsMsgOutIphone;
import net.zoneland.ums.common.dal.dataobject.UmsMsgOut;
import net.zoneland.ums.common.util.Host;
import net.zoneland.ums.common.util.enums.msg.MsgInfoStatusEnum;
import net.zoneland.ums.common.util.enums.msg.MsgStatusIphoneEnum;
import net.zoneland.ums.common.util.helper.DateHelper;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 *
 * @author XuFan
 * @version $Id: UmsMsgOutTest.java, v 0.1 Aug 24, 2012 9:50:23 AM XuFan Exp $
 */
@TransactionConfiguration(defaultRollback = false)
public class UmsMsgOutTest extends BaseDaoTest {
    @Autowired
    private UmsMsgOutMapper    umsMsgOutMapper;

    @Autowired
    private UmsMsgOutUcsMapper umsMsgOutUcsMapper;

    public void test1() {
        UmsMsgOut ums = umsMsgOutMapper.selectByPrimaryKey("a8096620-ca29-4de6-a13f-71e966f5bf8e");
        for (int i = 0; i < 1; i++) {
            List<UmsMsgOut> list = new ArrayList<UmsMsgOut>();
            ums.setId(UUID.randomUUID().toString());
            ums.setOrgNo("33401");
            ums.setContent(null);
            list.add(ums);
            int result = umsMsgOutMapper.insertBatch(list);
            System.out.println(i + " : " + result);
            //            new UmsMsgOutTest().test(10);
        }
    }

    public void updateStatusByHostTest() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", "2");
        map.put("targetStatus", "0");
        map.put("host", "172.16.86.103");
        map.put("gmtModified", new Date());
        int result = umsMsgOutUcsMapper.updateStatusByHost(map);
        System.out.println(result);
    }

    public void updateStatusById() {
        UmsMsgOut msgOut = new UmsMsgOut();
        msgOut.setId("34d9e164-b7a7-47ee-86e5-30d5f143748d");
        msgOut.setStatus(MsgInfoStatusEnum.expire.getValue());
        msgOut.setErrorMsg(MsgInfoStatusEnum.expire.getDescription());
        msgOut.setGmtModified(new Date());
        msgOut.setHost(Host.getHost());
        umsMsgOutMapper.updateStatusById(msgOut);

    }

    public static void main1(String[] args) {

    }

    //    public static void main(String[] args) {
    //        for (int i = 0; i < 1; i++) {
    //            //            UmsMsgOut ums = umsMsgOutMapper
    //            //                .selectByPrimaryKey("8b616558-83e8-4825-b873-60296b4e180b");
    //            //            System.out.println(ums);
    //            //            ums.setId(UUID.randomUUID().toString());
    //            //            List<UmsMsgOut> list = new ArrayList<UmsMsgOut>();
    //            //            list.add(ums);
    //            //            int result = umsMsgOutMapper.insertBatch(list);
    //            //            System.out.println(i + " : " + result);
    //            new UmsMsgOutTest().test(10);
    //        }
    //    }

    public void test(int num) {
        for (int i = 0; i < 1; i++) {
            new InsertMsg().start();
        }
    }

    public class InsertMsg extends Thread {

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                System.out.println(umsMsgOutMapper);
                UmsMsgOut ums = umsMsgOutMapper
                    .selectByPrimaryKey("8b616558-83e8-4825-b873-60296b4e180b");
                System.out.println(ums);
                ums.setId(UUID.randomUUID().toString());
                List<UmsMsgOut> list = new ArrayList<UmsMsgOut>();
                list.add(ums);
                int result = umsMsgOutMapper.insertBatch(list);
                System.out.println(Thread.class.getName() + i + " : " + result);
            }
        }
    }

    public void test2() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(new Date());
        System.out.println(time);
        System.out.println(umsMsgOutMapper.getMsgOutNow(time));
    }

    //    public void testselectDelayLimit1000() {
    //        Map<String, Object> map = new HashMap<String, Object>(6);
    //        map.put("status", MsgInfoStatusEnum.success.getValue());
    //        map.put("sendTime", new Date());
    //        List<UmsMsgOut> list = umsMsgOutMapper.selectDelayLimit3000(map);
    //        list.addAll(list);
    //        System.out.println("返回结果 ： " + list.size());
    //    }

    public void testSelectMsgOutStat() {

    }

    public void testsearchCountByRecvId() {
        Map<String, Object> params = new HashMap<String, Object>();

        params.put("recvId", "13588754574");
        params.put("first", 0);
        params.put("end", 10);
        params.put("sinceTime", new Date());
        Integer a = umsMsgOutMapper.searchCountByRecvId(params);
        System.out.println("返回结果 ： " + a);
    }

    public void testsearchByRecvId() {
        Map<String, Object> params = new HashMap<String, Object>();

        params.put("recvId", "13588754574");
        params.put("first", 0);
        params.put("end", 10);
        params.put("sinceTime", new Date());
        List<UmsMsgOutIphone> re = umsMsgOutMapper.searchByRecvId(params);
        System.out.println(re);
    }

    @Test
    public void testsearchByUserId() {
        Map<String, Object> params = new HashMap<String, Object>();

        params.put("recvId", "13588754574");
        params.put("first", 0);
        params.put("end", 10);
        params.put("sinceTime", new Date());
        List<UmsMsgOutDO> re = umsMsgOutMapper.searchMyMsgByUserId(params);
        System.out.println(re);
    }

    //@Test
    public void testupdateMobileStatus() {
        Map<String, Object> params = new HashMap<String, Object>(6);
        params.put("id", "5eef01ee-5f55-42cc-9bf8-939740c1a339");
        params.put("mobileStatus", MsgStatusIphoneEnum.del.getValue());
        int res = umsMsgOutMapper.updateMobileStatus(params);
        System.out.println(res);
    }

    public void testSelectByStatusLimit2000() {
        List<UmsMsgOut> lists = umsMsgOutUcsMapper.selectByStatusLimit2000(MsgInfoStatusEnum.init
            .getValue());
        for (UmsMsgOut ums : lists) {
            System.out.println(ums.getDocount());
            if (ums.getId().equalsIgnoreCase("07b860f7-56c2-4531-a4e2-330fdc24a5dc")) {
                System.out.println("找到错误了");
                break;
            }
        }
        System.out.println("********" + lists.size());
    }

    public void testFiling() {
        Calendar d = Calendar.getInstance();
        //        /// String mon = String.valueOf(d.get(Calendar.MONTH));
        //        d.add(Calendar.MONTH, -3);
        Date date = DateHelper.getEndOfMonth(d.getTime());
        //        String mon = DateHelper.monFormat.format(date);
        Map<String, Object> params = new HashMap<String, Object>(6);
        params.put("tableName", "test");
        params.put("gmtCreated", date);
        //        System.out.println(DateHelper.dateFormat.format(date));
        //        int c = umsMsgOutMapper.fileSelectUmsMsgOut("UMS_MSG_OUT_" + mon);
        //        if (c > 0) {
        //            System.out.println("已经归档");
        //            return;
        //        }
        //        umsMsgOutMapper.fileCreateUmsMsgOut(params);
        System.out.println("开始对表格test进行归档");
        int res = umsMsgOutMapper.fileInsertUmsMsgOut(params);
        System.out.println(res);
        //        if (res > 0) {
        //            umsMsgOutMapper.fileDeleteUmsMsgOut(params);
        //
        //        }

    }

    //@Test
    public void testSearchAllMsgByPage() {
        Map<String, Object> params = new HashMap<String, Object>();
        List<String> areaCodeList = new ArrayList<String>();
        areaCodeList.add("3");
        areaCodeList.add("31");
        areaCodeList.add("4");
        params.put("areaCodeList", areaCodeList);
        params.put("flowNo", null);
        params.put("bizType", null);
        int total = umsMsgOutMapper.searchAllMsgNum(params);
        System.out.println(total);
    }

    //    @Test
    public void testUpdate() {
        UmsMsgOut umsMsgOut = new UmsMsgOut();
        umsMsgOut.setId("a742645d-ed02-4d30-8e09-fc26bfd7a428");
        umsMsgOut.setStatus("1");

        umsMsgOut.setGmtModified(new Date());

        System.out.println(umsMsgOutMapper.updateStatus(umsMsgOut));
    }

    public void testStat() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(new Date());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", MsgInfoStatusEnum.success.getValue());
        map.put("date", date);
        System.out.println(umsMsgOutMapper.statMsgByAppIdAndMedia(map));
    }
}
