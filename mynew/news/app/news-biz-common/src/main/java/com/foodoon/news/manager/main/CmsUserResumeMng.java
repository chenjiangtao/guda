package com.foodoon.news.manager.main;

import com.foodoon.news.entity.main.CmsUser;
import com.foodoon.news.entity.main.CmsUserResume;

public interface CmsUserResumeMng {
	public CmsUserResume save(CmsUserResume ext, CmsUser user);

	public CmsUserResume update(CmsUserResume ext, CmsUser user);
}