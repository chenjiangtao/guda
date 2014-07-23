package com.foodoon.news.dao.main;

import net.sf.ehcache.Ehcache;

import com.foodoon.common.hibernate3.Updater;
import com.foodoon.news.entity.main.ContentCount;

public interface ContentCountDao {
	public int clearCount(boolean week, boolean month);

	public int copyCount();

	public int freshCacheToDB(Ehcache cache);

	public ContentCount findById(Integer id);

	public ContentCount save(ContentCount bean);

	public ContentCount updateByUpdater(Updater<ContentCount> updater);
}