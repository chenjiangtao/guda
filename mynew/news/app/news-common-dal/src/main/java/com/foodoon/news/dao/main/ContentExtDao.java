package com.foodoon.news.dao.main;

import com.foodoon.common.hibernate3.Updater;
import com.foodoon.news.entity.main.ContentExt;

public interface ContentExtDao {
	public ContentExt findById(Integer id);

	public ContentExt save(ContentExt bean);

	public ContentExt updateByUpdater(Updater<ContentExt> updater);
}