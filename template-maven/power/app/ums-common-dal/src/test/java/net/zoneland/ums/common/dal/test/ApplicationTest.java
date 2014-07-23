/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.dal.test;

import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * 
 * @author Administrator
 * @version $Id: ApplicationTest.java, v 0.1 2012-11-26 上午10:38:26 Administrator Exp $
 */
@TransactionConfiguration(defaultRollback = false)
public class ApplicationTest extends BaseDaoTest {

    //    @Autowired
    //    private ApplicationMapper applicationMapper;
    //
    //    @Autowired
    //    private UmsAppInfoMapper  umsAppInfoMapper;
    //
    //    public void test() {
    //        String id = "1000";
    //        System.out.println(applicationMapper.selectByPrimaryKey(id));
    //    }
    //
    //    @Test
    //    public void applicationChange() {
    //        List<Application> applications = applicationMapper.selectAllApp();
    //        System.out.println(applications.size());
    //        for (Application application : applications) {
    //            UmsAppInfo umsAppInfo = new UmsAppInfo();
    //            umsAppInfo.setId(UUID.randomUUID().toString());
    //            umsAppInfo.setAppId(application.getAppid());
    //            umsAppInfo.setAppName(application.getAppname());
    //            umsAppInfo.setAppCode(application.getAppid());
    //            umsAppInfo.setIp(application.getIp());
    //            umsAppInfo.setPassword(MD5.md5(StringUtils.trim(application.getPassword())));
    //            umsAppInfo.setPort(String.valueOf(application.getPort()));
    //            umsAppInfo.setFee(application.getFee());
    //            umsAppInfo.setFeeType(application.getFeetype());
    //            umsAppInfo.setSignName(application.getSigname());
    //            umsAppInfo.setUsername(application.getAppid());
    //            umsAppInfo.setStatus(application.getStatus() == null ? "0" : application.getStatus());
    //            umsAppInfo.setGmtCreated(new Date());
    //            umsAppInfo.setGmtModified(new Date());
    //            System.out.println(umsAppInfo);
    //            int result = umsAppInfoMapper.insert(umsAppInfo);
    //            System.out.println(result);
    //        }
    //    }
}
