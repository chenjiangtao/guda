package com.foodoon.news.dao.main;

import java.util.List;

import com.foodoon.common.hibernate3.Updater;
import com.foodoon.news.entity.main.CmsRole;

public interface CmsRoleDao {
	public List<CmsRole> getList();

	public CmsRole findById(Integer id);

	public CmsRole save(CmsRole bean);

	public CmsRole updateByUpdater(Updater<CmsRole> updater);

	public CmsRole deleteById(Integer id);
}