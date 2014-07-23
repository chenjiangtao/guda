package com.foodoon.news.manager.assist;

import com.foodoon.news.entity.assist.CmsGuestbook;
import com.foodoon.news.entity.assist.CmsGuestbookExt;

public interface CmsGuestbookExtMng {
	public CmsGuestbookExt save(CmsGuestbookExt ext, CmsGuestbook guestbook);

	public CmsGuestbookExt update(CmsGuestbookExt ext);
}