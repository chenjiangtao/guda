package net.zoneland.ums.common.mysql.dataobject;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class InError extends InErrorKey {
    private Byte   sequenceno;

    private String retcode;

    private String errmsg;

    private Byte   docount;

    private String appid;

    private String appserialno;

    private String mediaid;

    private String sendid;

    private String recvid;

    private Date   submittime;

    private Date   finishtime;

    private String content;

    private Byte   ack;

    private String reply;

    private Byte   msgtype;

    private String subapp;

    public Byte getSequenceno() {
        return sequenceno;
    }

    public void setSequenceno(Byte sequenceno) {
        this.sequenceno = sequenceno;
    }

    public String getRetcode() {
        return retcode;
    }

    public void setRetcode(String retcode) {
        this.retcode = retcode == null ? null : retcode.trim();
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg == null ? null : errmsg.trim();
    }

    public Byte getDocount() {
        return docount;
    }

    public void setDocount(Byte docount) {
        this.docount = docount;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid == null ? null : appid.trim();
    }

    public String getAppserialno() {
        return appserialno;
    }

    public void setAppserialno(String appserialno) {
        this.appserialno = appserialno == null ? null : appserialno.trim();
    }

    public String getMediaid() {
        return mediaid;
    }

    public void setMediaid(String mediaid) {
        this.mediaid = mediaid == null ? null : mediaid.trim();
    }

    public String getSendid() {
        return sendid;
    }

    public void setSendid(String sendid) {
        this.sendid = sendid == null ? null : sendid.trim();
    }

    public String getRecvid() {
        return recvid;
    }

    public void setRecvid(String recvid) {
        this.recvid = recvid == null ? null : recvid.trim();
    }

    public Date getSubmittime() {
        return submittime;
    }

    public void setSubmittime(Date submittime) {
        this.submittime = submittime;
    }

    public Date getFinishtime() {
        return finishtime;
    }

    public void setFinishtime(Date finishtime) {
        this.finishtime = finishtime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Byte getAck() {
        return ack;
    }

    public void setAck(Byte ack) {
        this.ack = ack;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply == null ? null : reply.trim();
    }

    public Byte getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(Byte msgtype) {
        this.msgtype = msgtype;
    }

    public String getSubapp() {
        return subapp;
    }

    public void setSubapp(String subapp) {
        this.subapp = subapp == null ? null : subapp.trim();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}