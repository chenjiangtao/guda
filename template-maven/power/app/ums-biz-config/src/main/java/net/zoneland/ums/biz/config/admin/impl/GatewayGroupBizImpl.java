/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package net.zoneland.ums.biz.config.admin.impl;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.zoneland.ums.biz.config.admin.GatewayGroupBiz;
import net.zoneland.ums.common.dal.UmsGateWayInfoMapper;
import net.zoneland.ums.common.dal.UmsGatewayGroupMapper;
import net.zoneland.ums.common.dal.UmsGatewayGroupRelMapper;
import net.zoneland.ums.common.dal.dataobject.UmsGateWayInfo;
import net.zoneland.ums.common.dal.dataobject.UmsGatewayGroup;
import net.zoneland.ums.common.dal.dataobject.UmsGatewayGroupRel;
import net.zoneland.ums.common.dal.dataobject.ZTree;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

/**
 * 
 * @author yangjuanying
 * @version $Id: GatewayGroupBizImpl.java, v 0.1 2013-1-6 上午11:05:49 yangjuanying Exp $
 */
public class GatewayGroupBizImpl implements GatewayGroupBiz {

 //   private final Logger             logger = Logger.getLogger(GatewayGroupBizImpl.class);

    @Autowired
    private UmsGatewayGroupRelMapper umsGatewayGroupRelMapper;
    @Autowired
    private UmsGatewayGroupMapper    umsGatewayGroupMapper;
    @Autowired
    private UmsGateWayInfoMapper     umsGateWayInfoMapper;

    /** 
     * 查询网关分组信息
     * 
     * @see net.zoneland.ums.biz.config.admin.GatewayGroupBiz#queryGatewayGroupForSelector()
     */
    public List<ZTree> queryGatewayGroupForSelector() {
        List<ZTree> zTrees = new ArrayList<ZTree>();
        List<UmsGatewayGroup> umsGatewayGroups = umsGatewayGroupMapper.selectAllGatewayGroup();
        if (umsGatewayGroups != null && umsGatewayGroups.size() > 0) {
            Collections.sort(umsGatewayGroups, new MyGatewayGroupcomparator());
            for (UmsGatewayGroup umsGatewayGroup : umsGatewayGroups) {
                ZTree zTree = getZTree(umsGatewayGroup);
                zTrees.add(zTree);
            }
        }
        return zTrees;
    }

    /**
     * 获得网关分组Ztree生成树
     * 
     * @param umsGatewayGroup
     * @return
     */
    private ZTree getZTree(UmsGatewayGroup umsGatewayGroup) {
        if (umsGatewayGroup == null) {
            return null;
        }
        ZTree zTree = new ZTree();
        zTree.setId(umsGatewayGroup.getId());
        zTree.setName(umsGatewayGroup.getGatewayGroupName());
        zTree.setpId("0");
        zTree.setOpen(false);
        zTree.setParent(true);
        return zTree;
    }

    /** 
     * 根据传入的网关分组主键ID，在关联表中查出其对应分组的网关信息
     * 
     * @see net.zoneland.ums.biz.config.admin.GatewayGroupBiz#queryGatewayInfosByGatewayGroupId(java.lang.String)
     */
    public List<ZTree> queryGatewayInfosByGatewayGroupId(String gatewayGroupId) {
        List<ZTree> zTrees = new ArrayList<ZTree>();
        if (StringUtils.hasText(gatewayGroupId)) {
            List<UmsGatewayGroupRel> umsGatewayGroupRels = umsGatewayGroupRelMapper
                .selectByGatewayGroupId(gatewayGroupId);
            for (UmsGatewayGroupRel umsGatewayGroupRel : umsGatewayGroupRels) {
                ZTree zTree = getGatewayGroupZTree(umsGatewayGroupRel);
                zTrees.add(zTree);
            }
        }
        return zTrees;
    }

