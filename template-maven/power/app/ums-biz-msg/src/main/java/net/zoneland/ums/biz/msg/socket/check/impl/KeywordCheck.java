/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.msg.socket.check.impl;

import net.zoneland.ums.biz.config.admin.KeywordBiz;
import net.zoneland.ums.biz.msg.socket.ServiceRequest;
import net.zoneland.ums.biz.msg.socket.check.Check;
import net.zoneland.ums.biz.msg.socket.process.ProcessResult;
import net.zoneland.ums.common.util.helper.StringHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

/**
 *
 * @author gag
 * @version $Id: KeywordCheck.java, v 0.1 2012-8-23 下午12:00:51 gag Exp $
 */
public class KeywordCheck implements Check {

    @Autowired
    private KeywordBiz keywordBiz;

    /**
     * @see net.zoneland.ums.biz.msg.socket.check.Check#check(net.zoneland.ums.biz.msg.socket.ServiceRequest)
     */
    public ProcessResult check(ServiceRequest sr) {
        if (sr == null) {
            return new ProcessResult(false, "请求对象为空");
        }
        if (StringUtils.hasText(sr.getMsg())) {
            String res = keywordBiz.includeKeyword(sr.getMsg(), sr.getAppId());
            if (StringHelper.isNotEmpty(res)) {
                return new ProcessResult(false, "消息内容含有非法关键词:" + res);
            }
        }
        return new ProcessResult(true);
    }

    public void setKeywordBiz(KeywordBiz keywordBiz) {
        this.keywordBiz = keywordBiz;
    }

}
