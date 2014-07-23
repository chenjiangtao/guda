/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.service.mail.test;

import javax.mail.FetchProfile;
import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;

import net.zoneland.ums.service.mail.MailReceiveHelper;

/**
 * 
 * @author gag
 * @version $Id: MailReceiveTest.java, v 0.1 2012-8-29 上午8:55:42 gag Exp $
 */
public class MailReceiveTest {

    public static void main(String[] args) throws MessagingException {
        MailReceiveHelper r = new MailReceiveHelper();
        r.javaMailProperties.put("mail.smtp.auth", "true");
        r.javaMailProperties.put("mail.smtp.timeout", "25000");

        Session session = Session.getDefaultInstance(r.javaMailProperties, null);
        Store store = session.getStore("pop3");

        store.connect("pop3.163.com", "gang0119", "gavin1217");
        // URLName urlname = new URLName("pop3", "smtp.163.com", 25, null, "gang0119@163.com",
        //         "gavin1217");
        // Store store = session.getStore(urlname);
        // store.connect();
        Folder folder = store.getDefaultFolder();
        folder = folder.getFolder("INBOX");
        folder.open(Folder.READ_WRITE);
        javax.mail.Message[] msgs = folder.getMessages();
        FetchProfile fp = new FetchProfile();
        fp.add(FetchProfile.Item.ENVELOPE);
        fp.add(FetchProfile.Item.FLAGS);
        fp.add("X-Mailer");
        folder.fetch(msgs, fp);
        //int recv_ok = 0;
        // for (int i = 0; i < msgs.length; i++) {
        int i = 0;
        StringBuffer content = new StringBuffer();
        r.getMessageText(content, msgs[i]);
        String messageStr = content.toString().trim();
        String subject = msgs[i].getSubject().trim();
        String from = ((InternetAddress) msgs[i].getFrom()[0]).getAddress();
        System.out.println(from);
        System.out.println(subject);
        System.out.println(messageStr);
        // }
    }

}
