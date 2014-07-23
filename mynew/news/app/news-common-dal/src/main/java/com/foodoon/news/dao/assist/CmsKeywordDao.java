package com.foodoon.news.dao.assist;

import java.util.List;

import com.foodoon.common.hibernate3.Updater;
import com.foodoon.news.entity.assist.CmsKeyword;

public interface CmsKeywordDao {
	public List<CmsKeyword> getList(Integer siteId, boolean onlyEnabled,
			boolean cacheable);

	public List<CmsKeyword> getListGlobal(boolean onlyEnabled, boolean cacheable);

	public CmsKeyword findById(Integer id);

	public CmsKeyword save(CmsKeyword bean);

	public CmsKeyword updateByUpdater(Updater<CmsKeyword> updater);

	public CmsKeyword deleteById(Integer id);
}