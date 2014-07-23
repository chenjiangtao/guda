/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.foodoon.info.web.home.user;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * @author foodoon
 * @version $Id: MyPubController.java, v 0.1 2013年10月19日 上午11:28:56 foodoon Exp $
 */
@Controller
public class MyPubController {

    @RequestMapping(value = "/home/myPub.htm", method = RequestMethod.GET)
    public String doGet(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
                                                                                                    throws IOException {

        return "user/home/myPub.vm";
    }

}
