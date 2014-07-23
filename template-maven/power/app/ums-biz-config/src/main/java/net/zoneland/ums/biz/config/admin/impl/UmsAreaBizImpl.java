package net.zoneland.ums.biz.config.admin.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.zoneland.mvc.runtime.core.security.SecurityContextHolder;
import net.zoneland.ums.biz.config.admin.UmsAreaBiz;
import net.zoneland.ums.biz.config.util.AjaxResult;
import net.zoneland.ums.common.dal.UmsAreaMapper;
import net.zoneland.ums.common.dal.UmsUserAreaRelMapper;
import net.zoneland.ums.common.dal.dataobject.UmsArea;
import net.zoneland.ums.common.dal.dataobject.UmsUserAreaRel;
import net.zoneland.ums.common.dal.dataobject.ZTree;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class UmsAreaBizImpl implements UmsAreaBiz {

    @Autowired
    private UmsAreaMapper        umsAreaMapper;

    @Autowired
    private UmsUserAreaRelMapper umsUserAreaRelMapper;

    public List<ZTree> queryAreaForSelector(String id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }
        List<ZTree> zTrees = new ArrayList<ZTree>();
        List<UmsArea> umsAreaList = umsAreaMapper.selectByParentId(id);
        for (UmsArea umsArea : umsAreaList) {
            ZTree zTree = getZTree(umsArea);
            zTrees.add(zTree);
        }
        return zTrees;
    }

    /** 
     * 根据用户当前分配单位的关联表情况获取加载子节点以及其勾选情况
     * 
     * @see net.zoneland.ums.biz.config.admin.UmsAreaBiz#getAssignChildArea(java.lang.String)
     */
    public List<ZTree> getAssignChildArea(String parentId, String userId) {
        if (StringUtils.isEmpty(parentId)) {
            return null;
        }
        List<ZTree> zTrees = new ArrayList<ZTree>();
        List<UmsArea> umsAreaList = umsAreaMapper.selectByParentId(parentId);
        if (umsAreaList == null || umsAreaList.size() == 0) {
            return null;
        }
        List<String> areaIds = umsUserAreaRelMapper.getAreaIdByUserId(userId);// 获取当前用户所属的单位编码集合
        for (UmsArea umsArea : umsAreaList) {
            ZTree zTree = getAssignChildZTree(umsArea, areaIds);
            zTrees.add(zTree);
        }
        return zTrees;
    }

    /**
     * 比较当前用户单位编码与zTree中所加载的某个节点，如果两者单位编码相同则zTree默认勾选上
     * 
     * @param umsArea
     * @return
     */
    private ZTree getAssignChildZTree(UmsArea umsArea, List<String> areaIds) {
        ZTree zTree = new ZTree();
        zTree.setId(umsArea.getAreaCode());
        zTree.setName(umsArea.getAreaName());
        zTree.setpId(umsArea.getParentId());
        zTree.setOpen(true);
        zTree.setParent(true);
        for (String areaId : areaIds) {// 如果当前用户单位编码与zTree中所加载的某个节点的编码相同则勾选上
            if (areaId.equals(umsArea.getAreaCode())) {
                zTree.setChecked(true);
            }
        }
        return zTree;
    }

    private ZTree getZTree(UmsArea umsArea) {
        if (umsArea == null) {
            return null;
        }
        ZTree zTree = new ZTree();
        zTree.setId(umsArea.getAreaCode());
        zTree.setName(umsArea.getAreaName());
        zTree.setpId(umsArea.getParentId());
        zTree.setOpen(false);
        zTree.setParent(true);
        return zTree;
    }

    /** 
     * 通过用户ID获取单位代码集合，拼成字符串，用逗号分割
     * 
     * @see net.zoneland.ums.biz.config.admin.UmsAreaBiz#getAreaCodes(java.lang.String)
     */
    public String getAreaCodes(String userId) {
        List<String> areaIds = umsUserAreaRelMapper.getAreaIdByUserId(userId);
        if (areaIds == null || areaIds.size() <= 0) {// 如果用户不属于任何单位
            return "";
        }
        String areaCodes = "";
        for (String areaId : areaIds) {
            areaCodes += areaId + ",";
        }
        return areaCodes;
    }

    public boolean savaUmsArea(UmsArea umsArea) {
        if (umsArea == null) {
            return false;
        }
        umsArea.setId(UUID.randomUUID().toString());
        umsArea.setGmtCreated(new Date());
        int save = umsAreaMapper.insert(umsArea);
        if (save == 1) {
            return true;
        }
        return false;
    }

    /**
     * @see net.zoneland.ums.biz.config.admin.UmsAreaBiz#delUmsAreaByAreaCode(java.lang.String)
     */
    public void delUmsAreaByAreaCode(String areaCode) {
        if (StringUtils.isEmpty(areaCode)) {
            return;
        }
        List<UmsArea> umsAreas = umsAreaMapper.selectByParentId(areaCode);
        UmsArea umsArea = umsAreaMapper.selectByAreaCode(areaCode);
        umsAreaMapper.deleteByAreaCode(areaCode);
        if (umsArea != null) {
            umsUserAreaRelMapper.deleteByAreaId(umsArea.getId());
        }
        if (umsAreas != null) {
            for (UmsArea test : umsAreas) {
                delUmsAreaByAreaCode(test.getAreaCode());
            }
        }
    }

    /** 
     * 给用户分配单位
     * 
     * @see net.zoneland.ums.biz.config.admin.UmsAreaBiz#giveArea(java.lang.String, java.lang.String, java.lang.String)
     */
    public AjaxResult giveArea(String areaCodeStr, String userId, String userName) {
        AjaxResult result = new AjaxResult();
        userName = StringUtils.trim(userName);
        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(userName)) {
            result.setInfo("分配单位失败：没有获取到当前用户信息");
            result.setResult(false);
            return result;
        }
        if (StringUtils.isEmpty(areaCodeStr)) {
            result.setInfo("请在下方勾选具体要分配的单位,再点击保存按钮");
            result.setResult(false);
            return result;
        }
        String getAreaCodes = "";
        String[] areaCodes = areaCodeStr.split(",");
        umsUserAreaRelMapper.deleteByUserId(userId);// 删除当前用户的用户单位关联表信息
        for (String areaCode : areaCodes) {// 遍历所选中的单位编码
            UmsUserAreaRel umsUserAreaRel = new UmsUserAreaRel(); // 新建用户单位关联表
            umsUserAreaRel.setId(UUID.randomUUID().toString());
            umsUserAreaRel.setAreaId(areaCode);
            umsUserAreaRel.setUserId(userId);
            umsUserAreaRelMapper.insert(umsUserAreaRel);
            getAreaCodes += areaCode + ",";
        }
        SecurityContextHolder.getContext().getPrincipal().setProvinceId(getAreaCodes);
        result.setInfo("用户【" + userName + "】所属单位分配成功");
        result.setResult(true);
        return result;
    }

    /**
     * @see net.zoneland.ums.biz.config.admin.UmsAreaBiz#updateAreaNameByAreaCode(java.lang.String,
     *      java.lang.String)
     */
    public boolean updateNameByCode(String areaCode, String areaName) {
        if (StringUtils.isEmpty(areaCode)) {
            return false;
        }
        Map<String, String> map = new HashMap<String, String>();
        map.put("areaName", areaName);
        map.put("areaCode", areaCode);
        int update = umsAreaMapper.updateAreaNameByAreaCode(map);
        if (update > 0) {
            return true;
        }
        return false;
    }

    /**
     * @see net.zoneland.ums.biz.config.admin.UmsAreaBiz#isExistOfAreaCode(java.lang.String)
     */
    public UmsArea isExistOfAreaCode(String areaCode) {
        if (StringUtils.isEmpty(areaCode)) {
            return null;
        }
        UmsArea test = umsAreaMapper.findByAreaCode(areaCode);
        return test;
    }

    class Mycomparator implements Comparator<UmsArea> {

        public int compare(UmsArea o1, UmsArea o2) {
            if (o1 == null || o2 == null) {
                return 0;
            }
            int areaCode1 = parseInt(o1.getAreaCode());
            int areaCode2 = parseInt(o2.getAreaCode());
            return areaCode1 - areaCode2;
        }

    }

    private int parseInt(String pageId) {
        if (StringUtils.isBlank(pageId)) {
            return 0;
        }
        try {
            return Integer.parseInt(pageId);
        } catch (Exception e) {
            return 0;
        }
    }

    /** 
     * @see net.zoneland.ums.biz.config.admin.UmsAreaBiz#isExistOfAreaName(java.util.Map)
     */
    public boolean isExistOfAreaName(String areaName, String parentId) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("areaName", areaName);
        map.put("parentId", parentId);
        UmsArea test = umsAreaMapper.findByAreaNameAndParentId(map);
        if (test == null) {
            return false;
        }
        return true;
    }

    /** 
     * @see net.zoneland.ums.biz.config.admin.UmsAreaBiz#findUmsAreaByAreaCode(java.lang.String)
     */
    public UmsArea findUmsAreaByAreaCode(String areaCode) {
        if (StringUtils.isEmpty(areaCode)) {
            return null;
        }
        UmsArea umsArea = umsAreaMapper.findByAreaCode(areaCode);
        return umsArea;
    }
}
