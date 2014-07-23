/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.service.mail;

import java.util.Date;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * 
 * @author gag
 * @version $Id: MailSenderHelper.java, v 0.1 2012-8-29 上午8:26:46 gag Exp $
 */
public class MailSenderHelper {

    @Autowired
    private JavaMailSender mailSender;

    private String         umsEmail;

    public void sendMessage(String to, String retCode, String msgID, int ack, String receive,
                            String from, String receiveDate, String receiveTime, String content)
                                                                                                throws AddressException,
                                                                                                MessagingException {
        StringBuilder sb = new StringBuilder();
        sb.append("[RetCode=").append(retCode).append("] ").append("[Ack=").append(ack)
            .append("] ").append("[Receive=").append(receive).append("] ").append("[From=")
            .append(from).append("] ");

        // 填充发送内容
        MimeMessage msg = mailSender.createMimeMessage();
        msg.setFrom(new InternetAddress(umsEmail));
        InternetAddress[] address = { new InternetAddress(to) };
        msg.setRecipients(Message.RecipientType.TO, address);
        msg.setSubject(sb.toString());
        StringBuilder msgContent = new StringBuilder("");
        //填充内容
        msgContent.append("[MsgID]").append(msgID).append("\n");
        msgContent.append("[SubmitTime]").append(receiveDate).append(" ").append(receiveTime)
            .append("\n");
        msgContent.append("[msg]").append(content).append("  ");
        msg.setContent(msgContent.toString(), "text/plain; charset=GBK");
        msg.setSentDate(new Date());
        msg.saveChanges();
        mailSender.send(msg);

    }

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void setUmsEmail(String umsEmail) {
        this.umsEmail = umsEmail;
    }

}
