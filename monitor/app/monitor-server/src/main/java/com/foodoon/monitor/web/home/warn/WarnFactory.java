/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.foodoon.monitor.web.home.warn;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.foodoon.monitor.web.home.warn.modem.ModemMessage;
import com.foodoon.monitor.web.home.warn.modem.ModemProxy;

/**
 * 
 * @author foodoon
 * @version $Id: WarnFactory.java, v 0.1 2013-6-21 下午9:55:39 foodoon Exp $
 */
public class WarnFactory {

    private final static Logger                 logger = LoggerFactory.getLogger(WarnFactory.class);

    public static LinkedHashMap<String, String> mail   = new LinkedHashMap<String, String>(1000);

    public static Map<String, String>           sms    = new LinkedHashMap<String, String>(1000);

    @Autowired
    private MailService                         mailService;

    @Autowired
    private ModemProxy                          modemProxy;

    public WarnFactory() {
        Timer timer = new Timer();
        timer.schedule(new SendTask(), 30000, 60000);
    }

    public static void putMail(String to, String content) {
        if (mail.size() > 1000) {
            Iterator<String> iterator = mail.keySet().iterator();
            mail.remove(iterator.next());
        }
        String c = mail.get(to);
        if (c == null) {
            mail.put(to, content);
        } else {
            mail.put(to, content + "   <br>  " + c);
        }
    }

    public static String[] removeFirstMail() {
        if (mail.size() > 0) {
            Iterator<String> iterator = mail.keySet().iterator();
            String k = iterator.next();
            String v = mail.get(k);
            mail.remove(k);
            return new String[] { k, v };
        }
        return null;
    }

    public static void putSms(String to, String content) {
        if (sms.size() > 1000) {
            Iterator<String> iterator = sms.keySet().iterator();
            sms.remove(iterator.next());
        }
        String c = sms.get(to);
        if (c == null) {
            sms.put(to, content);
        } else {
            sms.put(to, content + "   " + c);
        }
    }

    public static String[] removeFirstSms() {
        if (sms.size() > 0) {
            Iterator<String> iterator = sms.keySet().iterator();
            String k = iterator.next();
            String v = sms.get(k);
            sms.remove(k);
            return new String[] { k, v };
        }
        return null;
    }

    class SendTask extends TimerTask {

        @Override
        public void run() {
            try {
                runSendTask();
            } catch (Exception e) {

            }
        }
    }

    public void runSendTask() {
        String[] mail = removeFirstMail();
        if (mail != null) {
            try {
                mailService.sendMail("monitor", mail[1], new String[] { mail[0] });
            } catch (MessagingException e) {
                logger.error("", e);
            }
        }

        String[] sms = removeFirstSms();
        if (sms != null) {
            try {
                if (!modemProxy.isStart()) {
                    modemProxy.doInit();
                }
                ModemMessage mm = new ModemMessage();
                mm.setContent(sms[1]);
                mm.setMobilePhone(sms[0]);
                modemProxy.send(mm);
            } catch (Exception e) {
                logger.error("", e);
            }
        }
    }

}
