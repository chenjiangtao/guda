package com.foodoon.core.dao;

import com.foodoon.common.hibernate3.Updater;
import com.foodoon.core.entity.DbFile;

public interface DbFileDao {
	public DbFile findById(String id);

	public DbFile save(DbFile bean);

	public DbFile updateByUpdater(Updater<DbFile> updater);

	public DbFile deleteById(String id);
}