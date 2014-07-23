package com.foodoon.news.dao.assist;

import java.util.List;

import com.foodoon.common.hibernate3.Updater;
import com.foodoon.news.entity.assist.CmsSensitivity;

public interface CmsSensitivityDao {
	public List<CmsSensitivity> getList(boolean cacheable);

	public CmsSensitivity findById(Integer id);

	public CmsSensitivity save(CmsSensitivity bean);

	public CmsSensitivity updateByUpdater(Updater<CmsSensitivity> updater);

	public CmsSensitivity deleteById(Integer id);
}