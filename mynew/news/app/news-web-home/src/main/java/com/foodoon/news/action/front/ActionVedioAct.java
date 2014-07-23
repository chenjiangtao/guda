package com.foodoon.news.action.front;


import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.foodoon.news.dao.main.ActionVedioDao;
import com.foodoon.news.entity.main.ActionVedio;
import com.foodoon.news.web.AjaxUtils;

@Controller
public class ActionVedioAct {
	
	@Autowired
	private ActionVedioDao actionVedioDao;
	
	@RequestMapping(value = "/actionVedio.htm")
	public String ad(String act, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
	
		ActionVedio vedio = new ActionVedio();
		vedio.setAction(act);
		vedio.setComment("test");
		vedio.setFromIp(request.getRemoteAddr());
		vedio.setGmtCreate(new Date());
		vedio.setId(UUID.randomUUID().toString());
		vedio.setUserName("游客");
		vedio = actionVedioDao.save(vedio);
		AjaxUtils.returnAjax(response, vedio);
		return null;
	}

}
