package com.foodoon.news.dao.main.impl;

import org.springframework.stereotype.Repository;

import com.foodoon.common.hibernate3.HibernateBaseDao;
import com.foodoon.news.dao.main.ChannelTxtDao;
import com.foodoon.news.entity.main.ChannelTxt;

@Repository
public class ChannelTxtDaoImpl extends HibernateBaseDao<ChannelTxt, Integer>
		implements ChannelTxtDao {
	public ChannelTxt findById(Integer id) {
		ChannelTxt entity = get(id);
		return entity;
	}

	public ChannelTxt save(ChannelTxt bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
	protected Class<ChannelTxt> getEntityClass() {
		return ChannelTxt.class;
	}
}