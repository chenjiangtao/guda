/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.service.mail;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.FetchProfile;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import net.zoneland.ums.biz.msg.socket.CodeConstants;
import net.zoneland.ums.biz.msg.socket.ServiceRequest;
import net.zoneland.ums.biz.msg.socket.process.ProcessResult;
import net.zoneland.ums.service.mail.process.MailProcess;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

/**
 *
 * @author gag
 * @version $Id: MailReceiveHelper.java, v 0.1 2012-8-28 下午5:41:59 gag Exp $
 */
public class MailReceiveHelper {

    private static final Logger logger             = Logger.getLogger(MailReceiveHelper.class);

    public static final String  DEFAULT_PROTOCOL   = "pop3";

    public Properties           javaMailProperties = new Properties();

    private String              host;

    private String              username;

    private String              password;

    @Autowired
    private MailProcess         mailProcess;

    @Autowired
    private MailSenderHelper    mailSenderHelper;

    public void receiveAndProcessMsg() {
        Store store = null;
        Folder folder = null;
        try {

            Session session = Session.getDefaultInstance(System.getProperties(), null);
            store = session.getStore(DEFAULT_PROTOCOL);
            store.connect(host, username, password);
            folder = store.getDefaultFolder();
            folder = folder.getFolder("INBOX");
            folder.open(Folder.READ_WRITE);
            int count = folder.getMessageCount();
            for (int i = 0; i < count; ++i) {
                javax.mail.Message msg = folder.getMessage(i + 1);
                if (msg.getSize() > 50000 || msg.getSize() == -1) {
                    logger.warn("邮件处理内容过大，删除,邮件大小:" + msg.getSize());
                    msg.setFlag(Flags.Flag.DELETED, true);
                    return;
                }
            }
            javax.mail.Message[] msgs = folder.getMessages();
            msgs = folder.getMessages();
            FetchProfile fp = new FetchProfile();
            fp.add(FetchProfile.Item.ENVELOPE);
            fp.add(FetchProfile.Item.FLAGS);
            fp.add("X-Mailer");
            folder.fetch(msgs, fp);
            for (int i = 0; i < msgs.length; i++) {
                if (msgs[i].getSize() > 50000 || msgs[i].getSize() == -1) {
                    logger.warn("邮件处理内容过大，删除,邮件大小:" + msgs[i].getSize());
                    msgs[i].setFlag(Flags.Flag.DELETED, true);
                }
                String from = null;
                try {
                    from = ((InternetAddress) msgs[i].getFrom()[0]).getAddress();
                } catch (MessagingException e) {
                    logger.error("邮件发送方解析错误", e);
                }
                StringBuffer content = new StringBuffer();
                getMessageText(content, msgs[i]);
                String messageStr = content.toString().trim();
                String subject = msgs[i].getSubject();
                if (subject == null) {
                    logger.warn("邮件处理失败，邮件主题为空，:" + content);
                    msgs[i].setFlag(Flags.Flag.DELETED, true);
                    continue;
                }
                subject = subject.trim();
                logger.info("收取邮件内容context:" + messageStr);
                ServiceRequest sr = MailRequestParser.parser(subject, messageStr);
                logger.warn("邮件内容解析后:" + sr);
                if (sr.getRequestCode() == null) {
                    //解析错误，回复邮件
                    logger.warn("邮件内容格式错误:" + content);
                } else {
                    //登录
                    if (StringUtils.hasText(sr.getMsg())) {
                        ProcessResult pr = mailProcess.processMsg(sr);
                        logger.warn("邮件消息处理结果:" + pr);
                        if (!pr.isSuccess()) {
                            String msg = "";
                            if (CodeConstants.MAIL_LOGIN_FAIL.equals(pr.getMsg())) {
                                msg = "应用密码不正确。请检查后，重新发送!";
                            } else {
                                msg = "您填写的邮件无发送内容。请检查后，重新发送!";
                            }
                            logger.warn("邮件接口处理消息失败，发送回执邮件,收件人:" + from);
                            //                            if (from != null && !from.equals(username)) {
                            //                                sendResponse(pr.getMsg(), from, sr.getId(), sr.getUmsTo(),
                            //                                    "UMS中心:" + msg + "\n原内容：\n" + content);
                            //                            }
                        } else {
                            logger.warn("邮件接口处理成功");
                        }
                    } else {
                        //                        logger.warn("邮件接口处理消息失败，邮件没有内容，发送回执邮件,收件人:" + from);
                        //                        sendResponse("2001", from, sr.getId(), sr.getUmsTo(),
                        //                            "UMS中心:您填写的邮件无发送内容。请检查后，重新发送!\n原内容：\n" + content);
                    }
                }
                msgs[i].setFlag(Flags.Flag.DELETED, true);
            }
        } catch (Exception e) {
            logger.error("读取邮件内容错误。host:" + host + ",username:" + username + ",password:"
                         + password, e);
        } finally {
            try {
                if (folder != null) {
                    folder.close(true);
                    folder = null;
                }
                if (store != null) {
                    store.close();
                    store = null;
                }
            } catch (Exception ex2) {
                logger.error("关闭邮箱文件夹错误。", ex2);
            }
        }

    }

