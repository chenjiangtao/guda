package com.foodoon.news.dao.main.impl;

import org.springframework.stereotype.Repository;

import com.foodoon.common.hibernate3.HibernateBaseDao;
import com.foodoon.news.dao.main.ContentTxtDao;
import com.foodoon.news.entity.main.ContentTxt;

@Repository
public class ContentTxtDaoImpl extends HibernateBaseDao<ContentTxt, Integer>
		implements ContentTxtDao {
	public ContentTxt findById(Integer id) {
		ContentTxt entity = get(id);
		return entity;
	}

	public ContentTxt save(ContentTxt bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
	protected Class<ContentTxt> getEntityClass() {
		return ContentTxt.class;
	}
}