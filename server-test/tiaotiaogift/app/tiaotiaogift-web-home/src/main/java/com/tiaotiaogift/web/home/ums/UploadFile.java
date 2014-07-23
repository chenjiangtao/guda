/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.tiaotiaogift.web.home.ums;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;

/**
 * 
 * @author gang
 * @version $Id: UploadFile.java, v 0.1 2013-4-27 下午10:32:44 gang Exp $
 */
public class UploadFile {

    public static String tempDir;

    public static String rootDir;

    public static String doUpload(HttpServletRequest request) throws ServletException, IOException {
        //设置request编码，主要是为了处理普通输入框中的中文问题

        String temp = System.getProperty("user.home");
        tempDir = temp + File.separator + "foodoon" + File.separator + "temp";

        rootDir = temp + File.separator + "foodoon" + File.separator;
        File tempPathFile = new File(tempDir);
        if (!tempPathFile.exists()) {
            tempPathFile.mkdirs();
        }
        request.setCharacterEncoding("utf-8");
        RequestContext requestContext = new ServletRequestContext(request);

        DiskFileItemFactory factory = new DiskFileItemFactory();
        //设置文件的缓存路径
        factory.setRepository(new File(tempDir));
        ServletFileUpload upload = new ServletFileUpload(factory);
        //设置上传文件大小的上限，-1表示无上限
        upload.setSizeMax(1024000); // 设置最大文件尺寸，这里是4MB
        List items = new ArrayList();
        try {
            //上传文件，并解析出所有的表单字段，包括普通字段和文件字段
            items = upload.parseRequest(request);
        } catch (FileUploadException e1) {
            System.out.println("文件上传发生错误" + e1.getMessage());
        }
        //下面对每个字段进行处理，分普通字段和文件字段
        Iterator it = items.iterator();
        while (it.hasNext()) {
            FileItem fileItem = (FileItem) it.next();
            //保存文件，其实就是把缓存里的数据写到目标路径下
            if (fileItem.getName() != null && fileItem.getSize() != 0) {
                File fullFile = new File(fileItem.getName());
                String f = rootDir + fullFile.getName();
                File newFile = new File(f);
                try {
                    fileItem.write(newFile);
                    return f;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("文件没有选择 或 文件内容为空");
            }

        }

        return null;
    }
}
