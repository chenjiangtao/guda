package com.foodoon.news.dao.main;

import com.foodoon.common.hibernate3.Updater;
import com.foodoon.news.entity.main.CmsSiteCompany;

public interface CmsSiteCompanyDao {

	public CmsSiteCompany save(CmsSiteCompany bean);

	public CmsSiteCompany updateByUpdater(Updater<CmsSiteCompany> updater);
}