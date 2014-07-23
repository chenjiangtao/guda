package com.foodoon.news.manager.assist;

import java.util.Date;

import com.foodoon.news.entity.assist.CmsVoteRecord;
import com.foodoon.news.entity.assist.CmsVoteTopic;
import com.foodoon.news.entity.main.CmsUser;

public interface CmsVoteRecordMng {
	public CmsVoteRecord save(CmsVoteTopic topic, CmsUser user, String ip,
			String cookie);

	public int deleteByTopic(Integer topicId);

	public Date lastVoteTimeByUserId(Integer userId, Integer topicId);

	public Date lastVoteTimeByIp(String ip, Integer topicId);

	public Date lastVoteTimeByCookie(String cookie, Integer topicId);
}