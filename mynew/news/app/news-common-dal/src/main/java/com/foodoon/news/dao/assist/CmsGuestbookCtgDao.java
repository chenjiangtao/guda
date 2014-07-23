package com.foodoon.news.dao.assist;

import java.util.List;

import com.foodoon.common.hibernate3.Updater;
import com.foodoon.news.entity.assist.CmsGuestbookCtg;

public interface CmsGuestbookCtgDao {
	public List<CmsGuestbookCtg> getList(Integer siteId);

	public CmsGuestbookCtg findById(Integer id);

	public CmsGuestbookCtg save(CmsGuestbookCtg bean);

	public CmsGuestbookCtg updateByUpdater(Updater<CmsGuestbookCtg> updater);

	public CmsGuestbookCtg deleteById(Integer id);
}