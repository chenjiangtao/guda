package com.foodoon.news.dao.assist;

import java.util.List;

import com.foodoon.common.hibernate3.Updater;
import com.foodoon.common.page.Pagination;
import com.foodoon.news.entity.assist.CmsVoteTopic;

public interface CmsVoteTopicDao {
	public Pagination getPage(Integer siteId, int pageNo, int pageSize);
	
	public List<CmsVoteTopic> getList(Boolean def,Integer siteId, int count);

	public CmsVoteTopic getDefTopic(Integer siteId);

	public CmsVoteTopic findById(Integer id);

	public CmsVoteTopic save(CmsVoteTopic bean);

	public CmsVoteTopic updateByUpdater(Updater<CmsVoteTopic> updater);

	public CmsVoteTopic deleteById(Integer id);
}