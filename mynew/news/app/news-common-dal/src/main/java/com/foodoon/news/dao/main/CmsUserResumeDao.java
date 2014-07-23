package com.foodoon.news.dao.main;

import com.foodoon.common.hibernate3.Updater;
import com.foodoon.news.entity.main.CmsUserResume;

public interface CmsUserResumeDao {
	public CmsUserResume findById(Integer id);

	public CmsUserResume save(CmsUserResume bean);

	public CmsUserResume updateByUpdater(Updater<CmsUserResume> updater);
}