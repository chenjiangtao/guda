/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.web.home.stat.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.zoneland.ums.biz.config.admin.AllAppMsgBiz;
import net.zoneland.ums.biz.config.gateway.GatewayService;
import net.zoneland.ums.biz.msg.search.service.MsgSearchService;
import net.zoneland.ums.common.dal.bo.AppMsgInfoBO;
import net.zoneland.ums.common.dal.bo.UmsMsgOutDO;
import net.zoneland.ums.common.dal.dataobject.UmsGateWayInfo;
import net.zoneland.ums.common.dal.dataobject.UmsMsgOut;
import net.zoneland.ums.common.util.enums.GateEnum;
import net.zoneland.ums.common.util.enums.msg.MsgInfoStatusEnum;
import net.zoneland.ums.common.util.helper.DateHelper;
import net.zoneland.ums.common.util.helper.StringHelper;
import net.zoneland.ums.common.util.page.PageResult;
import net.zoneland.ums.web.home.admin.form.GateWayForm;
import net.zoneland.ums.web.home.stat.form.AppMsgInfoForm;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author yangjuanying
 * @version $Id: AllAppDataMsgController.java, v 0.1 2012-10-13 下午5:39:47 yangjuanying Exp $
 */
@Controller
@RequestMapping("stat")
public class SearchDataMsgController {
    private static final Logger logger = Logger.getLogger(SearchDataMsgController.class);

    @Autowired
    private AllAppMsgBiz        allAppMsgBiz;

    @Autowired
    private MsgSearchService    msgSearchService;

    @Autowired
    private GatewayService      gatewayService;

