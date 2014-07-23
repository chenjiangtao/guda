package com.foodoon.news.manager.main;

import com.foodoon.news.entity.main.CmsSite;
import com.foodoon.news.entity.main.CmsUser;
import com.foodoon.news.entity.main.CmsUserSite;

public interface CmsUserSiteMng {
	public CmsUserSite findById(Integer id);

	public CmsUserSite save(CmsSite site, CmsUser user, Byte step,
			Boolean allChannel);

	public CmsUserSite update(CmsUserSite bean);

	public void updateByUser(CmsUser user, Integer siteId, Byte step,
			Boolean allChannel);

	public void updateByUser(CmsUser user, Integer[] siteIds, Byte[] steps,
			Boolean[] allChannels);

	public int deleteBySiteId(Integer siteId);

	public CmsUserSite deleteById(Integer id);

	public CmsUserSite[] deleteByIds(Integer[] ids);
}