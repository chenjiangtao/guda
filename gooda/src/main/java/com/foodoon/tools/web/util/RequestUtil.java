package com.foodoon.tools.web.util;

import javax.servlet.http.HttpServletRequest;

public class RequestUtil {
	
	public static int getInt(HttpServletRequest request,String paramName){
		if(request == null){
			return 0;
		}
		String val = request.getParameter(paramName);
		if(val == null){
			return 0;
		}
		return Convert.toInt(val);
	}
	
	public static String resolveVM(HttpServletRequest request){
		if(request == null){
			return null;
		}
		String uri = request.getRequestURI();
		if(uri == null || "/".equals(uri)){
			return null;
		}
		if(uri.endsWith("htm") && uri.length()>3){
			return uri.substring(0, uri.length()-3) + "vm";
		}
		return null;
		
	}

}
