/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.service.json.test;

import java.io.Serializable;
import java.util.Date;

import net.zoneland.ums.common.util.helper.DateHelper;

/**
 * 
 * @author gag
 * @version $Id: MsgSend.java, v 0.1 2012-8-27 下午5:34:08 gag Exp $
 */
public class MsgSend implements Serializable {

    /**  */
    private static final long serialVersionUID = -1976772916981829941L;

    private String            sendUser         = "1358812121212";

    private String            receiveUser      = "1888812121212";

    private String            content          = "测试内容";

    private String            status           = "1";

    private String            flowNo           = "flowNo";

    private String            sendDate         = DateHelper.getStrDateTime(new Date());

    private String            createDate       = DateHelper.getStrDateTime(new Date());

    public String getSendUser() {
        return sendUser;
    }

    public void setSendUser(String sendUser) {
        this.sendUser = sendUser;
    }

    public String getReceiveUser() {
        return receiveUser;
    }

    public void setReceiveUser(String receiveUser) {
        this.receiveUser = receiveUser;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFlowNo() {
        return flowNo;
    }

    public void setFlowNo(String flowNo) {
        this.flowNo = flowNo;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

}
