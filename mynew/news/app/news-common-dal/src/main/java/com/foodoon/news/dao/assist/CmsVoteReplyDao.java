package com.foodoon.news.dao.assist;

import com.foodoon.common.hibernate3.Updater;
import com.foodoon.common.page.Pagination;
import com.foodoon.news.entity.assist.CmsVoteReply;

public interface CmsVoteReplyDao {

	public Pagination getPage(Integer  subTopicId, int pageNo, int pageSize);
	
	public CmsVoteReply findById(Integer id);

	public CmsVoteReply save(CmsVoteReply bean);

	public CmsVoteReply updateByUpdater(Updater<CmsVoteReply> updater);

	public CmsVoteReply deleteById(Integer id);
}