    private void sendResponse(String retCode, String to, String id, String recvId, String msg) {
        try {
            mailSenderHelper.sendMessage(to, retCode, id, 0, recvId, "", "", "", msg);
        } catch (AddressException e) {
            logger.error("发送邮件错误。", e);
        } catch (MessagingException e) {
            logger.error("发送邮件错误。", e);
        }
    }

    public StringBuffer getMessageText(StringBuffer buf, Part part) {
        try {
            Object content = getPartContent(part);
            logger.info("mail content gbk:" + ",type" + part.getContentType() + "-"
                        + new String(((String) content).getBytes("GBK")));
            logger.info("mail content utf-8:" + ",type" + part.getContentType() + "-"
                        + new String(((String) content).getBytes("utf-8")));
            logger.info("mail content gb18030:" + ",type" + part.getContentType() + "-"
                        + new String(((String) content).getBytes("gb18030")));
            if (content == null) {
            } else if (content instanceof Multipart) {
                Multipart mPart = (Multipart) content;
                int partCount = mPart.getCount();
                for (int i = 0; i < partCount; i++) {
                    this.getMessageText(buf, mPart.getBodyPart(i));
                }
            } else if (content instanceof String) {
                if (part.getContentType().toLowerCase().indexOf("text/plain") >= 0) {
                    if (part.getContentType().toLowerCase().indexOf("gb2312") >= 0
                        || part.getContentType().toLowerCase().indexOf("gbk") >= 0) {

                        buf.append(new String(((String) content).getBytes("GBK")));

                    } else if (part.getContentType().toLowerCase().indexOf("utf-8") >= 0) {
                        buf.append(new String(((String) content).getBytes("UTF-8")));
                    } else if (part.getContentType().toLowerCase().indexOf("gb18030") >= 0) {
                        buf.append(((String) content));
                    } else {
                        buf.append(native2Unicode((String) content));
                    }

                }
            }
        } catch (Exception ex) {
            logger.error("获取邮件内容错误", ex);
        }
        return buf;
    }

    public static String native2Unicode(String str) {
        String ustr = "";
        if (str == null) {
            return null;
        }
        try {
            ustr = new String(str.getBytes("iso-8859-1"), "GBK");
        } catch (UnsupportedEncodingException ex) {
            logger.error("获取邮件内容错误,编码不支持", ex);
            return str;
        }
        return ustr;
    }

    public Object getPartContent(Part part) throws MessagingException {
        Object result = null;

        try {
            result = part.getContent();
        } catch (IllegalArgumentException ex) {
            throw new MessagingException("content charset is not recognized: " + ex.getMessage());
        } catch (IOException ex) {
            throw new MessagingException("getPartContent(): " + ex.getMessage());
        }
        return result;
    }

    public void setJavaMailProperties(Properties javaMailProperties) {
        this.javaMailProperties = javaMailProperties;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMailSenderHelper(MailSenderHelper mailSenderHelper) {
        this.mailSenderHelper = mailSenderHelper;
    }

    public void setMailProcess(MailProcess mailProcess) {
        this.mailProcess = mailProcess;
    }
}
