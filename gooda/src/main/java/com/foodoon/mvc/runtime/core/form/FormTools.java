package com.foodoon.mvc.runtime.core.form;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

public class FormTools {
	
	public static String newForm(HttpServletRequest req){
		String token = String.valueOf(UUID.randomUUID());
		req.getSession().setAttribute(DefHandlerInterceptorAdapter.FORM_TOKEN_KEY, token);
		return token;
	}

}
