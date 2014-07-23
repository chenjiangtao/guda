/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.msg.draft;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import net.zoneland.ums.common.dal.UmsContactMapper;
import net.zoneland.ums.common.dal.UmsGroupMapper;
import net.zoneland.ums.common.dal.UmsGroupUserRelMapper;
import net.zoneland.ums.common.dal.UmsMsgDraftMapper;
import net.zoneland.ums.common.dal.UmsOrganizationMapper;
import net.zoneland.ums.common.dal.UmsUserInfoMapper;
import net.zoneland.ums.common.dal.bo.UmsMsgDraftBO;
import net.zoneland.ums.common.dal.dataobject.UmsContact;
import net.zoneland.ums.common.dal.dataobject.UmsGroup;
import net.zoneland.ums.common.dal.dataobject.UmsGroupUserRel;
import net.zoneland.ums.common.dal.dataobject.UmsMsgDraft;
import net.zoneland.ums.common.dal.dataobject.UmsOrganization;
import net.zoneland.ums.common.dal.dataobject.UmsUserInfo;
import net.zoneland.ums.common.dal.util.PageSearch;
import net.zoneland.ums.common.util.helper.UUIDHelper;
import net.zoneland.ums.common.util.page.PageResult;

import org.apache.commons.lang.StringUtils;

/**
 *
 * @author wangyong
 * @version $Id: MsgDrafServiceImpl.java, v 0.1 2012-8-21 上午11:32:00 wangyong Exp $
 */
public class MsgDraftServiceImpl implements MsgDraftService {

    private final static String   SPLITCHAR = ",";

    private UmsMsgDraftMapper     umsMsgDraftMapper;

    private UmsUserInfoMapper     umsUserInfoMapper;

    private UmsGroupMapper        umsGroupMapper;

    private UmsOrganizationMapper umsOrganizationMapper;

    private UmsGroupUserRelMapper umsGroupUserRelMapper;

    private UmsContactMapper      umsContactMapper;

