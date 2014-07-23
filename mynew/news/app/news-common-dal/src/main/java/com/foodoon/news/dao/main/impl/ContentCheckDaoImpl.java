package com.foodoon.news.dao.main.impl;

import org.springframework.stereotype.Repository;

import com.foodoon.common.hibernate3.HibernateBaseDao;
import com.foodoon.news.dao.main.ContentCheckDao;
import com.foodoon.news.entity.main.ContentCheck;

@Repository
public class ContentCheckDaoImpl extends HibernateBaseDao<ContentCheck, Long>
		implements ContentCheckDao {
	public ContentCheck findById(Long id) {
		ContentCheck entity = get(id);
		return entity;
	}

	public ContentCheck save(ContentCheck bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
	protected Class<ContentCheck> getEntityClass() {
		return ContentCheck.class;
	}
}