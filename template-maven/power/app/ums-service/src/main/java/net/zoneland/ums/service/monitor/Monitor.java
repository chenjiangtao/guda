/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package net.zoneland.ums.service.monitor;

import java.net.URL;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import net.zoneland.gateway.util.Base64;
import net.zoneland.ums.biz.msg.queue.QueueStatistic;
import net.zoneland.ums.common.util.Host;
import net.zoneland.ums.common.util.helper.DateHelper;
import net.zoneland.ums.service.webservice.form.SendRequest;

import org.codehaus.xfire.client.Client;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;

/**
 * 
 * @author gang
 * @version $Id: Monitor.java, v 0.1 2013-1-29 下午12:59:26 gang Exp $
 */
public class Monitor {

    private String         wsdl = "http://localhost:9081/ums/services/MsgService?wsdl";

    @Autowired
    private QueueStatistic queueStatistic;

    public Monitor() {
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new MonitorTask(), 30 * 1000, 60 * 1000 * 60);
    }

    class MonitorTask extends TimerTask {

        /** 
         * @see java.util.TimerTask#run()
         */
        @Override
        public void run() {
            try {
                int hour = getHour();
                if (hour > 7 && hour < 22) {
                    sendMonitor();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void sendMonitor() {
        SendRequest sr = new SendRequest();
        sr.setAppId("5555");
        sr.setSubAppId("");
        sr.setRecvId("13588754574,13566402695");
        sr.setContent(DateHelper.getStrDateTime() + "[" + Host.getHost() + "]monitor:"
                      + queueStatistic.getSysInfo());
        sr.setAck("0");
        sr.setReply("");
        sr.setPriority("8");
        Gson gson = new Gson();
        String json = gson.toJson(sr);
        try {
            Client client = new Client(new URL(wsdl));
            client.addOutHandler(new ClientAuthenticationHandler("5555", "1111"));
            client.invoke("sendMsgJson",
                new Object[] { new String(Base64.encode(json.getBytes("utf-8"))) });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static int getHour() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

}