    /**
     * 获得网关关联表Ztree生成树
     * 
     * @param umsGatewayGroupRel
     * @return
     */
    private ZTree getGatewayGroupZTree(UmsGatewayGroupRel umsGatewayGroupRel) {
        if (umsGatewayGroupRel == null) {
            return null;
        }
        ZTree zTree = new ZTree();
        UmsGateWayInfo umsGatewayInfo = umsGateWayInfoMapper.selectByPrimaryKey(umsGatewayGroupRel
            .getGatewayId());
        if (umsGatewayInfo != null) {
            zTree.setId(umsGatewayInfo.getId());
            zTree.setName(umsGatewayInfo.getGatewayName());
        }
        zTree.setpId(umsGatewayGroupRel.getGatewayGroupId());
        zTree.setOpen(false);
        zTree.setParent(false);
        return zTree;
    }

    /** 
     * 点击“添加网关分组”按钮时往数据库中插入一个新的网关分组
     * 
     * @see net.zoneland.ums.biz.config.admin.GatewayGroupBiz#createNewGatewayGroup()
     */
    public UmsGatewayGroup createNewGatewayGroup() {
        UmsGatewayGroup umsGatewayGroup = new UmsGatewayGroup();
        umsGatewayGroup.setId(UUID.randomUUID().toString());
        umsGatewayGroup.setGatewayGroupName("新增网关分组");
        umsGatewayGroup.setGmtCreated(new Date());
        int res = umsGatewayGroupMapper.insert(umsGatewayGroup);
        if (res != 1) {// 没插入成功
            umsGatewayGroup = null;
        }
        return umsGatewayGroup;
    }

    /** 
     * 更新网关分组名
     * 
     * @see net.zoneland.ums.biz.config.admin.GatewayGroupBiz#updateByGatewayGroupName(java.lang.String, java.lang.String)
     */
    public boolean updateByGatewayGroupName(String gatewayGroupId, String gatewayGroupName) {
        if (!StringUtils.hasText(gatewayGroupId)) {
            return false;
        }
        UmsGatewayGroup umsGatewayGroup = new UmsGatewayGroup();
        umsGatewayGroup.setId(gatewayGroupId);
        umsGatewayGroup.setGatewayGroupName(gatewayGroupName);
        int update = umsGatewayGroupMapper.updateByPrimaryKeySelective(umsGatewayGroup);
        if (update > 0) {
            return true;
        }
        return false;
    }

    /**
     * 对网关分组名进行排序
     */
    class MyGatewayGroupcomparator implements Comparator<UmsGatewayGroup> {

        Collator collator = Collator.getInstance(java.util.Locale.CHINA);

        public int compare(UmsGatewayGroup o1, UmsGatewayGroup o2) {
            if (o1 == null || o2 == null) {
                return 0;
            }
            if (o1.getGatewayGroupName() == null || o2.getGatewayGroupName() == null) {
                return 0;
            }
            return collator.compare(o1.getGatewayGroupName(), o2.getGatewayGroupName());
        }
    }

    /** 
     * 通过网关分组主键ID删除整个网关分组(包括删除其关联表的数据)
     * 
     * @see net.zoneland.ums.biz.config.admin.GatewayGroupBiz#delGatewayGroupById(java.lang.String)
     */
    public void delGatewayGroupById(String gatewayGroupId) {
        if (!StringUtils.hasText(gatewayGroupId)) {
            return;
        }
        umsGatewayGroupRelMapper.deleteByGatewayGroupId(gatewayGroupId);
        umsGatewayGroupMapper.deleteByPrimaryKey(gatewayGroupId);
    }

    /** 
     * 在网关分组中添加网关，更新相关关联表信息
     * 
     * @see net.zoneland.ums.biz.config.admin.GatewayGroupBiz#saveGatewayGroup(java.lang.String, java.lang.String)
     */
    public boolean saveGatewayGroup(String gatewayGroupId, String gatewayIdStr) {
        if (!StringUtils.hasText(gatewayGroupId)) {
            return false;
        }
        umsGatewayGroupRelMapper.deleteByGatewayGroupId(gatewayGroupId);
        if (StringUtils.hasText(gatewayIdStr)) {
            String[] gatewayIdArrays = gatewayIdStr.split(",");
            for (String gatewayId : gatewayIdArrays) {
                UmsGatewayGroupRel umsGatewayGroupRel = new UmsGatewayGroupRel();
                umsGatewayGroupRel.setId(UUID.randomUUID().toString());
                umsGatewayGroupRel.setGatewayGroupId(gatewayGroupId);
                umsGatewayGroupRel.setGatewayId(gatewayId);
                umsGatewayGroupRel.setGmtCreated(new Date());
                int res = umsGatewayGroupRelMapper.insertSelective(umsGatewayGroupRel);
                if (res != 1) {
                    return false;
                }
            }
        }
        return true;
    }

