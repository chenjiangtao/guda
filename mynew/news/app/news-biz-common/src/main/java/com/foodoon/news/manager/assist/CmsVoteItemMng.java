package com.foodoon.news.manager.assist;

import java.util.Collection;

import com.foodoon.common.page.Pagination;
import com.foodoon.news.entity.assist.CmsVoteItem;
import com.foodoon.news.entity.assist.CmsVoteSubTopic;

public interface CmsVoteItemMng {
	public Pagination getPage(int pageNo, int pageSize);

	public CmsVoteItem findById(Integer id);

	public Collection<CmsVoteItem> save(Collection<CmsVoteItem> items,
			CmsVoteSubTopic topic);

	public Collection<CmsVoteItem> update(Collection<CmsVoteItem> items,
			CmsVoteSubTopic topic);

	public CmsVoteItem deleteById(Integer id);

	public CmsVoteItem[] deleteByIds(Integer[] ids);

}