/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.web.home.admin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import net.zoneland.ums.biz.config.admin.AllAppMsgBiz;
import net.zoneland.ums.biz.config.admin.AppSubBiz;
import net.zoneland.ums.biz.config.admin.MsgInRuleBiz;
import net.zoneland.ums.biz.msg.ObjectBuilder;
import net.zoneland.ums.common.dal.bo.UmsMsgInRuleBo;
import net.zoneland.ums.common.dal.dataobject.UmsAppInfo;
import net.zoneland.ums.common.dal.dataobject.UmsAppSub;
import net.zoneland.ums.common.dal.dataobject.UmsMsgInRule;
import net.zoneland.ums.common.util.helper.StringHelper;
import net.zoneland.ums.common.util.page.PageResult;
import net.zoneland.ums.web.home.admin.form.MsgInRuleForm;
import net.zoneland.ums.web.home.base.BaseController;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 上行规则维护控制层 
 * 
 * @author yangjuanying
 * @version $Id: MsgInRuleController.java, v 0.1 2012-10-13 下午7:27:14 yangjuanying Exp $
 */
@Controller
@RequestMapping("/admin/msgInRule")
public class MsgInRuleController extends BaseController {

    private static final Logger logger     = Logger.getLogger(MsgInRuleController.class);

    /**
     * 内容关键字长度最大为60.
     */
    private static final int    WORDLENGTH = 60;

    @Autowired
    private MsgInRuleBiz        msgInRuleBiz;

    @Autowired
    private AllAppMsgBiz        allAppMsgBiz;

    @Autowired
    private AppSubBiz           appSubBiz;

