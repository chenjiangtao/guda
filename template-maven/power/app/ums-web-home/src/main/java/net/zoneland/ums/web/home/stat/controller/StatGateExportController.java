/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package net.zoneland.ums.web.home.stat.controller;

import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.zoneland.ums.biz.msg.stat.GateStatService;
import net.zoneland.ums.biz.msg.stat.bo.GateStatBo;
import net.zoneland.ums.biz.msg.stat.bo.UmsStatVo;
import net.zoneland.ums.common.util.helper.DateHelper;
import net.zoneland.ums.common.util.helper.MSExcelHelper;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author gang
 * @version $Id: StatGateExportController.java, v 0.1 2013-3-27 下午1:43:48 gang Exp $
 */
@Controller
public class StatGateExportController {

    private static final Logger logger = Logger.getLogger(StatGateExportController.class);

    @Autowired
    private GateStatService     gateStatService;

    /** 
     * @see org.springframework.web.servlet.view.document.AbstractExcelView#buildExcelDocument(java.util.Map, org.apache.poi.hssf.usermodel.HSSFWorkbook, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @RequestMapping(value = "/stat/exportGateStatExcel.htm")
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook,
                                      HttpServletRequest request, HttpServletResponse response)
                                                                                               throws Exception {
        response.reset();
        response.setContentType("APPLICATION/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=\"export.xls\"");
        GateStatBo bo = convert2(request);
        List<UmsStatVo> list = null;
        try {
            list = gateStatService.searchGateStatForExport(bo);
        } catch (Exception e) {
            logger.error("查询统计上行短信初始化页面出现异常：", e);
        }
        List<List<Object>> sheet1 = new ArrayList<List<Object>>();
        List<Object> totallist = new ArrayList<Object>();
        totallist.add("日期");
        totallist.add("应用ID");
        totallist.add("应用名");
        totallist.add("短信类型");
        totallist.add("移动95598");
        totallist.add("移动106");
        totallist.add("联通95598");
        totallist.add("联通106");
        totallist.add("电信106");
        totallist.add("95598合计");
        totallist.add("106合计");
        sheet1.add(totallist);
        if (list != null && list.size() > 0) {
            Iterator<UmsStatVo> it = list.iterator();
            while (it.hasNext()) {
                UmsStatVo vo = it.next();
                List<Object> rowlist = new ArrayList<Object>();
                rowlist.add(filter(DateHelper.getStrDateByFormat(vo.getUmsStat().getStatDate(),
                    "yyyy-MM-dd")));// 发送方手机号           
                rowlist.add(filter(vo.getUmsStat().getAppId()));// 接收方手机号
                rowlist.add(filter(vo.getAppName()));// 短信内容  
                rowlist.add(filter(vo.getUmsStat().getMsgType()));// 所属应用
                rowlist.add(filter(vo.getUmsStat().getCmpp95598()));// 所属运营商              
                rowlist.add(filter(vo.getUmsStat().getCmpp106()));// 状态
                rowlist.add(filter(vo.getUmsStat().getSgip95598()));
                rowlist.add(filter(vo.getUmsStat().getSgip106()));
                rowlist.add(filter(vo.getUmsStat().getSmgp106()));
                rowlist.add(filter(vo.getUmsStat().getStat95598()));
                rowlist.add(filter(vo.getUmsStat().getStat106()));
                sheet1.add(rowlist);
            }
        }
        MSExcelHelper.writeSheetTextForxls(workbook, sheet1);
        OutputStream os = response.getOutputStream();
        workbook.write(os);
        os.flush();
        os.close();
    }

    private String filter(Object str) {
        if (str == null) {
            return "";
        }
        return str.toString();
    }

    private GateStatBo convert2(HttpServletRequest request) {
        GateStatBo bo = new GateStatBo();
        String smsType = request.getParameter("smsType");
        if (StringUtils.hasText(smsType)) {
            bo.setType(smsType);
        }

        Date startTime = getDate(request.getParameter("startTime"));
        Date endTime = getDate(request.getParameter("endTime"));
        if (startTime != null && endTime != null) {
            if (startTime.after(endTime)) {
                Date t = startTime;
                startTime = endTime;
                endTime = t;
            }
            bo.setStartTime(startTime);
            bo.setEndTime(endTime);
        }
        return bo;

    }

    private Date getDate(String date) {
        if (!StringUtils.hasText(date)) {
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {

        }
        return null;
    }

}
