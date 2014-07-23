/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.socket.client;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.zoneland.ums.common.util.helper.DateHelper;
import net.zoneland.ums.common.util.helper.MSExcelHelper;
import net.zoneland.ums.socket.form.SocketForm;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 
 * @author Administrator
 * @version $Id: ExportExcel.java, v 0.1 2012-10-9 上午9:49:28 Administrator Exp $
 */
public class ExportExcel {

    private static final Logger logger = Logger.getLogger(ExportExcel.class);

    /**
     * 导出消息
     */

    //    String path_base = "C:/Users/Public/Desktop" + "/SocketFormExcel";
    //    File file_b = new File(path_base);
    //    if (!file_b.exists()) {
    //        file_b.mkdir();
    //    }
    //    String path = path_base + "/" + fileName + "socket测试导出表.xls";
    //    logger.info(path);
    //    File fileOld = new File(path);
    //    if (fileOld.exists()) {
    //        fileOld.delete();
    //    }
    //    File filenew = new File(path);
    //    if (!filenew.exists()) {
    //        filenew.createNewFile();
    //    }
    //    new ExportExcel().packageExcelSocketFormlist(path, list);

    /**
     * 输出的excel
     * 
     * @param path
     * @param list
     */
    public void packageExcelSocketFormlist(String path, List<SocketForm> list) {
        List<List<Object>> sheet1 = new ArrayList<List<Object>>();

        List<Object> totallist = new ArrayList<Object>();
        // totallist.add("Socket");
        totallist.add("发送线程");
        totallist.add("响应时间(ms)");
        totallist.add("发送时间");
        totallist.add("发送状态");
        sheet1.add(totallist);
        if (list != null && list.size() > 0) {
            Iterator<SocketForm> it = list.iterator();
            while (it.hasNext()) {
                SocketForm socketForm = it.next();
                List<Object> rowlist = new ArrayList<Object>();
                rowlist.add(socketForm.getThreadName() == null ? "" : socketForm.getThreadName());
                rowlist.add(socketForm.getTime() == null ? "" : socketForm.getTime());
                rowlist.add(socketForm.getSendTime() == null ? "" : DateHelper.getStrDateByFormat(
                    socketForm.getSendTime(), "yyyy-MM-dd HH:mm:ss"));
                rowlist.add(socketForm.getStatue() == null ? "" : socketForm.getStatue());
                sheet1.add(rowlist);
            }
        }

        /**
         * 输入到excel
         */
        FileOutputStream fos = null;
        HSSFWorkbook demoWorkBook = new HSSFWorkbook();
        try {
            MSExcelHelper.writeSheetTextForxls(demoWorkBook, sheet1);
            fos = new FileOutputStream(path);
            demoWorkBook.write(fos);

        } catch (Exception e) {
            logger.error("写入excel sheet出错：" + e);
        } finally {
            try {
                fos.close();
            } catch (Exception e2) {
                logger.error("关闭FileOutputStream出错：" + e2);
            }
        }
    }
}
