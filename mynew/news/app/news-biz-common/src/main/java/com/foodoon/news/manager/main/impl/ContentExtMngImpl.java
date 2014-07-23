package com.foodoon.news.manager.main.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foodoon.common.hibernate3.Updater;
import com.foodoon.news.dao.main.ContentExtDao;
import com.foodoon.news.entity.main.Content;
import com.foodoon.news.entity.main.ContentExt;
import com.foodoon.news.manager.main.ContentExtMng;

@Service
@Transactional
public class ContentExtMngImpl implements ContentExtMng {
	public ContentExt save(ContentExt ext, Content content) {
		content.setContentExt(ext);
		ext.setContent(content);
		if (ext.getReleaseDate() == null) {
			ext.setReleaseDate(content.getSortDate());
		}
		ext.init();
		dao.save(ext);
		content.setContentExt(ext);
		return ext;
	}

	public ContentExt update(ContentExt bean) {
		Updater<ContentExt> updater = new Updater<ContentExt>(bean);
		bean = dao.updateByUpdater(updater);
		bean.blankToNull();
		// 修改后需要重新生成静态页
		bean.setNeedRegenerate(true);
		return bean;
	}

	private ContentExtDao dao;

	@Autowired
	public void setDao(ContentExtDao dao) {
		this.dao = dao;
	}
}