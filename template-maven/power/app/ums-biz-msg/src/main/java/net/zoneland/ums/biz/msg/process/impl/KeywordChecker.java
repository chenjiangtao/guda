/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.msg.process.impl;

import net.zoneland.ums.biz.config.admin.KeywordBiz;
import net.zoneland.ums.biz.msg.PrimitiveMessage;
import net.zoneland.ums.biz.msg.process.Checker;
import net.zoneland.ums.common.util.helper.StringHelper;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author gag
 * @version $Id: KeywordChecker.java, v 0.1 2012-9-17 上午9:06:10 gag Exp $
 */
public class KeywordChecker implements Checker {

    private final static Logger logger = Logger.getLogger(KeywordChecker.class);

    @Autowired
    private KeywordBiz          keywordBiz;

    public boolean doCheck(PrimitiveMessage primitiveMessage) {
        String res = keywordBiz.includeKeyword(primitiveMessage.getContent(),
            primitiveMessage.getAppId());
        if (StringHelper.isNotEmpty(res)) {
            logger.warn("消息内容含有非法关键词:msg" + primitiveMessage);
            primitiveMessage.setComments("消息内容含有非法关键词:" + res);
            return false;
        }
        return true;
    }

    public void setKeywordBiz(KeywordBiz keywordBiz) {
        this.keywordBiz = keywordBiz;
    }

}
