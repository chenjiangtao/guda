#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package ${package}.web;

import javax.servlet.http.HttpServletRequest;


import ${package}.biz.BlankBiz;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestM${parentArtifactId}ing;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * ������
 * 
 * @author gag
 * @version ${symbol_dollar}Id: BlankController.java, v 0.1 2012-4-26 ����9:16:33 gag Exp ${symbol_dollar}
 */
@Controller
@RequestM${parentArtifactId}ing("/*.htm")
public class BlankController {

    private final static Logger logger = Logger.getLogger(BlankController.class);

    @Autowired
    private BlankBiz blankBiz;

    @RequestM${parentArtifactId}ing(method = RequestMethod.GET)
    public String doGet(HttpServletRequest request, ModelMap modelMap) {
        if (logger.isInfoEnabled()) {
            logger.info("test url��" + request.getRequestURL());
        }
        modelMap.addAttribute("attr", blankBiz.getTest());
        return "index.vm";

    }

    /**
     * Setter method for property <tt>blankBiz</tt>.
     * 
     * @param blankBiz value to be assigned to property blankBiz
     */
    public void setBlankBiz(BlankBiz blankBiz) {
        this.blankBiz = blankBiz;
    }

}
