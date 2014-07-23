/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.tiaotiaogift.web.home.admin;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import net.zoneland.mvc.runtime.core.autoconfig.AppConfigurer;
import net.zoneland.mvc.runtime.core.config.Configration;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * @author gag
 * @version $Id: Upload.java, v 0.1 2012-12-6 上午9:27:08 gag Exp $
 */
@Controller
public class UploadController {

    private String baseDir     = AppConfigurer.getAppProperties().getProperty("app_root");
    private String relativeDir = "upload";
    private String uploadPath;
    private String uploadHtmlPath;                                                        // 上传文件的目录
    private String tempPath;                                                              // 临时文件目录
    File           tempPathFile;

    @PostConstruct
    public void init() {
        if (!Configration.APP_RUN_MODE_PROD.equalsIgnoreCase(AppConfigurer.getAppProperties()
            .getProperty("run_mode"))) {
            System.out.println(AppConfigurer.getAppProperties().getProperty("run_mode"));
            uploadPath = baseDir + File.separator + "htdocs" + File.separator + relativeDir;
            uploadHtmlPath = baseDir + File.separator + "htdocs";
            tempPath = uploadPath + File.separator + "temp";
        }
    }

    @RequestMapping(value = "/admin/gang.htm", method = RequestMethod.GET)
    public String doshow(HttpServletRequest request, ModelMap modelMap) {

        return "admin/gang.vm";
    }

    @RequestMapping(value = "/admin/upload.htm", method = RequestMethod.POST)
    public String doUpload(HttpServletRequest request, ModelMap modelMap) {

        System.out.println(uploadPath);
        try {
            File uploadFile = new File(uploadPath);
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
            while (i.hasNext()) {
                FileItem fi = (FileItem) i.next();
                String fileName = fi.getName();
                if (fileName != null) {
                    if (fileName.endsWith(".html")) {
                        File fullFile = new File(fileName);
                        File savedFile = new File(uploadHtmlPath, fullFile.getName());
                        fi.write(savedFile);
                    } else {
                        File fullFile = new File(fileName);
                        File savedFile = new File(uploadPath, fullFile.getName());
                        fi.write(savedFile);
                    }
                }
            }
            System.out.print("upload succeed");
        } catch (Exception e) {
            // 可以跳转出错页面
            e.printStackTrace();
        }
        return "admin/uploadSuccess.vm";

    }

    @RequestMapping(value = "/admin/upload.htm", method = RequestMethod.GET)
    public String doList(HttpServletRequest request, ModelMap modelMap) {
        String url = request.getRequestURL().toString();
        String path = request.getServletPath();
        String realPath = url.substring(0, url.indexOf(path));
        File uploadFile = new File(uploadPath);
        List<String> list = new ArrayList<String>();
        try {
            findAllFile(uploadFile, list, realPath);
        } catch (UnsupportedEncodingException e) {

        }
        String callback = request.getParameter("CKEditorFuncNum");
        modelMap.addAttribute("files", list);
        modelMap.addAttribute("callback", callback);
        return "admin/list.vm";
    }

    private void findAllFile(File path, List<String> list, String domain)
                                                                         throws UnsupportedEncodingException {

        if (path.exists()) {
            File[] files = path.listFiles();
            for (int i = 0, len = files.length; i < len; ++i) {
                if (files[i].isFile() && files[i].canRead()) {
                    list.add(java.net.URLEncoder.encode(
                        domain + "/" + relativeDir + "/" + files[i].getName(), "utf-8"));
                } else if (files[i].isDirectory()) {
                    findAllFile(files[i], list, domain);
                }

            }
        }
    }

}
