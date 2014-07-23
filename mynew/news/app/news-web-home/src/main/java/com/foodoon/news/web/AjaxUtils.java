package com.foodoon.news.web;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

public class AjaxUtils {
	
	private static final Logger log = LoggerFactory.getLogger(AjaxUtils.class);

	public static void returnAjax(HttpServletResponse response, Object result) {
		response.setContentType("text/Xml;charset=UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.println(JSON.toJSONString(result));
		} catch (Exception e) {
			log.error("",e);
		} finally {
			out.close();
		}

	}

}
