package com.foodoon.news.manager.assist;

import java.util.List;

import com.foodoon.news.entity.back.CmsConstraints;
import com.foodoon.news.entity.back.CmsField;
import com.foodoon.news.entity.back.CmsTable;

public interface CmsDataMng {
	public List<CmsTable> listTabels();

	public CmsTable findTable(String tablename);

	public List<CmsField> listFields(String tablename);

	public List<CmsConstraints> listConstraints(String tablename);
}