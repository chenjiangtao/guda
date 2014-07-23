/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.msg.draft;

import java.util.List;

import net.zoneland.ums.common.dal.dataobject.UmsMsgDraft;
import net.zoneland.ums.common.util.page.PageResult;

/**
 *
 * @author wangyong
 * @version $Id: MsgDraftService.java, v 0.1 2012-8-20 下午10:51:10 wangyong Exp $
 */
public interface MsgDraftService {

    /**
      *保存和更新数草稿箱
      *
      * @param umsMsgDraft
      * @return
      */
    boolean saveOrUpdateDraft(UmsMsgDraft umsMsgDraft);

    /**
     *删除草稿
     *
     * @param msgDraftId
     * @return
     */
    boolean delDraft(String msgDraftId);

    /**
     *
     *
     * @param msgDraftIdList
     * @return
     */
    boolean delDraft(List<String> msgDraftIdList);

    /**
     *通过Id获得草稿
     *
     * @param draftId
     * @return
     */
    UmsMsgDraft findDraft(String draftId);

    /**
     *通过用户Id，页面及每页显示个数获得草稿
     *
     * @param userId
     * @param pageId
     * @return
     */
    PageResult<UmsMsgDraft> findDraft(String userId, int pageId);

    /**
     *通过recvId获得名字
     *
     * @param recvId
     * @return
     */
    String getRecvName(String recvId);
}
