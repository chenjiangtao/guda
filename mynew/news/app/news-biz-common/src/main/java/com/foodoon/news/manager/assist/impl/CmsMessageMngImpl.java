package com.foodoon.news.manager.assist.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foodoon.common.hibernate3.Updater;
import com.foodoon.common.page.Pagination;
import com.foodoon.news.dao.assist.CmsMessageDao;
import com.foodoon.news.entity.assist.CmsMessage;
import com.foodoon.news.manager.assist.CmsMessageMng;

/**
 *
 */
@Service
@Transactional
public class CmsMessageMngImpl implements CmsMessageMng {

	public Pagination getPage(Integer siteId, Integer sendUserId,
			Integer receiverUserId, String title, Date sendBeginTime,
			Date sendEndTime, Boolean status, Integer box, Boolean cacheable,
			int pageNo, int pageSize) {
		return dao.getPage(siteId, sendUserId, receiverUserId, title,
				sendBeginTime, sendEndTime, status, box, cacheable, pageNo,
				pageSize);
	}

	public CmsMessage findById(Integer id) {
		return dao.findById(id);
	}

	public CmsMessage save(CmsMessage bean) {
		return dao.save(bean);
	}

	public CmsMessage update(CmsMessage bean) {
		Updater<CmsMessage> updater = new Updater<CmsMessage>(bean);
		bean = dao.updateByUpdater(updater);
		return bean;
	}

	public CmsMessage deleteById(Integer id) {
		return dao.deleteById(id);
	}

	public CmsMessage[] deleteByIds(Integer[] ids) {
		return dao.deleteByIds(ids);
	}

	@Autowired
	private CmsMessageDao dao;

}
