package net.zoneland.ums.common.dal;

import java.util.List;
import java.util.Map;

import net.zoneland.ums.common.dal.bo.UmsMsgInRuleBo;
import net.zoneland.ums.common.dal.dataobject.UmsMsgInRule;
import net.zoneland.ums.common.util.annotation.Log;

public interface UmsMsgInRuleMapper {

    @Log(name = "上行规则维护", comments = "删除上行规则")
    int deleteByPrimaryKey(String id);

    @Log(name = "上行规则维护", comments = "新增上行规则")
    int insert(UmsMsgInRule record);

    @Log(name = "上行规则维护", comments = "新增上行规则")
    int insertSelective(UmsMsgInRule record);

    UmsMsgInRule selectByPrimaryKey(String id);

    @Log(name = "上行规则维护", comments = "更新上行规则")
    int updateByPrimaryKeySelective(UmsMsgInRule record);

    @Log(name = "上行规则维护", comments = "更新上行规则")
    int updateByPrimaryKey(UmsMsgInRule record);

    /**
     * 分页查询上行规则的总数量
     * 
     * @param params
     * @return
     */
    int searchMsgInRuleNum(Map<String, Object> params);

    /**
     * 分页查询上行规则
     * 
     * @param params
     * @return
     */
    List<UmsMsgInRuleBo> searchSelectiveByPage(Map<String, Object> params);

    /**
     * 通过条件获取上行规则
     * 
     * @param umsMsgInRule
     * @return
     */
    List<UmsMsgInRule> getMsgInRule(UmsMsgInRule umsMsgInRule);

    List<UmsMsgInRule> selectAll();

    /**
     * 通过appId删除上行规则
     * 
     * @param appId
     */
    int deleteByAppId(String appId);

    /**
     * 通过subAppId删除与该子应用相关的上行规则
     * 
     * @param subAppId
     */
    int deleteBySubAppId(String subAppId);
}