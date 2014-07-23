package com.foodoon.news.dao.assist;

import com.foodoon.common.hibernate3.Updater;
import com.foodoon.common.page.Pagination;
import com.foodoon.news.entity.assist.CmsCommentExt;

public interface CmsCommentExtDao {
	public Pagination getPage(int pageNo, int pageSize);

	public CmsCommentExt findById(Integer id);

	public CmsCommentExt save(CmsCommentExt bean);

	public CmsCommentExt updateByUpdater(Updater<CmsCommentExt> updater);

	public int deleteByContentId(Integer contentId);

	public CmsCommentExt deleteById(Integer id);
}