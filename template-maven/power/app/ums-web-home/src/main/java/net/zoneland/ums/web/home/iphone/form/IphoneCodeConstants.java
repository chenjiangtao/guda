/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.web.home.iphone.form;

/**
 * 
 * @author yangjuanying
 * @version $Id: IphoneCodeConstants.java, v 0.1 2012-9-26 下午3:11:25 yangjuanying Exp $
 */
public class IphoneCodeConstants {

    public static final String SUCCESS     = "1"; // 1表示成功

    public static final String FAILURE     = "2"; // 2表示失败

    public static final String OTHER_ERROR = "3"; // 3表示其他错误

    public static final String newFailure(String msg) {
        if (msg == null) {
            return FAILURE;
        }
        StringBuilder buf = new StringBuilder(1 + msg.length());
        buf.append(FAILURE).append(msg);
        return buf.toString();
    }

    public static final String newFailure(String code, String msg) {
        if (code == null) {
            code = FAILURE;
        }
        if (msg == null) {
            return code;
        }
        StringBuilder buf = new StringBuilder(1 + msg.length());
        buf.append(code).append(msg);
        return buf.toString();
    }
}
