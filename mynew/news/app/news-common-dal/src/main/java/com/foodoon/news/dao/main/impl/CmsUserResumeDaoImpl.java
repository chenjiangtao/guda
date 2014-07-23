package com.foodoon.news.dao.main.impl;

import org.springframework.stereotype.Repository;

import com.foodoon.common.hibernate3.HibernateBaseDao;
import com.foodoon.news.dao.main.CmsUserResumeDao;
import com.foodoon.news.entity.main.CmsUserResume;

@Repository
public class CmsUserResumeDaoImpl extends HibernateBaseDao<CmsUserResume, Integer> implements CmsUserResumeDao {
	public CmsUserResume findById(Integer id) {
		CmsUserResume entity = get(id);
		return entity;
	}

	public CmsUserResume save(CmsUserResume bean) {
		getSession().save(bean);
		return bean;
	}
	
	@Override
	protected Class<CmsUserResume> getEntityClass() {
		return CmsUserResume.class;
	}
}