package net.zoneland.ums.common.mysql.dataobject;

import java.util.Date;

public class OutReply extends OutReplyKey {

    private Byte    sequenceno;

    private String  retcode;

    private String  errmsg;

    private Byte    statusflag;

    private String  appid;

    private String  appserialno;

    private String  mediaid;

    private String  sendid;

    private String  recvid;

    private Date    submittime;

    private Date    finishtime;

    private Short   rep;

    private Byte    docount;

    private Byte    priority;

    private String  batchmode;

    private Boolean contentmode;

    private String  content;

    private String  timesetflag;

    private Date    settime;

    private Byte    ack;

    private String  replydes;

    private String  reply;

    private Short   fee;

    private Byte    feetype;

    private String  subapp;

    private String  msgid;

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

    public Byte getStatusflag() {
        return statusflag;
    }

    public void setStatusflag(Byte statusflag) {
        this.statusflag = statusflag;
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

    public Short getRep() {
        return rep;
    }

    public void setRep(Short rep) {
        this.rep = rep;
    }

    public Byte getDocount() {
        return docount;
    }

    public void setDocount(Byte docount) {
        this.docount = docount;
    }

    public Byte getPriority() {
        return priority;
    }

    public void setPriority(Byte priority) {
        this.priority = priority;
    }

    public String getBatchmode() {
        return batchmode;
    }

    public void setBatchmode(String batchmode) {
        this.batchmode = batchmode == null ? null : batchmode.trim();
    }

    public Boolean getContentmode() {
        return contentmode;
    }

    public void setContentmode(Boolean contentmode) {
        this.contentmode = contentmode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getTimesetflag() {
        return timesetflag;
    }

    public void setTimesetflag(String timesetflag) {
        this.timesetflag = timesetflag == null ? null : timesetflag.trim();
    }

    public Date getSettime() {
        return settime;
    }

    public void setSettime(Date settime) {
        this.settime = settime;
    }

    public Byte getAck() {
        return ack;
    }

    public void setAck(Byte ack) {
        this.ack = ack;
    }

    public String getReplydes() {
        return replydes;
    }

    public void setReplydes(String replydes) {
        this.replydes = replydes == null ? null : replydes.trim();
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply == null ? null : reply.trim();
    }

    public Short getFee() {
        return fee;
    }

    public void setFee(Short fee) {
        this.fee = fee;
    }

    public Byte getFeetype() {
        return feetype;
    }

    public void setFeetype(Byte feetype) {
        this.feetype = feetype;
    }

    public String getSubapp() {
        return subapp;
    }

    public void setSubapp(String subapp) {
        this.subapp = subapp == null ? null : subapp.trim();
    }

    public String getMsgid() {
        return msgid;
    }

    public void setMsgid(String msgid) {
        this.msgid = msgid == null ? null : msgid.trim();
    }
}