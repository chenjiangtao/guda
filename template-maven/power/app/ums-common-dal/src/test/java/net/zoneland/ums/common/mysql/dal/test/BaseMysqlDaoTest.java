/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.mysql.dal.test;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

/**
 * 
 * @author gag
 * @version $Id: BaseMysqlDaoTest.java, v 0.1 2012-11-15 下午2:04:34 gag Exp $
 */

@ContextConfiguration(locations = { "classpath:/spring-test-datasource-mysql.xml" })
public class BaseMysqlDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

}
