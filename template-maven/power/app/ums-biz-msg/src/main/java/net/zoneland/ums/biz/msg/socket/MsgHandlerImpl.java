/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.msg.socket;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import net.zoneland.ums.biz.msg.flow.FlowControl;
import net.zoneland.ums.biz.msg.socket.check.Check;
import net.zoneland.ums.biz.msg.socket.process.ProcessResult;
import net.zoneland.ums.biz.msg.socket.process.SocketProcess;
import net.zoneland.ums.common.util.helper.StringHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author gag
 * @version $Id: MsgHandlerImpl.java, v 0.1 2012-8-28 下午1:29:58 gag Exp $
 */
public class MsgHandlerImpl implements MsgHandler {

    private final Logger  logger = LoggerFactory.getLogger(MsgHandlerImpl.class);

    @Autowired
    private FlowControl   flowControl;

    @Autowired
    private SocketProcess sendMsgProcess;

    @Autowired
    private SocketProcess fetchMsgProcess;

    @Autowired
    private SocketProcess loginProcess;

    @Autowired
    private SocketProcess fetchMsgResponseProcess;

    @Autowired
    private SocketProcess activeTestProcess;

    private List<Check>   checkers;

    /**
     * @see net.zoneland.ums.biz.msg.socket.MsgHandler#processMsg(net.zoneland.ums.biz.msg.socket.ServiceRequest)
     */
    public ProcessResult processMsg(ServiceRequest sr, String loginAppId) {
        //控制取消息速度
        //        if (sr != null
        //            && (CodeConstants.REQUEST_1004.equals(sr.getRequestCode())
        //                || CodeConstants.REQUEST_3004.equals(sr.getRequestCode()) || CodeConstants.REQUEST_3012
        //                    .equals(sr.getRequestCode()))) {
        //            if (!fetchMsgChecker.checker(sr.getAppId())) {
        //                logger.warn("fech frequency!取消息请求[" + sr + "]过于频繁.");
        //                ProcessResult pr = new ProcessResult(true, CodeConstants.newFailure(
        //                    CodeConstants.FREQUENCY_FAILURE, "请求过于频繁"));
        //                pr.setUuid(sr.getUuid());
        //                return pr;
        //            }
        //        }
        if (checkers != null) {
            Iterator<Check> it = checkers.iterator();
            while (it.hasNext()) {
                Check c = it.next();
                ProcessResult pr = c.check(sr);
                if (!pr.isSuccess()) {
                    logger.warn("请求[" + sr + "]前置检查失败,错误:[" + pr.getMsg() + "].");
                    pr = new ProcessResult(true, CodeConstants.newFailure(CodeConstants.FAILURE,
                        pr.getMsg()));
                    pr.setUuid(sr.getUuid());
                    return pr;
                }
            }
        }
        logger.info("请求[" + sr + "]前置检查通过。");
        if (CodeConstants.REQUEST_1002.equals(sr.getRequestCode())
            || CodeConstants.REQUEST_2002.equals(sr.getRequestCode())
            || CodeConstants.REQUEST_3002.equals(sr.getRequestCode())
            || CodeConstants.REQUEST_3011.equals(sr.getRequestCode())) {
            String[] recvId = StringHelper.splitRecvId(sr.getUmsTo());

            int flow = flowControl.checkFlow(sr.getAppId(), recvId.length);
            if (flow > 0) {
                // 超过流量
                String result = String.valueOf(flow).substring(0, 1);
                flow = Integer.valueOf(String.valueOf(flow).substring(1));
                if ("1".equalsIgnoreCase(result)) {
                    ProcessResult pr = new ProcessResult(true, CodeConstants.newFailure(
                        CodeConstants.FLOW_FAILURE, "日流量超过阀值，超过数量" + flow));
                    pr.setUuid(sr.getUuid());
                    return pr;
                } else if ("2".equalsIgnoreCase(result)) {
                    ProcessResult pr = new ProcessResult(true, CodeConstants.newFailure(
                        CodeConstants.FLOW_FAILURE, "月流量超过阀值，超过数量" + flow));
                    pr.setUuid(sr.getUuid());
                    return pr;
                } else {
                    ProcessResult pr = new ProcessResult(true, CodeConstants.newFailure(
                        CodeConstants.FLOW_FAILURE, "流量超过阀值。"));
                    pr.setUuid(sr.getUuid());
                    return pr;
                }

            } else {
                // 其他检查,如定时发送时间是否正确
                if (validSendMsg(sr)) {
                    ProcessResult pr = sendMsgProcess.doProcess(sr);
                    pr.setUuid(sr.getUuid());
                    return pr;
                }
                ProcessResult pr = new ProcessResult(true, CodeConstants.newFailure(
                    CodeConstants.FAILURE, "消息定时发送时间或者有效性时间不正确"));
                pr.setUuid(sr.getUuid());
                return pr;

            }

        } else if (CodeConstants.REQUEST_1004.equals(sr.getRequestCode())
                   || CodeConstants.REQUEST_3004.equals(sr.getRequestCode())
                   || CodeConstants.REQUEST_3012.equals(sr.getRequestCode())) {
            // 请求获取消息
            // 判断当前登录appid是否一致
            if (sr.getAppId().equals(loginAppId)) {
                ProcessResult pr = fetchMsgProcess.doProcess(sr);
                pr.setUuid(sr.getUuid());
                return pr;
            } else {
                ProcessResult pr = new ProcessResult(true, CodeConstants.newFailure(
                    CodeConstants.FAILURE, "获取消息的appID与当前登录appID不一致"));
                pr.setUuid(sr.getUuid());
                return pr;
            }

        } else if (CodeConstants.REQUEST_1005.equals(sr.getRequestCode())) {
            // 取消息回执
            ProcessResult pr = fetchMsgResponseProcess.doProcess(sr);
            pr.setUuid(sr.getUuid());
            return pr;
        } else if (CodeConstants.REQUEST_1001.equals(sr.getRequestCode())) {
            // 登录，密码验证
            ProcessResult pr = loginProcess.doProcess(sr);
            pr.setUuid(sr.getUuid());
            return pr;
        } else if (CodeConstants.REQUEST_1003.equals(sr.getRequestCode())) {
            // 链接保持
            ProcessResult pr = activeTestProcess.doProcess(sr);
            pr.setUuid(sr.getUuid());
            return pr;
        } else {
            ProcessResult pr = new ProcessResult(true, CodeConstants.newFailure(
                CodeConstants.FAILURE, "未知请求类型。"));
            pr.setUuid(sr.getUuid());
            return pr;
        }
    }

    private boolean validSendMsg(ServiceRequest sr) {
        if (sr.getSendDate() != null && sr.getSendDate().after(new Date())) {
            return false;
        }
        if (sr.getValidTime() < 0) {
            return false;
        }
        return true;
    }

    public void setFlowControl(FlowControl flowControl) {
        this.flowControl = flowControl;
    }

    public void setSendMsgProcess(SocketProcess sendMsgProcess) {
        this.sendMsgProcess = sendMsgProcess;
    }

    public void setFetchMsgProcess(SocketProcess fetchMsgProcess) {
        this.fetchMsgProcess = fetchMsgProcess;
    }

    public void setLoginProcess(SocketProcess loginProcess) {
        this.loginProcess = loginProcess;
    }

    public void setFetchMsgResponseProcess(SocketProcess fetchMsgResponseProcess) {
        this.fetchMsgResponseProcess = fetchMsgResponseProcess;
    }

    public void setActiveTestProcess(SocketProcess activeTestProcess) {
        this.activeTestProcess = activeTestProcess;
    }

    public void setCheckers(List<Check> checkers) {
        this.checkers = checkers;
    }

}
