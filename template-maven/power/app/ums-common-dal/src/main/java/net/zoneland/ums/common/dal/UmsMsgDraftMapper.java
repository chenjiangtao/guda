package net.zoneland.ums.common.dal;

import java.util.List;

import net.zoneland.ums.common.dal.dataobject.UmsMsgDraft;
import net.zoneland.ums.common.dal.util.PageSearch;
import net.zoneland.ums.common.util.annotation.Log;

public interface UmsMsgDraftMapper {

    @Log(name = "草稿箱维护", comments = "删除草稿")
    int deleteByPrimaryKey(String id);

    @Log(name = "草稿箱维护", comments = "新增草稿")
    int insert(UmsMsgDraft record);

    UmsMsgDraft selectByPrimaryKey(String id);

    @Log(name = "草稿箱维护", comments = "修改草稿")
    int updateByPrimaryKeySelective(UmsMsgDraft record);

    @Log(name = "草稿箱维护", comments = "修改草稿")
    int updateByPrimaryKey(UmsMsgDraft record);

    /**
     *根据用户分页查询
     *
     * @param map
     * @return
     */
    List<UmsMsgDraft> selectByUserId(PageSearch ps);

    /**
     *根据用户获得草稿个数
     *
     * @param userId
     * @return
     */
    int selectCountByUserId(String userId);

}