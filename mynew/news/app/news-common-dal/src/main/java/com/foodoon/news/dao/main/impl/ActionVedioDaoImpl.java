package com.foodoon.news.dao.main.impl;

import java.util.List;

import com.foodoon.common.hibernate3.Finder;
import com.foodoon.common.hibernate3.HibernateBaseDao;
import com.foodoon.news.dao.main.ActionVedioDao;
import com.foodoon.news.entity.main.ActionVedio;


public class ActionVedioDaoImpl extends HibernateBaseDao<ActionVedio, String> implements ActionVedioDao{

	public ActionVedio save(ActionVedio bean) {
		getSession().save(bean);
		getSession().flush();
		return bean;
	}

	public List<ActionVedio> getList() {
		Finder f = Finder.create("select bean from ActionVedio");
		f.append(" order buy gmtCreate desc limit 10");
		return find(f);
	}

	@Override
	protected Class<ActionVedio> getEntityClass() {
		return ActionVedio.class;
	}

}
