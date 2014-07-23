/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.foodoon.monitor.web.home.admin;

import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.foodoon.monitor.web.home.BaseController;
import com.foodoon.monitor.web.home.TimeHelper;
import com.foodoon.monitor.web.home.vo.Response;

/**
 * 
 * @author foodoon
 * @version $Id: KeyController.java, v 0.1 2013-6-19 上午7:44:57 foodoon Exp $
 */
@Controller
public class KeyController extends BaseController {

    @RequestMapping(value = "/key.htm", method = RequestMethod.GET)
    public String doGet(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        return "key.vm";
    }

    @RequestMapping(value = "createKey.json")
    public void createKey(HttpServletRequest request, HttpServletResponse response,
                          ModelMap modelMap) {

        String k = (request.getParameter("key"));
        String validTime = request.getParameter("validTime");
        Response res = new Response();

        res.setSuccess(true);
        res.setMsg(md5(k, validTime));

        super.ajaxReturn(res, response);

    }

    public final static String md5(String plainText, String date) {
        if (plainText == null) {
            return null;
        }
        if (!StringUtils.hasText(date)) {
            date = "2099-12-31";
        }
        String[] d = date.split("-");
        String real = d[0] + d[1] + d[2];
        real = TimeHelper.encodeTime(real);
        plainText = plainText + "medi";
        String md5Str = null;
        try {
            StringBuffer buf = new StringBuffer();
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            md5Str = buf.toString();
            System.out.println(md5Str);
            String prefix = md5Str.substring(0, 3);
            String suffix = md5Str.substring(3, md5Str.length());
            return prefix + real + suffix;

        } catch (Exception e) {

        }
        return md5Str;
    }

    public static void main(String[] arg) {
        String line = md5("123", "2013-10-11");

        if (line.length() == 40) {
            String d = line.substring(3, 11);
            d = TimeHelper.decodeTime(d);
            System.out.println(d);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            try {
                Date date = dateFormat.parse(d);
                if (date.getTime() < new Date().getTime()) {
                    throw new RuntimeException("序列号过期");
                }
            } catch (ParseException e) {

            }
            String k = line.substring(0, 3) + line.substring(11);
            System.out.println(k);
        } else {
            throw new RuntimeException("序列号错误");
        }
    }
}
