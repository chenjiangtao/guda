package com.foodoon.news.dao.assist;

import java.util.List;

import com.foodoon.news.entity.back.CmsConstraints;
import com.foodoon.news.entity.back.CmsField;
import com.foodoon.news.entity.back.CmsTable;

public interface CmsDataDao {
	public List<CmsTable> listTables();

	public List<CmsField> listFields(String tablename);

	public List<CmsConstraints> listConstraints(String tablename);

	public CmsTable findTable(String tablename);

}