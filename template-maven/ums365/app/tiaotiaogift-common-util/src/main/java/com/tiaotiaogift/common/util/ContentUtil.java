/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.tiaotiaogift.common.util;

/**
 * 
 * @author gang
 * @version $Id: ContentUtil.java, v 0.1 2013-5-1 上午9:25:17 gang Exp $
 */
public class ContentUtil {

    public static int getSmsCount(int recv, String content) {
        if (content == null || recv == 0) {
            return 0;
        }
        int clen = content.length();
        int a = clen / 70;
        int b = clen % 70;
        int len = 0;
        if (b == 0) {
            len = a;
        } else {
            len = a + 1;
        }
        return len * recv;
    }

}
