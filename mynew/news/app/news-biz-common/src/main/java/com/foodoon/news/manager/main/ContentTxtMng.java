package com.foodoon.news.manager.main;

import com.foodoon.news.entity.main.Content;
import com.foodoon.news.entity.main.ContentTxt;

public interface ContentTxtMng {
	public ContentTxt save(ContentTxt txt, Content content);

	public ContentTxt update(ContentTxt txt, Content content);
}