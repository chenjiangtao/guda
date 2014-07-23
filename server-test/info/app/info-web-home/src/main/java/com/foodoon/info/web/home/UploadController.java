/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.foodoon.info.web.home;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.zoneland.mvc.runtime.core.autoconfig.AppConfigurer;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.foodoon.info.common.dal.ImgMapper;
import com.foodoon.info.common.dataobject.Img;
import com.foodoon.info.common.util.DateUtil;
import com.foodoon.info.web.home.vo.UploadVo;

/**
 * @author foodoon
 * @version $Id: UploadController.java, v 0.1 2013年10月19日 下午8:18:13 foodoon Exp $
 */
@Controller
public class UploadController extends BaseAjaxController {

    private static final String baseDir       = AppConfigurer.getAppProperties().getProperty("app_root");
    public static final String  baseUploadDir = baseDir + File.separator + "htdocs";
    private static String       relativeDir   = "upload";
    private static String       uploadPath;                                                              // 上传文件的目录
    private static String       tempPath;                                                                // 临时文件目录
    File                        tempPathFile;

    @Autowired
    private ImgMapper           imgMapper;

    static {
        // if
        // (!Configration.APP_RUN_MODE_PROD.equalsIgnoreCase(AppConfigurer.getAppProperties().getProperty("run_mode")))
        // {

        uploadPath = baseUploadDir + File.separator + relativeDir;

        tempPath = uploadPath + File.separator + "temp";
        // }
    }

    @RequestMapping(value = "/prod/upload.htm", method = RequestMethod.GET)
    public String doUpload(HttpServletRequest request, ModelMap modelMap) {

        return "index.vm";
    }

    @RequestMapping(value = "/prod/upload.htm", method = RequestMethod.POST)
    public void doPost(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {

        String path = uploadPath + File.separator + DateUtil.getTodayStr();
        try {
            File uploadFile = new File(path);
            if (!uploadFile.exists()) {
                uploadFile.mkdirs();
            }
            File tempPathFile = new File(tempPath);
            if (!tempPathFile.exists()) {
                tempPathFile.mkdirs();
            }

            DiskFileItemFactory factory = new DiskFileItemFactory();

            factory.setSizeThreshold(4096); // 设置缓冲区大小，这里是4kb
            factory.setRepository(tempPathFile);// 设置缓冲区目录

            ServletFileUpload upload = new ServletFileUpload(factory);

            upload.setSizeMax(1024000); // 设置最大文件尺寸，这里是4MB

            List<FileItem> items = upload.parseRequest(request);// 得到所有的文件
            Iterator<FileItem> i = items.iterator();
            String fileName = null;
            List<UploadVo> vos = new ArrayList<UploadVo>();
            String t = (String) request.getSession().getAttribute(PublishController.PUB_TOKEN);

            while (i.hasNext()) {
                FileItem fi = (FileItem) i.next();
                fileName = getFileName(fi.getName());
                if (fileName != null) {

                    File savedFile = new File(path, fileName);
                    fi.write(savedFile);
                    Img img = new Img();
                    String id = UUID.randomUUID().toString();
                    img.setId(id);
                    String urlPath = "/" + relativeDir + "/" + DateUtil.getTodayStr() + "/" + fileName;
                    img.setPath(urlPath);
                    img.setGmtCreated(new Date());
                    img.setDetailId(t);
                    imgMapper.insert(img);
                    UploadVo vo = new UploadVo();
                    vo.setImgId(id);
                    vo.setPath(urlPath);
                    vos.add(vo);
                }
            }
            super.ajaxReturn(vos, response);
        } catch (Exception e) {
            // 可以跳转出错页面
            e.printStackTrace();
        }
    }

    private String getFileName(String fileName) {

        if (fileName != null) {
            return UUID.randomUUID().toString() + fileName.substring(fileName.indexOf("."), fileName.length());
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println("Tulips.jpg".indexOf("."));
    }

}
