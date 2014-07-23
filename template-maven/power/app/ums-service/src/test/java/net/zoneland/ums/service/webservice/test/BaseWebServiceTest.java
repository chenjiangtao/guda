package net.zoneland.ums.service.webservice.test;

/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
import java.net.MalformedURLException;

import net.zoneland.ums.common.util.Base64;

import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;

/**
 * 
 * @author gag
 * @version $Id: Test.java, v 0.1 2012-4-25 11:31:07 gag Exp $
 */
public class BaseWebServiceTest {

    public static void main(String[] args) {
        System.out.println((new String(Base64.encodeBase64("abc".getBytes()))));
    }

    /**
     *
     * @param cls
     * @param ws
     * @return
     * @throws MalformedURLException
     */
    public static Object setWsdlInfo(Class<?> cls, String wsdl) throws MalformedURLException {
        Service serviceModel = new ObjectServiceFactory().create(cls);

        return new XFireProxyFactory().create(serviceModel, wsdl);
    }

}
