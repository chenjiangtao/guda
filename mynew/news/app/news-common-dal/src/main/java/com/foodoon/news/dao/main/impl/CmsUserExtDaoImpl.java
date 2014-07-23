package com.foodoon.news.dao.main.impl;

import org.springframework.stereotype.Repository;

import com.foodoon.common.hibernate3.HibernateBaseDao;
import com.foodoon.news.dao.main.CmsUserExtDao;
import com.foodoon.news.entity.main.CmsUserExt;

@Repository
public class CmsUserExtDaoImpl extends HibernateBaseDao<CmsUserExt, Integer> implements CmsUserExtDao {
	public CmsUserExt findById(Integer id) {
		CmsUserExt entity = get(id);
		return entity;
	}

	public CmsUserExt save(CmsUserExt bean) {
		getSession().save(bean);
		return bean;
	}
	
	@Override
	protected Class<CmsUserExt> getEntityClass() {
		return CmsUserExt.class;
	}
}