    /**
     * 初始化时的查询统计页面(刚进入查询统计页面时，默认显示什么都不查)
     * 
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/searchDataInit.htm")
    public String doGet(AppMsgInfoForm form, ModelMap modelMap) throws Exception {
        List<GateWayForm> gatewayFormInfos = new ArrayList<GateWayForm>();
        Date date = new Date();
        String startTime = DateHelper.getPreStrDateByFormat(date, "yyyy-MM-dd");
        form.setStartTime(startTime);
        String endTime = DateHelper.getStrDateByFormat(date, "yyyy-MM-dd");
        form.setEndTime(endTime);
        try {
            List<UmsGateWayInfo> gatewayInfos = gatewayService.findAll();// 获取全部网关信息  
            if (gatewayInfos != null && gatewayInfos.size() > 0) {
                Set<String> TypeSets = new HashSet<String>();
                for (UmsGateWayInfo gatewayInfo : gatewayInfos) {
                    TypeSets.add(gatewayInfo.getType());
                }
                for (String TypeSet : TypeSets) {// 用到了Set集合的元素不能重复特性,将运营商相同的网关类型去重
                    GateWayForm gatewayForm = new GateWayForm();
                    gatewayForm.setType(TypeSet);
                    gatewayForm.setGatewayName(GateEnum.getDescription(TypeSet));
                    gatewayFormInfos.add(gatewayForm);
                }
            }
        } catch (Exception e) {
            logger.error("查询统计数据短信初始化页面出现异常：", e);
        }
        modelMap.addAttribute("init", 0);//标记这是初始化页面要显示的提示
        modelMap.addAttribute("msg", form);
        modelMap.addAttribute("gatewayFormInfos", gatewayFormInfos);// 运营商下拉框
        modelMap.addAttribute("statelist", MsgInfoStatusEnum.values());// 状态
        return "stat/searchData.vm";
    }

    /**
     * 拦截条件查询统计全部应用【数据短信】的get和post请求
     * 
     * @param form
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/searchData.htm")
    public String searchData(AppMsgInfoForm form, HttpServletRequest request,
                             HttpServletResponse response, ModelMap modelMap) throws Exception {
        AppMsgInfoBO appMsgInfoBO = new AppMsgInfoBO();
        appMsgInfoBO = getAppMsgInfoBO(form);// 将前端页面获取到的对象转换成为业务层提供操作的对象
        int pageId = StringHelper.parseInt(form.getPageId());
        List<GateWayForm> gatewayFormInfos = new ArrayList<GateWayForm>();
        PageResult<UmsMsgOut> result = new PageResult<UmsMsgOut>();
        try {
            result = allAppMsgBiz.searchAllAppMsgUcs(appMsgInfoBO, pageId);
            List<UmsGateWayInfo> gatewayInfos = gatewayService.findAll();// 获取全部网关信息
            if (gatewayInfos != null && gatewayInfos.size() > 0) {
                Set<String> TypeSets = new HashSet<String>();
                for (UmsGateWayInfo gatewayInfo : gatewayInfos) {
                    TypeSets.add(gatewayInfo.getType());
                }
                for (String TypeSet : TypeSets) {// 用到了Set集合的元素不能重复特性,将运营商相同的网关类型去重
                    GateWayForm gatewayForm = new GateWayForm();
                    gatewayForm.setType(TypeSet);
                    gatewayForm.setGatewayName(GateEnum.getDescription(TypeSet));
                    gatewayFormInfos.add(gatewayForm);
                }
            }
        } catch (Exception e) {
            logger.error("查询统计数据短信出现异常：", e);
        }
        modelMap.addAttribute("result", result);
        modelMap.addAttribute("msg", form);
        modelMap.addAttribute("gatewayFormInfos", gatewayFormInfos);// 运营商下拉框
        modelMap.addAttribute("statelist", MsgInfoStatusEnum.values());// 状态
        return "stat/searchData.vm";
    }

    /**
     * 拦截查询统计全部应用【数据短信】导出excel表的请求
     * 
     * @param request
     * @param response
     * @param form
     * @param modelMap
     * @throws Exception
     */
    @RequestMapping(value = "/exportDataMsgExcel.htm")
    public void exportDataMsgExcel(HttpServletRequest request, HttpServletResponse response,
                                   AppMsgInfoForm form, ModelMap modelMap) throws Exception {
        AppMsgInfoBO appMsgInfoBO = new AppMsgInfoBO();
        appMsgInfoBO = getAppMsgInfoBO(form);// 将前端页面获取到的对象转换成为业务层提供操作的对象 
        // 导出消息    
        String path_base = request.getSession().getServletContext().getRealPath("/")
                           + "/MsgOutUcsExcel";
        File file_b = new File(path_base);
        if (!file_b.exists()) {
            file_b.mkdir();
        }
        String path = path_base + "/查询统计数据短信导出表.xls";
        File fileOld = new File(path);
        if (fileOld.exists()) {
            fileOld.delete();
        }
        File filenew = new File(path);
        if (!filenew.exists()) {
            filenew.createNewFile();
        }
        try {
            allAppMsgBiz.exportDataMsgExcel(appMsgInfoBO, path);
            // 输出
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/vnd.ms-Excel;charset=utf-8");
            String fileName = new String("查询统计数据短信导出表".getBytes("GBK"), "iso-8859-1") + ".xls";
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            File file = new File(path);
            logger.debug(file.getAbsolutePath());
            InputStream inputStream = new FileInputStream(file);
            OutputStream os = response.getOutputStream();
            byte[] b = new byte[1024];
            int length;
            while ((length = inputStream.read(b)) > 0) {
                os.write(b, 0, length);
            }
            inputStream.close();
            os.close();
        } catch (FileNotFoundException e) {
            logger.error("查询统计数据短信导出表文件没有找到：" + e);
        } catch (IOException e) {
            logger.error("查询统计数据短信导出表IO读写异常：" + e);
        }
    }

    /**
     * 查询统计数据短信页面的详细短信信息
     * 
     * @param form
     * @param modelMap
     * @param request
     * @return
     */
    @RequestMapping("/searchDataMsgDetail.htm")
    public String searchDataMsgDetail(AppMsgInfoForm form, ModelMap modelMap,
                                      HttpServletRequest request) {
        String id = request.getParameter("id");
        UmsMsgOutDO umsMsgOut = new UmsMsgOutDO();
        if (StringHelper.isNotEmpty(id)) {
            umsMsgOut = msgSearchService.getDataMsgInfoById(id);
        }
        modelMap.addAttribute("msgInfo", umsMsgOut);
        modelMap.addAttribute("msgId", id);
        modelMap.addAttribute("msg", form);
        return "stat/searchDataMsgDetail.vm";
    }

