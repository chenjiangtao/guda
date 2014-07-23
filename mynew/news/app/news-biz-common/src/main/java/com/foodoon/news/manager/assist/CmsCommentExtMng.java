package com.foodoon.news.manager.assist;

import com.foodoon.news.entity.assist.CmsComment;
import com.foodoon.news.entity.assist.CmsCommentExt;

public interface CmsCommentExtMng {
	public CmsCommentExt save(String ip, String text, CmsComment comment);

	public CmsCommentExt update(CmsCommentExt bean);

	public int deleteByContentId(Integer contentId);
}