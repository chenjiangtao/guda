/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.config.admin;

import net.zoneland.ums.common.dal.bo.UmsMsgInRuleBo;
import net.zoneland.ums.common.dal.dataobject.UmsMsgInRule;
import net.zoneland.ums.common.util.page.PageResult;

/**
 * 上行规则维护业务层接口
 * 
 * @author yangjuanying
 * @version $Id: MsgInRuleBiz.java, v 0.1 2012-10-13 下午10:42:50 yangjuanying Exp $
 */
public interface MsgInRuleBiz {

    /**
     * 保存新增上行规则
     * 
     * @param umsMsgInRule
     * @return
     */
    boolean saveMsgInRule(UmsMsgInRule umsMsgInRule);

    /**
     * 分页显示上行规则表
     * 
     * @param umsMsgInRuleBo
     * @param pageId
     * @return
     */
    PageResult<UmsMsgInRuleBo> searchMsgInRuleByPage(UmsMsgInRuleBo umsMsgInRuleBo, int pageId);

    /**
     * 根据主键ID删除该上行规则记录
     *
     * @param id
     */
    public boolean deleteMsgInRule(String id);

    /**
     * 通过ID查找上行规则记录
     * 
     * @param id
     * @return
     */
    UmsMsgInRule getMsgInRuleById(String id);

    /**
     * 更新上行规则
     * 
     * @param umsMsgInRule
     */
    public boolean update(UmsMsgInRule umsMsgInRule);

    public UmsMsgInRule findRule(String word);
}
