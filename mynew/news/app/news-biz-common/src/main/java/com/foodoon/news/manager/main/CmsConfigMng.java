package com.foodoon.news.manager.main;

import java.util.Date;

import com.foodoon.news.entity.main.CmsConfig;
import com.foodoon.news.entity.main.MarkConfig;
import com.foodoon.news.entity.main.MemberConfig;

public interface CmsConfigMng {
	public CmsConfig get();

	public void updateCountCopyTime(Date d);

	public void updateCountClearTime(Date d);

	public CmsConfig update(CmsConfig bean);

	public MarkConfig updateMarkConfig(MarkConfig mark);

	public void updateMemberConfig(MemberConfig memberConfig);
}