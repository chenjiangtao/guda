/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.msg.socket;

/**
 * 
 * @author gag
 * @version $Id: CodeConstants.java, v 0.1 2012-8-27 上午9:39:16 gag Exp $
 */
public class CodeConstants {

    public static final String SUCCESS            = "0000";

    public static final String FAILURE            = "0001";

    public static final String MAIL_LOGIN_FAIL    = "2003";

    public static final String MAIL_CONTENT_ERROR = "2001";

    public static final String MAIL_OTHER_ERROR   = "2001";

    /** 流量超过阀值 */
    public static final String FLOW_FAILURE       = "0003";
    
    /**
     * 内容含有非法关键词
     */
    public static final String KEYWORD_FAILURE       = "0004";
    
    public static final String RECV_FAILURE       = "0005";
    
    public static final String ARG_FAILURE       = "0002";
    
    public static final String OTHER_FAILURE       = "0006";


    public static final String FREQUENCY_FAILURE  = "0011";

    /** 密码验证请求 */
    public static final String REQUEST_1001       = "1001";

    /** 链接保持请求 */
    public static final String REQUEST_1003       = "1003";

    /** 发送消息请求 */
    public static final String REQUEST_1002       = "1002";

    /** 发送消息请求 */
    public static final String REQUEST_2002       = "2002";

    /** 发送消息请求 */
    public static final String REQUEST_3002       = "3002";

    /** 发送消息请求 */
    public static final String REQUEST_3011       = "3011";

    /** 取消息请求 */
    public static final String REQUEST_1004       = "1004";

    /** 取消息请求 */
    public static final String REQUEST_3004       = "3004";

    /** 取消息请求 */
    public static final String REQUEST_3012       = "3012";

    /** 取消息回执请求 */
    public static final String REQUEST_1005       = "1005";

    public static final String newFailure(String msg) {
        if (msg == null) {
            return FAILURE;
        }
        StringBuilder buf = new StringBuilder(4 + msg.length());
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
        StringBuilder buf = new StringBuilder(4 + msg.length());
        buf.append(code).append(msg);
        return buf.toString();
    }

}
