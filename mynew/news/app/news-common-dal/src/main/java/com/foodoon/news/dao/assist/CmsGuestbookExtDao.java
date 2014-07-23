package com.foodoon.news.dao.assist;

import com.foodoon.common.hibernate3.Updater;
import com.foodoon.news.entity.assist.CmsGuestbookExt;

public interface CmsGuestbookExtDao {
	public CmsGuestbookExt findById(Integer id);

	public CmsGuestbookExt save(CmsGuestbookExt bean);

	public CmsGuestbookExt updateByUpdater(Updater<CmsGuestbookExt> updater);

	public CmsGuestbookExt deleteById(Integer id);
}