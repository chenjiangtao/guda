package com.foodoon.news.manager.main.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foodoon.common.hibernate3.Updater;
import com.foodoon.news.dao.main.CmsModelDao;
import com.foodoon.news.entity.main.CmsModel;
import com.foodoon.news.manager.main.CmsModelMng;

@Service
@Transactional
public class CmsModelMngImpl implements CmsModelMng {
	@Transactional(readOnly = true)
	public List<CmsModel> getList(boolean containDisabled,Boolean hasContent) {
		return dao.getList(containDisabled,hasContent);
	}

	public CmsModel getDefModel() {
		return dao.getDefModel();
	}

	@Transactional(readOnly = true)
	public CmsModel findById(Integer id) {
		CmsModel entity = dao.findById(id);
		return entity;
	}
	
	@Transactional(readOnly = true)
	public CmsModel findByPath(String path){
		CmsModel entity = dao.findByPath(path);
		return entity;
	}

	public CmsModel save(CmsModel bean) {
		bean.init();
		dao.save(bean);
		return bean;
	}

	public CmsModel update(CmsModel bean) {
		Updater<CmsModel> updater = new Updater<CmsModel>(bean);
		CmsModel entity = dao.updateByUpdater(updater);
		return entity;
	}

	public CmsModel deleteById(Integer id) {
		CmsModel bean = dao.deleteById(id);
		return bean;
	}

	public CmsModel[] deleteByIds(Integer[] ids) {
		CmsModel[] beans = new CmsModel[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	public CmsModel[] updatePriority(Integer[] ids, Integer[] priority,
			Boolean[] disabled, Integer defId) {
		int len = ids.length;
		CmsModel[] beans = new CmsModel[len];
		for (int i = 0; i < len; i++) {
			beans[i] = findById(ids[i]);
			beans[i].setPriority(priority[i]);
			beans[i].setDisabled(disabled[i]);
			if (beans[i].getId().equals(defId)) {
				beans[i].setDef(true);
			} else {
				beans[i].setDef(false);
			}
		}
		return beans;
	}

	private CmsModelDao dao;

	@Autowired
	public void setDao(CmsModelDao dao) {
		this.dao = dao;
	}
}