package com.foodoon.news.manager.main;

import com.foodoon.news.entity.main.Channel;
import com.foodoon.news.entity.main.ChannelExt;

public interface ChannelExtMng {
	public ChannelExt save(ChannelExt ext, Channel channel);

	public ChannelExt update(ChannelExt ext);
}