    /**
     * 将前端页面获取到的对象转换成为业务层提供操作的对象
     * 
     * @param form
     * @return
     */
    private AppMsgInfoBO getAppMsgInfoBO(AppMsgInfoForm form) {
        AppMsgInfoBO appMsgInfoBO = new AppMsgInfoBO();
        if (form != null) {
            if (form.getStartTime() != null && !"".equals(form.getStartTime())) {
                try {
                    String startTime = form.getStartTime() + " 00:00:00";
                    Calendar cstart = DateHelper.gc(startTime, "yyyy-MM-dd HH:mm:ss");
                    appMsgInfoBO.setStartTime(new Timestamp(cstart.getTimeInMillis()));
                } catch (Exception e) {
                    logger.error("时间转化失败" + e);
                }
            } else {
                Date date = new Date();
                String startTime = DateHelper.getPreStrDateByFormat(date, "yyyy-MM-dd");
                form.setStartTime(startTime);
                startTime = startTime + " 00:00:00";
                try {
                    Calendar cstart = DateHelper.gc(startTime, "yyyy-MM-dd HH:mm:ss");
                    appMsgInfoBO.setStartTime(new Timestamp(cstart.getTimeInMillis()));
                } catch (ParseException e) {
                    logger.error("时间转化失败", e);
                }
            }
            if (form.getEndTime() != null && !"".equals(form.getEndTime())) {
                try {
                    String endTime = form.getEndTime() + " 23:59:59";
                    Calendar cend = DateHelper.gc(endTime, "yyyy-MM-dd HH:mm:ss");
                    appMsgInfoBO.setEndTime(new Timestamp(cend.getTimeInMillis()));
                } catch (Exception e) {
                    logger.error("时间转化失败" + e);
                }
            } else {
                Date date = new Date();
                String endTime = DateHelper.getStrDateByFormat(date, "yyyy-MM-dd");
                form.setEndTime(endTime);
                endTime = endTime + " 23:59:59";
                try {
                    Calendar cEnd = DateHelper.gc(endTime, "yyyy-MM-dd HH:mm:ss");
                    appMsgInfoBO.setEndTime(new Timestamp(cEnd.getTimeInMillis()));
                } catch (ParseException e) {
                    logger.error("时间转化失败", e);
                }
            }
            appMsgInfoBO.setStatus("".equals(form.getStatus()) ? null : form.getStatus());// 获取条件：状态
            appMsgInfoBO.setAppName(StringUtils.isEmpty(form.getAppName()) ? null : form
                .getAppName());
            if ("".equals(form.getAppId())) {// 应用ID
                appMsgInfoBO.setAppId(null);
            } else {
                appMsgInfoBO.setAppId(form.getAppId());
            }
            appMsgInfoBO.setSendId(StringUtils.isEmpty(form.getMsgDest()) ? null : form
                .getMsgDest());// 发送方手机号
            appMsgInfoBO.setMsgdestName(StringUtils.isEmpty(form.getMsgdestName()) ? null : form
                .getMsgdestName());// 发送人员姓名
            appMsgInfoBO.setMsgsrcName(StringUtils.isEmpty(form.getMsgsrcName()) ? null : form
                .getMsgsrcName());// 接收人员姓名
            appMsgInfoBO.setRecvId(StringUtils.isEmpty(form.getMsgSrc()) ? null : form.getMsgSrc());// 获取接收方手机号
            appMsgInfoBO.setBizType(StringUtils.isEmpty(form.getBizType()) ? null : form
                .getBizType());// 业务类型
            appMsgInfoBO.setBizName(StringUtils.isEmpty(form.getBizName()) ? null : form
                .getBizName());// 业务系统
            appMsgInfoBO.setCreateUser(StringUtils.isEmpty(form.getCreateUser()) ? null : form
                .getCreateUser());// 生成人员
            appMsgInfoBO.setOrgNo(StringUtils.isEmpty(form.getOrgNo()) ? null : form.getOrgNo());//  组织号
            appMsgInfoBO.setFlowNo(StringUtils.isEmpty(form.getFlowNo()) ? null : form.getFlowNo());// 流程号
            if ("".equals(form.getGatewaytype())) {
                appMsgInfoBO.setGatewaytype(null);
            } else {
                appMsgInfoBO.setGatewaytype(form.getGatewaytype());
            }
        }
        return appMsgInfoBO;
    }
}
