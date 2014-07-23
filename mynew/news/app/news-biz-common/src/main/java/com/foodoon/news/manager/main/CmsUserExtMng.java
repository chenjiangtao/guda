package com.foodoon.news.manager.main;

import com.foodoon.news.entity.main.CmsUser;
import com.foodoon.news.entity.main.CmsUserExt;

public interface CmsUserExtMng {
	public CmsUserExt save(CmsUserExt ext, CmsUser user);

	public CmsUserExt update(CmsUserExt ext, CmsUser user);
}