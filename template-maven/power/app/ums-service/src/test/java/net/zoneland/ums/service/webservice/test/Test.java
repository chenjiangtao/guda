/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.service.webservice.test;

import org.springframework.web.util.HtmlUtils;

/**
 * 
 * @author gag
 * @version $Id: Test.java, v 0.1 2012-11-8 下午8:44:03 gag Exp $
 */
public class Test {

    public static void main(String[] args) {
        System.out.println(HtmlUtils.htmlEscape("[{\"ab\")"));
        System.out.println(HtmlUtils.htmlUnescape(HtmlUtils.htmlUnescape(HtmlUtils
            .htmlEscape("[{\"ab\")"))));

    }

}
