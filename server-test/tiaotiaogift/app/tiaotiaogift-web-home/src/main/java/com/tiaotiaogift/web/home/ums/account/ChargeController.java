/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.tiaotiaogift.web.home.ums.account;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * @author gang
 * @version $Id: ChargeController.java, v 0.1 2013-4-30 下午4:23:57 gang Exp $
 */
@Controller
public class ChargeController {

    @RequestMapping(value = "/account/charge.htm", method = RequestMethod.GET)
    public String doGet(HttpServletRequest request, ModelMap modelMap, HttpServletResponse response)
                                                                                                    throws IOException {

        return "ums/account/charge.vm";
    }

}
