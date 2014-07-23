/**
 *
 */
package net.zoneland.ums.biz.msg.quartz;

import net.zoneland.ums.biz.msg.in.LockAppService;
import net.zoneland.ums.biz.msg.quartz.support.Job2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

/**
 * 处理定时发送的消息的定时任务类
 * @author louguodong
 *
 */
public class SendDelaySmsJob implements Job2 {

    private static final long   serialVersionUID = 6201142402101641810L;
    private static final Logger logger           = LoggerFactory.getLogger("UMS-QUARTZ");

    private static final String APPID            = "delay";

    /* (non-Javadoc)
     * @see net.zoneland.ums.biz.msg.quartz.support.Job2#executeInternal(org.springframework.context.ApplicationContext)
     */
    public void executeInternal(ApplicationContext cxt) {
        LockAppService lockAppService = cxt.getBean(LockAppService.class);
        boolean lockResult = lockAppService.lockApp(APPID);
        if (!lockResult) {
            logger.warn("已有服务器执行捞取定时消息！");
            return;
        }
        logger.info("delay锁定成功！");
        logger.info("捞取定时任务启动~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~开始发送定时发送的消息。");
        SmsQuartzBiz quartzBiz = cxt.getBean(SmsQuartzBiz.class);
        try {
            quartzBiz.dealDelaySms();
        } catch (Exception e) {
            logger.error("发送定时消息的定时任务出错！", e);
        } finally {
            if (lockResult) {
                if (lockAppService.releaseApp(APPID)) {
                    logger.info("delay解锁！");
                }
            }
        }
    }

}
