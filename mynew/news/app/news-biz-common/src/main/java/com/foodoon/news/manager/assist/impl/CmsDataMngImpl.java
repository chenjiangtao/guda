package com.foodoon.news.manager.assist.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foodoon.news.dao.assist.CmsDataDao;
import com.foodoon.news.entity.back.CmsConstraints;
import com.foodoon.news.entity.back.CmsField;
import com.foodoon.news.entity.back.CmsTable;
import com.foodoon.news.manager.assist.CmsDataMng;

@Service
@Transactional
public class CmsDataMngImpl implements CmsDataMng {

	@Transactional(readOnly = true)
	public List<CmsTable> listTabels() {
		return dao.listTables();
	}

	@Transactional(readOnly = true)
	public CmsTable findTable(String tablename) {
		return dao.findTable(tablename);
	}

	@Transactional(readOnly = true)
	public List<CmsField> listFields(String tablename) {
		return dao.listFields(tablename);
	}

	@Transactional(readOnly = true)
	public List<CmsConstraints> listConstraints(String tablename) {
		return dao.listConstraints(tablename);
	}


	private CmsDataDao dao;

	@Autowired
	public void setDao(CmsDataDao dao) {
		this.dao = dao;
	}


}