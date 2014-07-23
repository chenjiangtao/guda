/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.msg.socket.process;

import java.util.List;

import net.zoneland.ums.biz.config.admin.BlackListBiz;
import net.zoneland.ums.biz.msg.socket.ServiceRequest;
import net.zoneland.ums.biz.msg.socket.SocketContext;
import net.zoneland.ums.biz.user.UmsUserInfoBiz;
import net.zoneland.ums.common.dal.UmsMsgOutMapper;
import net.zoneland.ums.common.dal.bo.UmsMsgOutStat;
import net.zoneland.ums.common.util.helper.DateHelper;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author gag
 * @version $Id: AdminProcess.java, v 0.1 2012-8-29 下午5:24:12 gag Exp $
 */
public class AdminProcess {

    public static final int ID_OK         = 1;

    public static final int ID_QUITSYSTEM = 2;

    public static final int ID_ERROR      = -1;

    public static final int ID_UNKNOWN    = 0;

    @Autowired
    private BlackListBiz    blackListBiz;

    @Autowired
    private SocketProcess   sendMsgProcess;

    @Autowired
    private UmsUserInfoBiz  umsUserInfoBiz;

    @Autowired
    private UmsMsgOutMapper umsMsgOutMapper;

    public int parse(String requestMsg, String from, String appid) {
        if (requestMsg.matches("0000") || requestMsg.matches("00000")) {
            refuseRequest(from, appid, true);

            return ID_OK;
        } else if (requestMsg.matches("1111") || requestMsg.matches("11111")) {
            refuseRequest(from, appid, false);

            return ID_OK;
        }
        if (!umsUserInfoBiz.isAdmin(from)) {
            return ID_UNKNOWN;
        }
        requestMsg = StringUtils.trim(requestMsg);
        if (requestMsg.matches("系统状态")) {
            sendOut(buildSystemStatus(), from);
            return ID_OK;
        }
        // 发送测试4或者21编码的数据包
        if (requestMsg.startsWith("DATA ")) {
            String[] flds = requestMsg.split(" +");
            sendOut(flds[2], flds[1], "ADMIN", 21);
            return ID_OK;
        }

        if (requestMsg.matches("退出系统") || requestMsg.equalsIgnoreCase("shutdown")) {
            sendOut(String.format("系统因你的命令而退出运行。"), from);
            return ID_QUITSYSTEM;
        }
        return ID_UNKNOWN;
    }

    private String buildSystemStatus() {

        int initCount = umsMsgOutMapper.getInitMsgOut();
        int total = umsMsgOutMapper.getAllMsgOut();
        String msg;
        msg = String.format("%s系统状态：已连接的应用%d个，接口连接%d个,in_ready表%d条记录,待发短信%d条(%s)",
            DateHelper.getStrCurrentDate("dd日HH:mm:ss"), SocketContext.getLoginAppCount(),
            SocketContext.getConnectCount(), initCount, total, getOutReadyDetail());
        return msg;
    }

    private String getOutReadyDetail() {

        List<UmsMsgOutStat> list = umsMsgOutMapper.selectMsgOutStat();
        String detail = "";
        for (UmsMsgOutStat obj : list) {
            detail += String.format("%s渠道%s有%d条", detail.length() > 0 ? "," : "", obj.getMediaId(),
                obj.getCount());
        }
        return detail;

    }

    void sendOut(String content, String to) {
        this.sendOut(content, to, "ADMIN");
    }

    void sendOut(String content, String to, String appid) {
        this.sendOut(content, to, appid, 15);

    }

    void sendOut(String content, String to, String appId, int msgType) {
        ServiceRequest sr = new ServiceRequest();
        if (appId != null) {
            sr.setAppId(appId);
        } else {
            sr.setAppId("ADMIN");
        }
        sr.setMsg(content);
        sr.setUmsTo(to);
        sr.setAck(0);
        sr.setMsgType(msgType);
        sendMsgProcess.doProcess(sr);

    }

    void refuseRequest(String from, String appId, boolean refused) {

        if (refused) {
            blackListBiz.insertBlackListByNum(from, appId);
            sendOut("退订成功，您将不再会收到本号码的任何短信。重新开通请发送1111到本号码。谢谢您的使用，再见！", from, appId);
        } else {
            blackListBiz.deleteBlackList(from, appId);
            sendOut("开通成功！退订请发送0000到本号码。谢谢您的使用！", from, appId);
        }
    }

    public void setBlackListBiz(BlackListBiz blackListBiz) {
        this.blackListBiz = blackListBiz;
    }

    public void setSendMsgProcess(SocketProcess sendMsgProcess) {
        this.sendMsgProcess = sendMsgProcess;
    }

    public void setUmsUserInfoBiz(UmsUserInfoBiz umsUserInfoBiz) {
        this.umsUserInfoBiz = umsUserInfoBiz;
    }

    public void setUmsMsgOutMapper(UmsMsgOutMapper umsMsgOutMapper) {
        this.umsMsgOutMapper = umsMsgOutMapper;
    }

}
