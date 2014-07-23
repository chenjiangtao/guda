package com.foodoon.news.dao.main;

import java.util.List;

import com.foodoon.news.entity.main.ActionVedio;

public interface ActionVedioDao {
	
	public ActionVedio save(ActionVedio bean);
	
	public List<ActionVedio> getList();

}
