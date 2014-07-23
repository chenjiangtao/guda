/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.dal.test;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

/**
 * 
 * @author gag
 * @version $Id: Test.java, v 0.1 2012-4-25 下午11:30:33 gag Exp $
 */
@ContextConfiguration(locations = { "classpath:/spring/spring-dao.xml",
                                   "classpath:/spring-test-datasource.xml" })
public class BaseDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

}
