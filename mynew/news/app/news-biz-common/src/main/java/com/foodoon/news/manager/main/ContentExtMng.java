package com.foodoon.news.manager.main;

import com.foodoon.news.entity.main.Content;
import com.foodoon.news.entity.main.ContentExt;

public interface ContentExtMng {
	public ContentExt save(ContentExt ext, Content content);

	public ContentExt update(ContentExt ext);
}