/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.foodoon.monitor.web.home;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.csvreader.CsvWriter;
import com.foodoon.monitor.dal.SysMapper;
import com.foodoon.monitor.dal.dataobject.Sys;
import com.foodoon.monitor.enums.ValueTypeEnums;

/**
 * 
 * @author foodoon
 * @version $Id: ExportXlsController.java, v 0.1 2013年10月2日 下午2:12:17 foodoon Exp $
 */
@Controller
public class ExportXlsController {

    @Autowired
    private SysMapper sysMapper;

    @RequestMapping(value = "/exportXls.htm")
    public void doExport(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
                                                                                                     throws UnsupportedEncodingException {
        String k = request.getParameter("k");
        String host = request.getParameter("host");
        Date startTime = LoadDataController.getDate(request.getParameter("startTime"));
        Date endTime = LoadDataController.getDate(request.getParameter("endTime"));
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("key", k);
        params.put("ip", host);
        if (startTime.after(endTime)) {
            params.put("startTime", endTime);
            params.put("endTime", startTime);
        } else {
            params.put("startTime", startTime);
            params.put("endTime", endTime);
        }
        List<Sys> sys = sysMapper.selectByType(params);
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/vnd.ms-Excel;charset=utf-8");

        response.setHeader("Content-disposition",
            "attachment;filename=" + new String("导出指标".getBytes("GBK"), "iso-8859-1") + ".csv");
        OutputStream os = null;

        CsvWriter writer = null;
        try {
            os = response.getOutputStream();
            writer = new CsvWriter(os, ',', Charset.forName("UTF-8"));// shift_jis日语字体  
            writer.writeRecord(new String[] { "ip", "指标", "值", "记录时间" });
            Iterator<Sys> it = sys.iterator();
            while (it.hasNext()) {
                Sys ss = it.next();
                if (ss.getValueType() == ValueTypeEnums.dbFloat.getValue()
                    || ss.getValueType() == ValueTypeEnums.serverFloat.getValue()) {
                    writer.writeRecord(new String[] { ss.getIp(), ss.getK(),
                            String.valueOf(ss.getVal()),
                            LoadDataController.getDateStr(ss.getGmtCreated()) });
                } else {
                    writer.writeRecord(new String[] { ss.getIp(), ss.getK(),
                            String.valueOf(ss.getValText()).replaceAll(",", "，"),
                            LoadDataController.getDateStr(ss.getGmtCreated()) });
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            writer.close();
            try {
                os.close();
            } catch (IOException e) {

            }
        }

    }
}