    /**
     * 拦截上行规则维护的Get请求
     * 
     * @param msgInRuleForm
     * @param pageId
     * @param result
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/list.htm", method = RequestMethod.GET)
    public String doGetMsgInRuleList(MsgInRuleForm msgInRuleForm, Integer pageId,
                                     BindingResult result, HttpServletRequest request,
                                     ModelMap modelMap) {
        if (pageId == null || pageId < 1) {// 如果获取当前页为空
            pageId = 1;// 设置当前页为第一页
        }
        PageResult<UmsMsgInRuleBo> pageResult = null;
        UmsMsgInRuleBo umsMsgInRuleBo = msgInRuleForm.cloneUmsMsgInRuleBo();
        try {
            pageResult = msgInRuleBiz.searchMsgInRuleByPage(umsMsgInRuleBo, pageId);
        } catch (Exception e) {
            logger.error("查询上行规则出错：", e);
            addErrorMsg(modelMap, "查询上行规则出错！");
        }
        modelMap.addAttribute("results", pageResult);// 分页结果集
        modelMap.addAttribute("pageId", pageId);// 绑定当前页
        modelMap.addAttribute("appName", msgInRuleForm.getAppName());// 绑定上行规则的应用名
        modelMap.addAttribute("word", msgInRuleForm.getWord());// 绑定上行规则的内容关键字
        return "/admin/msgInRule/msgInRuleList.vm";
    }

    /**
     * 拦截查询上行规则的Post请求
     * 
     * @param msgInRuleForm
     * @param pageId
     * @param result
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/list.htm", method = RequestMethod.POST)
    public String doPostMsgInRuleList(MsgInRuleForm msgInRuleForm, Integer pageId,
                                      BindingResult result, HttpServletRequest request,
                                      ModelMap modelMap) {
        if (pageId == null || pageId < 1) {// 如果获取当前页为空
            pageId = 1;// 设置当前页为第一页
        }
        PageResult<UmsMsgInRuleBo> pageResult = null;
        UmsMsgInRuleBo umsMsgInRuleBo = msgInRuleForm.cloneUmsMsgInRuleBo();
        try {
            umsMsgInRuleBo.setAppName(msgInRuleForm.getAppName());// 用应用名来模糊查询
            pageResult = msgInRuleBiz.searchMsgInRuleByPage(umsMsgInRuleBo, pageId);
        } catch (Exception e) {
            logger.error("查询上行规则出错：", e);
            addErrorMsg(modelMap, "查询上行规则出错！");
        }
        modelMap.addAttribute("results", pageResult);// 分页结果集
        modelMap.addAttribute("pageId", pageId);// 绑定当前页
        modelMap.addAttribute("appName", msgInRuleForm.getAppName());// 绑定上行规则的应用名
        modelMap.addAttribute("word", msgInRuleForm.getWord());// 绑定上行规则的内容关键字
        return "/admin/msgInRule/msgInRuleList.vm";
    }

    /**
     * 拦截更新上行规则的Get请求
     * 
     * @param msgInRuleForm
     * @param pageId
     * @param result
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/update.htm", method = RequestMethod.GET)
    public String doGetUpateMsgInRule(MsgInRuleForm msgInRuleForm, Integer pageId,
                                      BindingResult result, HttpServletRequest request,
                                      ModelMap modelMap) {
        String id = request.getParameter("ruleId");
        msgInRuleForm.setId(id);
        try {
            List<UmsAppInfo> appList = allAppMsgBiz.getAllApp();
            UmsMsgInRule msgInRule = msgInRuleBiz.getMsgInRuleById(id);
            if (msgInRule == null) {
                addErrorMsg(modelMap, "系统异常");
                modelMap.addAttribute("apps", appList);// 根据集合获取应用下拉框
                return "/admin/msgInRule/msgInRuleUpdate.vm";
            }
            List<UmsAppSub> appSubList = appSubBiz.getAppSub(msgInRule.getAppId());
            // 获取全部应用的主键ID，应用appId和应用名name
            modelMap.addAttribute("apps", appList);// 根据集合获取应用下拉框
            modelMap.addAttribute("pageId", pageId);
            modelMap.addAttribute("subApps", appSubList);// 根据集合获取子应用下拉框
            ObjectBuilder.copyObject(msgInRule, msgInRuleForm);
        } catch (Exception e) {
            logger.error("更新上行规则出错：", e);
            addErrorMsg(modelMap, "系统出现异常！");
        }
        return "/admin/msgInRule/msgInRuleUpdate.vm";
    }

    /**
     * 拦截更新上行规则的Post请求
     * 
     * @param msgInRuleForm
     * @param pageId
     * @param result
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/update.htm", method = RequestMethod.POST)
    public String doPostUpateMsgInRule(@Valid MsgInRuleForm msgInRuleForm, BindingResult result,
                                       HttpServletRequest request, ModelMap modelMap) {
        String subApp = request.getParameter("subApp");
        msgInRuleForm.setSubAppId(subApp);
        List<UmsAppInfo> appList = allAppMsgBiz.getAllApp();// 获取全部应用的主键ID，应用appId和应用名name
        List<UmsAppSub> appSubList = appSubBiz.getAppSub(msgInRuleForm.getAppId());
        //验证输入数据的正确性
        if (result.hasErrors()) {
            modelMap.addAttribute("apps", appList);// 根据集合获取应用下拉框
            modelMap.addAttribute("subApps", appSubList);// 根据集合获取子应用下拉框
            return "/admin/msgInRule/msgInRuleUpdate.vm";
        }
        if (StringHelper.getStringLength(msgInRuleForm.getWord()) > WORDLENGTH) {// 判断输入内容关键字长度是否大于20个汉字，即60个字符
            modelMap.addAttribute("subApps", appSubList);// 根据集合获取子应用下拉框
            modelMap.addAttribute("apps", appList);// 根据集合获取应用下拉框
            addErrorMsg(modelMap, "内容关键字长度不能大于60个字符(20个汉字)");
            return "/admin/msgInRule/msgInRuleAdd.vm";
        }
        //更新上行规则
        try {
            modelMap.addAttribute("apps", appList);// 根据集合获取应用下拉框
            modelMap.addAttribute("subApps", appSubList);// 根据集合获取子应用下拉框
            UmsMsgInRule msgInRule = msgInRuleForm.cloneUmsMsgInRule();
            UmsMsgInRule umsMsgInRule = msgInRuleBiz.getMsgInRuleById(msgInRuleForm.getId());
            if (umsMsgInRule == null || msgInRule == null) {
                addErrorMsg(modelMap, "系统异常");
                modelMap.addAttribute("subApps", appSubList);// 根据集合获取子应用下拉框
                modelMap.addAttribute("apps", appList);// 根据集合获取应用下拉框
                return "/admin/msgInRule/msgInRuleUpdate.vm";
            }
            msgInRule.setGmtCreated(umsMsgInRule.getGmtCreated());
            boolean res = msgInRuleBiz.update(msgInRule);
            if (res) {
                modelMap.addAttribute("umsMsgInRule", umsMsgInRule);
                return "/admin/msgInRule/msgInRuleUpdateSuccess.vm";
            }
            addMsg(modelMap, "内容关键字已存在，修改失败");
            return "/admin/msgInRule/msgInRuleUpdate.vm";
        } catch (Exception e) {
            logger.error("更新上行规则出现系统异常！", e);
            addErrorMsg(modelMap, "系统异常");
            return "/admin/msgInRule/msgInRuleUpdate.vm";
        }
    }

    /**
     * 拦截添加上行规则的Get请求
     * 
     * @param msgInRuleForm
     * @param result
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/add.htm", method = RequestMethod.GET)
    public String doGetAddMsgInRule(MsgInRuleForm msgInRuleForm, BindingResult result,
                                    HttpServletRequest request, ModelMap modelMap) {
        List<UmsAppInfo> appList = new ArrayList<UmsAppInfo>();
        List<UmsAppSub> appSubList = new ArrayList<UmsAppSub>();
        try {
            if (StringUtils.isNotEmpty(msgInRuleForm.getAppId())) {
                appSubList = appSubBiz.getAppSub(msgInRuleForm.getAppId());
            }
            appList = allAppMsgBiz.getAllApp();// 获取全部应用的主键ID，应用appId和应用名name
        } catch (Exception e) {
            logger.error("添加上行规则出错：", e);
            addErrorMsg(modelMap, "系统出现异常！");
        }
        modelMap.addAttribute("subApps", appSubList);// 根据集合获取子应用下拉框
        modelMap.addAttribute("apps", appList);// 根据集合获取应用下拉框
        return "/admin/msgInRule/msgInRuleAdd.vm";
    }

    /**
     * 拦截添加上行规则的Post请求
     * 
     * @param msgInRuleForm
     * @param result
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/add.htm", method = RequestMethod.POST)
    public String doPostAddMsgInRule(@Valid MsgInRuleForm msgInRuleForm, BindingResult result,
                                     HttpServletRequest request, ModelMap modelMap) {
        String subApp = request.getParameter("subApp");
        msgInRuleForm.setSubAppId(subApp);
        List<UmsAppInfo> appList = allAppMsgBiz.getAllApp();// 获取全部应用的主键ID，应用appId和应用名name
        List<UmsAppSub> appSubList = appSubBiz.getAppSub(msgInRuleForm.getAppId());
        //验证输入数据的正确性
        if (result.hasErrors()) {
            modelMap.addAttribute("apps", appList);// 根据集合获取应用下拉框
            modelMap.addAttribute("subApps", appSubList);// 根据集合获取子应用下拉框
            return "/admin/msgInRule/msgInRuleAdd.vm";
        }
        if (StringHelper.getStringLength(msgInRuleForm.getWord()) > WORDLENGTH) {// 判断输入内容关键字长度是否大于20个汉字，即60个字符
            modelMap.addAttribute("subApps", appSubList);// 根据集合获取子应用下拉框
            modelMap.addAttribute("apps", appList);// 根据集合获取应用下拉框
            addErrorMsg(modelMap, "内容关键字长度不能大于60个字符(20个汉字)");
            return "/admin/msgInRule/msgInRuleAdd.vm";
        }
        UmsMsgInRule umsMsgInRule = msgInRuleForm.cloneUmsMsgInRule();
        try {
            if (umsMsgInRule == null) {
                addErrorMsg(modelMap, "系统异常");
                modelMap.addAttribute("subApps", appSubList);// 根据集合获取子应用下拉框
                modelMap.addAttribute("apps", appList);// 根据集合获取应用下拉框
                return "/admin/msgInRule/msgInRuleAdd.vm";
            }
            boolean res = msgInRuleBiz.saveMsgInRule(umsMsgInRule);// 保存新增上行规则
            modelMap.addAttribute("subApps", appSubList);// 根据集合获取子应用下拉框
            modelMap.addAttribute("apps", appList);// 根据集合获取应用下拉框
            modelMap.addAttribute("word", msgInRuleForm.getWord());// 绑定上行规则的内容关键字
            if (res) {
                modelMap.addAttribute("umsMsgInRule", umsMsgInRule);
                return "/admin/msgInRule/msgInRuleAddSuccess.vm";
            }
            addMsg(modelMap, "内容关键字已存在，添加失败");
        } catch (Exception e) {
            logger.error("添加上行规则出错：", e);
            addErrorMsg(modelMap, "系统出现异常！");
        }
        return "/admin/msgInRule/msgInRuleAdd.vm";
    }

    /**
     * 拦截删除上行维护记录的get请求，根据主键ID删除当前所选上行维护记录
     * 
     * @param msgInRuleForm 上行维护记录表单
     * @param modelMap ModelMap视图对象
     * @param request request请求
     * @return
     */
    @RequestMapping(value = "/del.htm", method = RequestMethod.GET)
    public String deleteBlackList(MsgInRuleForm msgInRuleForm, ModelMap modelMap,
                                  HttpServletRequest request) {
        String id = request.getParameter("id");
        String word = request.getParameter("word");
        String appName = request.getParameter("appName");
        String pageId = request.getParameter("pageId");
        PageResult<UmsMsgInRuleBo> pageResult = null;
        UmsMsgInRuleBo umsMsgInRuleBo = msgInRuleForm.cloneUmsMsgInRuleBo();
        if (StringUtils.isNotEmpty(id)) {
            try {
                boolean res = msgInRuleBiz.deleteMsgInRule(id);// 根据当前所选上行维护记录ID在上行维护表中删除该记录
                if (res) {
                    addMsg(modelMap, "上行规则记录删除成功");
                } else {
                    addMsg(modelMap, "上行规则记录删除失败");
                }
                if (StringHelper.isNotEmpty(pageId)) {
                    int curPageId = Integer.parseInt(pageId);
                    pageResult = msgInRuleBiz.searchMsgInRuleByPage(umsMsgInRuleBo, curPageId);// 分页显示黑名单列表
                } else {
                    pageResult = msgInRuleBiz.searchMsgInRuleByPage(umsMsgInRuleBo, 1);
                }
                modelMap.addAttribute("results", pageResult);
                modelMap.addAttribute("word", word);
                modelMap.addAttribute("appName", appName);// 绑定上行规则的应用名
                modelMap.addAttribute("pageId", pageId);
            } catch (Exception e) {
                logger.error("删除上行规则记录出错", e);
                addErrorMsg(modelMap, "删除上行规则记录出错");
            }
        }
        return "/admin/msgInRule/msgInRuleList.vm";
    }
}
