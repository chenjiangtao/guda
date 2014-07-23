package com.foodoon.news.manager.main;

import com.foodoon.news.entity.main.CmsSite;
import com.foodoon.news.entity.main.CmsSiteCompany;

public interface CmsSiteCompanyMng {
	public CmsSiteCompany save(CmsSite site,CmsSiteCompany bean);

	public CmsSiteCompany update(CmsSiteCompany bean);
}