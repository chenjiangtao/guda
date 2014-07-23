package com.foodoon.news.manager.main.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foodoon.common.hibernate3.Updater;
import com.foodoon.news.dao.main.ContentTxtDao;
import com.foodoon.news.entity.main.Content;
import com.foodoon.news.entity.main.ContentTxt;
import com.foodoon.news.manager.main.ContentTxtMng;

@Service
@Transactional
public class ContentTxtMngImpl implements ContentTxtMng {
	public ContentTxt save(ContentTxt txt, Content content) {
		if (txt.isAllBlank()) {
			return null;
		} else {
			txt.setContent(content);
			txt.init();
			dao.save(txt);
			content.setContentTxt(txt);
			return txt;
		}
	}

	public ContentTxt update(ContentTxt txt, Content content) {
		ContentTxt entity = dao.findById(content.getId());
		if (entity == null) {
			entity = save(txt, content);
			content.getContentTxtSet().add(entity);
			return entity;
		} else {
			if (txt.isAllBlank()) {
				content.getContentTxtSet().clear();
				return null;
			} else {
				Updater<ContentTxt> updater = new Updater<ContentTxt>(txt);
				entity = dao.updateByUpdater(updater);
				entity.blankToNull();
				return entity;
			}
		}
	}

	private ContentTxtDao dao;

	@Autowired
	public void setDao(ContentTxtDao dao) {
		this.dao = dao;
	}
}