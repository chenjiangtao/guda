package com.foodoon.news.manager.main.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foodoon.common.hibernate3.Updater;
import com.foodoon.news.dao.main.CmsSiteCompanyDao;
import com.foodoon.news.entity.main.CmsSite;
import com.foodoon.news.entity.main.CmsSiteCompany;
import com.foodoon.news.manager.main.CmsSiteCompanyMng;

@Service
@Transactional
public class CmsSiteCompanyMngImpl implements CmsSiteCompanyMng {
	public CmsSiteCompany save(CmsSite site,CmsSiteCompany bean) {
		site.setSiteCompany(bean);
		bean.setSite(site);
		dao.save(bean);
		return bean;
	}

	public CmsSiteCompany update(CmsSiteCompany bean) {
		Updater<CmsSiteCompany> updater = new Updater<CmsSiteCompany>(bean);
		bean = dao.updateByUpdater(updater);
		return bean;
	}

	private CmsSiteCompanyDao dao;

	@Autowired
	public void setDao(CmsSiteCompanyDao dao) {
		this.dao = dao;
	}
}