package com.foodoon.news.dao.main;

import com.foodoon.common.hibernate3.Updater;
import com.foodoon.news.entity.main.ChannelExt;

public interface ChannelExtDao {
	public ChannelExt save(ChannelExt bean);

	public ChannelExt updateByUpdater(Updater<ChannelExt> updater);
}