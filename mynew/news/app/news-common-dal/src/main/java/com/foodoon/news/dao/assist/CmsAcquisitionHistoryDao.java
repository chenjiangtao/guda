package com.foodoon.news.dao.assist;

import java.util.List;

import com.foodoon.common.hibernate3.Updater;
import com.foodoon.common.page.Pagination;
import com.foodoon.news.entity.assist.CmsAcquisitionHistory;

public interface CmsAcquisitionHistoryDao {
	public List<CmsAcquisitionHistory> getList(Integer siteId,Integer acquId);

	public Pagination getPage(Integer siteId,Integer acquId, Integer pageNo, Integer pageSize);

	public CmsAcquisitionHistory findById(Integer id);

	public CmsAcquisitionHistory save(CmsAcquisitionHistory bean);

	public CmsAcquisitionHistory updateByUpdater(Updater<CmsAcquisitionHistory> updater);

	public CmsAcquisitionHistory deleteById(Integer id);

	public Boolean checkExistByProperties(Boolean title, String value);
}