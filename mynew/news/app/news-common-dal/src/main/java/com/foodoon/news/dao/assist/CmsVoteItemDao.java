package com.foodoon.news.dao.assist;

import com.foodoon.common.hibernate3.Updater;
import com.foodoon.common.page.Pagination;
import com.foodoon.news.entity.assist.CmsVoteItem;

public interface CmsVoteItemDao {
	public Pagination getPage(int pageNo, int pageSize);

	public CmsVoteItem findById(Integer id);

	public CmsVoteItem save(CmsVoteItem bean);

	public CmsVoteItem updateByUpdater(Updater<CmsVoteItem> updater);

	public CmsVoteItem deleteById(Integer id);
}