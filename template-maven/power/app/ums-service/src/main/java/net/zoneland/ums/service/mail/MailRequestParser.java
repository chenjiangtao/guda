/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.service.mail;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.zoneland.ums.biz.msg.socket.ServiceRequest;



/**
 * 
 * @author gang
 * @version $Id: MailRequestParser.java, v 0.1 2012-8-28 下午9:24:12 gang Exp $
 */
public class MailRequestParser {

    /**
     * 解析邮件内容
     * @param subject
     * @param content
     * @return
     */
    public static ServiceRequest parser(String subject, String content) {
        ServiceRequest sr = new ServiceRequest();
        try {
        	String temp[] = subject.split("\\[");
           for(int j =0;j<temp.length;++j){
            String[] itemStrs = temp[j].split("]");
            for (int i = 0; i < itemStrs.length; i++) {
                String itemStr = itemStrs[i];
                String[] item = itemStr.split("=");
                if (item.length == 2) {
                    setVal(item[0], item[1], sr);
                }
            }
            
           }
           String[] itemStrs = content.split("\\[");
            
            for (int i = 0; i < itemStrs.length; i++) {
                String itemStr = itemStrs[i];
                String[] item = itemStr.split("]");
                if (item.length == 2) {
                    setVal(item[0], item[1], sr);
                }
            }
            
            sr.setRequestCode("1002");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sr;
    }

    private static void setVal(String key, String val, ServiceRequest sr) {
        key = key.toUpperCase();
        if (val != null) {
            val = val.trim();
            if ("APP".equalsIgnoreCase(key)) {
                sr.setAppId(val);
            } else if ("PASS".equalsIgnoreCase(key)) {
                sr.setPassword(val);
            } else if ("MSG".equalsIgnoreCase(key)) {
                sr.setMsg(val);
            } else if ("UMS_TO".equalsIgnoreCase(key)) {
                sr.setUmsTo(val);
            } else if ("ID".equalsIgnoreCase(key)) {
                sr.setId(val);
            } else if ("SUBTYPE".equalsIgnoreCase(key)) {
                sr.setSubType(val);
            } else if ("MESSAGETYPE".equalsIgnoreCase(key)) {
                sr.setMsgType(getIntVal(val));
            } else if ("ACK".equalsIgnoreCase(key)) {
                sr.setAck(getIntVal(val));
            } else if ("REPLY".equalsIgnoreCase(key)) {
                sr.setReply(val);
            } else if ("PRIORITY".equalsIgnoreCase(key)) {
                sr.setPriority(getIntVal(val));
            } else if ("REP".equalsIgnoreCase(key)) {
                sr.setRep(getIntVal(val));
            }
        }
    }

    private static int getIntVal(String val) {
        try {
            int v = Integer.parseInt(val);
            return v;
        } catch (Exception e) {

        }
        return 0;
    }

}