    /**
     * @see net.zoneland.ums.biz.msg.draft.MsgDraftService#saveOrUpdateDraft(net.zoneland.ums.common.dal.dataobject.UmsMsgDraft)
     */
    public boolean saveOrUpdateDraft(UmsMsgDraft umsMsgDraft) {
        if (umsMsgDraft == null) {
            return false;
        }
        try {
            if (!StringUtils.isBlank(umsMsgDraft.getId())) {
                umsMsgDraft.setGmtModified(new Date());
                umsMsgDraftMapper.updateByPrimaryKeySelective(umsMsgDraft);
                return true;
            } else {
                umsMsgDraft.setId(String.valueOf(UUID.randomUUID()));
                umsMsgDraft.setGmtCreated(new Date());
                umsMsgDraft.setGmtModified(new Date());
                umsMsgDraftMapper.insert(umsMsgDraft);
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * @see net.zoneland.ums.biz.msg.draft.MsgDraftService#delDraft(java.lang.String)
     */
    public boolean delDraft(String msgDraftId) {
        if (msgDraftId == null) {
            return false;
        }
        umsMsgDraftMapper.deleteByPrimaryKey(msgDraftId);
        return true;
    }

    /**
     * @see net.zoneland.ums.biz.msg.draft.MsgDraftService#delDraft(java.util.List)
     */
    public boolean delDraft(List<String> msgDraftIdList) {
        return false;
    }

    /**
     * @see net.zoneland.ums.biz.msg.draft.MsgDraftService#findDraft(java.lang.String)
     */
    public UmsMsgDraft findDraft(String draftId) {

        return umsMsgDraftMapper.selectByPrimaryKey(draftId);
    }

    /**
     * @see net.zoneland.ums.biz.msg.draft.MsgDraftService#findDraft(java.lang.String, int, int)
     */
    public PageResult<UmsMsgDraft> findDraft(String userId, int pageId) {

        UmsMsgDraftBO bo = new UmsMsgDraftBO();
        bo.setUserId(userId);
        int count = umsMsgDraftMapper.selectCountByUserId(userId);
        PageResult<UmsMsgDraft> result = new PageResult<UmsMsgDraft>(count, pageId);
        bo.setOrderBy("GMT_CREATED");
        PageSearch ps = new PageSearch(bo, result.getFirstrecode(), result.getEndrecode());
        List<UmsMsgDraft> msgDraft = umsMsgDraftMapper.selectByUserId(ps);
        for (int i = 0, size = msgDraft.size(); i < size; i++) {
            String recvId = msgDraft.get(i).getRecvId();
            msgDraft.get(i).setRecvId(getRecvName(recvId));
        }
        result.setResults(msgDraft);
        return result;
    }

    public UmsMsgDraftMapper getUmsMsgDraftMapper() {
        return umsMsgDraftMapper;
    }

    public void setUmsMsgDraftMapper(UmsMsgDraftMapper umsMsgDraftMapper) {
        this.umsMsgDraftMapper = umsMsgDraftMapper;
    }

    /**
     * @see net.zoneland.ums.biz.msg.draft.MsgDraftService#getRecvName(java.lang.String)
     */
    public String getName(String recvId) {
        String userIdentifier = UUIDHelper.getIdentifier(recvId);
        if (userIdentifier == null) {
            return recvId;
        }
        String id = UUIDHelper.getUUID(recvId);
        if (userIdentifier.equalsIgnoreCase("person")) {
            int num = id.lastIndexOf("_");
            if (num == -1) {
                UmsUserInfo umsUserInfo = umsUserInfoMapper.selectByPrimaryKey(id);
                if (umsUserInfo == null) {
                    return "未知";
                }
                return umsUserInfo.getUserName();
            }
            id = UUIDHelper.getUUID(id);
            UmsGroupUserRel umsGroupUserRel = umsGroupUserRelMapper.selectByPrimaryKey(id);
            if (umsGroupUserRel == null) {
                return "未知";
            }
            return umsGroupUserRel.getComments();
        }

        if (userIdentifier.equalsIgnoreCase("group")) {
            UmsGroup umsGroup = umsGroupMapper.selectByPrimaryKey(id);
            if (umsGroup == null) {
                return "未知";
            }
            return umsGroup.getGroupName();
        }

        if (userIdentifier.equalsIgnoreCase("contact")) {
            UmsContact umsContact = umsContactMapper.selectByPrimaryKey(id);
            if (umsContact == null) {
                return "未知";
            }
            if (StringUtils.isEmpty(umsContact.getUserName())) {
                return umsContact.getPhone();
            }
            return umsContact.getUserName();
        }

        if (userIdentifier.equalsIgnoreCase("org")) {
            UmsOrganization umsOrganization = umsOrganizationMapper.selectByPrimaryKey(id);
            if (umsOrganization == null) {
                return "未知";
            }
            return umsOrganization.getOrgName();
        }

        return recvId;
    }

    public String getRecvName(String recvId) {
        String[] usersArray = recvId.split(SPLITCHAR);
        String recvName = null;
        for (int i = 0, len = usersArray.length; i < len; ++i) {
            if (recvName == null) {
                recvName = getName(usersArray[i]);
            } else {
                recvName = recvName + "," + getName(usersArray[i]);
            }
        }
        return recvName;
    }

    public UmsUserInfoMapper getUmsUserInfoMapper() {
        return umsUserInfoMapper;
    }

    public void setUmsUserInfoMapper(UmsUserInfoMapper umsUserInfoMapper) {
        this.umsUserInfoMapper = umsUserInfoMapper;
    }

    public UmsGroupMapper getUmsGroupMapper() {
        return umsGroupMapper;
    }

    public void setUmsGroupMapper(UmsGroupMapper umsGroupMapper) {
        this.umsGroupMapper = umsGroupMapper;
    }

    public UmsOrganizationMapper getUmsOrganizationMapper() {
        return umsOrganizationMapper;
    }

    public void setUmsOrganizationMapper(UmsOrganizationMapper umsOrganizationMapper) {
        this.umsOrganizationMapper = umsOrganizationMapper;
    }

    public UmsGroupUserRelMapper getUmsGroupUserRelMapper() {
        return umsGroupUserRelMapper;
    }

    public void setUmsGroupUserRelMapper(UmsGroupUserRelMapper umsGroupUserRelMapper) {
        this.umsGroupUserRelMapper = umsGroupUserRelMapper;
    }

    public UmsContactMapper getUmsContactMapper() {
        return umsContactMapper;
    }

    public void setUmsContactMapper(UmsContactMapper umsContactMapper) {
        this.umsContactMapper = umsContactMapper;
    }
}
