package com.foodoon.news.dao.assist;

import com.foodoon.news.entity.assist.CmsSiteFlow;

public interface CmsSiteFlowDao {
	public CmsSiteFlow save(CmsSiteFlow cmsSiteFlow);

	public CmsSiteFlow findUniqueByProperties(Integer siteId, String accessDate,
			String sessionId, String page);
}
