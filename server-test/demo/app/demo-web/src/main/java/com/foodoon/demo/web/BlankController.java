/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.foodoon.demo.web;

import javax.servlet.http.HttpServletRequest;




import com.foodoon.demo.biz.BlankBiz;
import com.foodoon.tools.web.util.RequestUtil;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * ������
 * 
 * @author gag
 * @version $Id: BlankController.java, v 0.1 2012-4-26 ����9:16:33 gag Exp $
 */
@Controller
@RequestMapping("*/*.htm")
public class BlankController {

    private final static Logger logger = Logger.getLogger(BlankController.class);

   
    @RequestMapping(method = RequestMethod.GET)
    public String doGet(HttpServletRequest request, ModelMap modelMap) {
        
        return resolveVM(request);


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
			return uri.substring(1, uri.length()-3) + "vm";
		}
		return null;
		
	}

   

}
