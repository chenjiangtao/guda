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
 * 处理发送失败的消息的定时任务类
 * @author louguodong
 *
 */
public class SendFailureSmsJob implements Job2 {

    /**  */
    private static final long   serialVersionUID = 1097346639584733451L;
    private static final Logger logger           = LoggerFactory.getLogger("UMS-QUARTZ");

    private static final String APPID            = "fail";

    /* (non-Javadoc)
     * @see net.zoneland.ums.biz.msg.quartz.support.Job2#executeInternal(org.springframework.context.ApplicationContext)
     */
    public void executeInternal(ApplicationContext cxt) {
        LockAppService lockAppService = cxt.getBean(LockAppService.class);
        boolean lockResult = lockAppService.lockApp(APPID);
        if (!lockResult) {
            logger.warn("已有服务器执行捞取发送失败消息！");
            return;
        }
        logger.info("fail锁定成功！");
        logger.info("捞取失败任务启动~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~开始重新发送失败状态的消息。");
        SmsQuartzBiz quartzBiz = cxt.getBean(SmsQuartzBiz.class);
        try {
            quartzBiz.dealFailureSms();
        } catch (Exception e) {
            logger.error("定时发送失败状态的消息任务出现错误!", e);
        } finally {
            if (lockResult) {
                if (lockAppService.releaseApp(APPID)) {
                    logger.info("fail解锁！");
                }
            }
        }
    }

}
