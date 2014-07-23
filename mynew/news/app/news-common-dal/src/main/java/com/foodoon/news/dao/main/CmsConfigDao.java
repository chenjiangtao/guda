package com.foodoon.news.dao.main;

import com.foodoon.common.hibernate3.Updater;
import com.foodoon.news.entity.main.CmsConfig;

public interface CmsConfigDao {
	public CmsConfig findById(Integer id);

	public CmsConfig updateByUpdater(Updater<CmsConfig> updater);
}