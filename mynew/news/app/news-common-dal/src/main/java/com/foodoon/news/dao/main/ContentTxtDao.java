package com.foodoon.news.dao.main;

import com.foodoon.common.hibernate3.Updater;
import com.foodoon.news.entity.main.ContentTxt;

public interface ContentTxtDao {
	public ContentTxt findById(Integer id);

	public ContentTxt save(ContentTxt bean);

	public ContentTxt updateByUpdater(Updater<ContentTxt> updater);
}