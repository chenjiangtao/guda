package com.foodoon.news.manager.assist;

import java.util.Date;

import com.foodoon.common.page.Pagination;
import com.foodoon.news.entity.assist.CmsMessage;

/**
 *
 */
public interface CmsMessageMng {
	public Pagination getPage(Integer siteId, Integer sendUserId,
			Integer receiverUserId, String title, Date sendBeginTime,
			Date sendEndTime, Boolean status, Integer box, Boolean cacheable,
			int pageNo, int pageSize);

	public CmsMessage findById(Integer id);

	public CmsMessage save(CmsMessage bean);

	public CmsMessage update(CmsMessage bean);

	public CmsMessage deleteById(Integer id);

	public CmsMessage[] deleteByIds(Integer[] ids);
}