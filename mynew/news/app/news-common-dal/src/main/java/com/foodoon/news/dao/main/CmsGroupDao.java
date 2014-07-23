package com.foodoon.news.dao.main;

import java.util.List;

import com.foodoon.common.hibernate3.Updater;
import com.foodoon.news.entity.main.CmsGroup;

public interface CmsGroupDao {
	public List<CmsGroup> getList();

	public CmsGroup getRegDef();

	public CmsGroup findById(Integer id);

	public CmsGroup save(CmsGroup bean);

	public CmsGroup updateByUpdater(Updater<CmsGroup> updater);

	public CmsGroup deleteById(Integer id);
}