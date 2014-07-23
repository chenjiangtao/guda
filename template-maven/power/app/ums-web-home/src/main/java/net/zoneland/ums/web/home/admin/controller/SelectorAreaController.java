/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.web.home.admin.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.zoneland.ums.biz.config.admin.UmsAreaBiz;
import net.zoneland.ums.common.dal.dataobject.ZTree;
import net.zoneland.ums.web.home.base.BaseController;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author wangyong
 * @version $Id: SelectorAreaController.java, v 0.1 2012-10-19 下午1:48:02
 *          wangyong Exp $
 */
@Controller
@RequestMapping("/admin")
public class SelectorAreaController extends BaseController {

    private final static Logger logger = Logger.getLogger(SelectorAreaController.class);

    @Autowired
    private UmsAreaBiz          umsAreaBiz;

    @RequestMapping("/getFirstArea.ajax")
    public void getFirstOrg(HttpServletRequest request, HttpServletResponse response) {
        List<ZTree> zTreeFirstList = umsAreaBiz.queryAreaForSelector("0");
        List<ZTree> zTreeList = new ArrayList<ZTree>();
        if (zTreeFirstList != null && zTreeFirstList.size() > 0) {
            for (ZTree ztree : zTreeFirstList) {
                zTreeList = umsAreaBiz.queryAreaForSelector(ztree.getId());
            }
        }
        if (zTreeList != null && zTreeList.size() > 0) {
            Collections.sort(zTreeList, new Mycomparator());
            ajaxReturn(zTreeList, response);
        }
    }

    @RequestMapping("/getChildArea.ajax")
    public void getChildrenOrg(HttpServletRequest request, HttpServletResponse response) {
        String parentId = request.getParameter("id");
        // 解析页面传来的parentId；
        List<ZTree> zTreeList = umsAreaBiz.queryAreaForSelector(parentId);
        Collections.sort(zTreeList, new Mycomparator());
        for (ZTree test : zTreeList) {
            logger.debug(test.getId());
        }
        if (zTreeList != null && zTreeList.size() > 0) {
            ajaxReturn(zTreeList, response);
        }
    }

    /**
     * 根据用户当前分配单位的关联表情况获取根节点单位以及其勾选情况
     * 
     * @param request
     * @param response
     */
    @RequestMapping("/getAssignFirstArea.ajax")
    public void getAssignFirstArea(HttpServletRequest request, HttpServletResponse response) {
        String userId = request.getParameter("userId");
        List<ZTree> zTreeFirstList = umsAreaBiz.getAssignChildArea("0", userId);
        List<ZTree> zTreeList = new ArrayList<ZTree>();
        if (zTreeFirstList != null && zTreeFirstList.size() > 0) {
            for (ZTree ztree : zTreeFirstList) {
                zTreeList = umsAreaBiz.getAssignChildArea(ztree.getId(), userId);
            }
        }
        if (zTreeList != null && zTreeList.size() > 0) {
            Collections.sort(zTreeList, new Mycomparator());
            ajaxReturn(zTreeList, response);
        }
    }

    /**
     * 根据用户当前分配单位的关联表情况获取加载子节点以及其勾选情况
     * 
     * @param request
     * @param response
     */
    @RequestMapping("/getAssignChildArea.ajax")
    public void getAssignChildArea(HttpServletRequest request, HttpServletResponse response) {
        String parentId = request.getParameter("id");
        String userId = request.getParameter("userId");
        // 解析页面传来的parentId；
        List<ZTree> zTreeList = umsAreaBiz.getAssignChildArea(parentId, userId);
        if (zTreeList != null && zTreeList.size() > 0) {
            Collections.sort(zTreeList, new Mycomparator());
            ajaxReturn(zTreeList, response);
        }
    }

    class Mycomparator implements Comparator<ZTree> {

        public int compare(ZTree o1, ZTree o2) {
            if (o1 == null || o2 == null) {
                return 0;
            }
            long areaCode1 = parseLong(o1.getId());
            long areaCode2 = parseLong(o2.getId());
            return (int) (areaCode1 - areaCode2);
        }
    }

    private Long parseLong(String pageId) {
        if (StringUtils.isBlank(pageId)) {
            return (long) 0;
        }
        try {
            return Long.parseLong(pageId);
        } catch (Exception e) {
            return (long) 0;
        }
    }
}