    /** 
     * 通过网关分组主键ID和网关的主键ID删除所对应的网关分组关联表的数据
     * 
     * @see net.zoneland.ums.biz.config.admin.GatewayGroupBiz#delGatewayGroupRelByIds(java.lang.String, java.lang.String)
     */
    public boolean delGatewayGroupRelByIds(String gatewayId, String gatewayGroupId) {
        if (!StringUtils.hasText(gatewayId) || !StringUtils.hasText(gatewayGroupId)) {
            return false;
        }
        Map<String, String> gatewayMap = new HashMap<String, String>();
        gatewayMap.put("gatewayGroupId", gatewayGroupId);
        gatewayMap.put("gatewayId", gatewayId);
        int res = umsGatewayGroupRelMapper.deleteByGatewayIds(gatewayMap);
        if (res != 1) {
            return false;
        }
        return true;
    }

    /** 
     * 根据传入的网关分组主键ID，在关联表中查出其对应的关联表信息
     * 
     * @see net.zoneland.ums.biz.config.admin.GatewayGroupBiz#queryByGatewayGroupId(java.lang.String)
     */
    public List<UmsGatewayGroupRel> queryByGatewayGroupId(String gatewayGroupId) {
        List<UmsGatewayGroupRel> umsGatewayGroupRels = umsGatewayGroupRelMapper
            .selectByGatewayGroupId(gatewayGroupId);
        return umsGatewayGroupRels;
    }

    /** 
     * 根据传入的网关分组主键ID，在关联表中查出其对应分组的网关信息
     * 
     * @see net.zoneland.ums.biz.config.admin.GatewayGroupBiz#queryGatewayInfoById(java.lang.String)
     */
    public List<UmsGateWayInfo> queryGatewayInfoById(String id) {
        List<UmsGatewayGroupRel> umsGatewayGroupRels = umsGatewayGroupRelMapper
            .selectByGatewayGroupId(id);
        if (umsGatewayGroupRels != null && umsGatewayGroupRels.size() > 0) {
            List<UmsGateWayInfo> gateWayInfos = new ArrayList<UmsGateWayInfo>();
            for (UmsGatewayGroupRel umsGatewayGroupRel : umsGatewayGroupRels) {
                UmsGateWayInfo gateWayInfo = new UmsGateWayInfo();
                gateWayInfo = umsGateWayInfoMapper.selectByPrimaryKey(umsGatewayGroupRel
                    .getGatewayId());
                if (gateWayInfo != null) {
                    gateWayInfos.add(gateWayInfo);
                }
            }
            return gateWayInfos;
        }
        return null;
    }

    /** 
     * 查询所有网关分组信息
     * 
     * @see net.zoneland.ums.biz.config.admin.GatewayGroupBiz#findAll()
     */
    public List<UmsGatewayGroup> findAll() {
        List<UmsGatewayGroup> list = umsGatewayGroupMapper.selectAllGatewayGroup();
        if (list == null) {
            return Collections.emptyList();
        }
        return list;
    }

    /** 
     * 根据主键ID找出对应的网关分组
     * 
     * @see net.zoneland.ums.biz.config.admin.GatewayGroupBiz#findGatewayGroup(java.lang.String)
     */
    public UmsGatewayGroup findGatewayGroup(String gatewayId) {
        if (gatewayId == null) {
            return null;
        }
        return umsGatewayGroupMapper.selectByPrimaryKey(gatewayId);
    }
}
