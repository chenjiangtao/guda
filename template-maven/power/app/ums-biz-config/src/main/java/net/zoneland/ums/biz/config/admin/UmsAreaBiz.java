package net.zoneland.ums.biz.config.admin;

import java.util.List;

import net.zoneland.ums.biz.config.util.AjaxResult;
import net.zoneland.ums.common.dal.dataobject.UmsArea;
import net.zoneland.ums.common.dal.dataobject.ZTree;

public interface UmsAreaBiz {

    List<ZTree> queryAreaForSelector(String id);

    /**
     * 通过用户ID获取单位代码集合，拼成字符串，用逗号分割
     * 
     * @param userId
     * @return
     */
    String getAreaCodes(String userId);

    boolean savaUmsArea(UmsArea umsArea);

    void delUmsAreaByAreaCode(String areaCode);

    /**
     * 给用户分配单位
     * 
     * @param areaCodeStr
     * @param userId 
     * @param userName 
     * @return
     */
    AjaxResult giveArea(String areaCodeStr, String userId, String userName);

    boolean updateNameByCode(String areaCode, String areaName);

    UmsArea isExistOfAreaCode(String areaCode);

    boolean isExistOfAreaName(String areaName, String parentId);

    UmsArea findUmsAreaByAreaCode(String areaCode);

    /**
     * 根据用户当前分配单位的关联表情况获取加载子节点以及其勾选情况
     * 
     * @param parentId 根据当前传入的parentId作为父节点的单位编码去查找子节点
     * @param userId
     * @return
     */
    List<ZTree> getAssignChildArea(String parentId, String userId);
}
