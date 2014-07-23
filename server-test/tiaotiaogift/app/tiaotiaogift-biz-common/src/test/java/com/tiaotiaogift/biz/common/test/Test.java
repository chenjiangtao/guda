/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.tiaotiaogift.biz.common.test;

/**
 * 
 * @author gag
 * @version $Id: Test.java, v 0.1 2012-4-25 ����11:29:43 gag Exp $
 */
public class Test {

    public static void main(String[] args) {
        String[] str = ("12" + "\\" + "/43/bc\\/56").replace("\\/", "_").split("/");
        System.out.println("12" + "\\" + "/43/bc\\/56");
        System.out.println(str[0]);
    }

}
