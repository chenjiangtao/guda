/**
 * tiaotiaogift.com Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.tiaotiaogift.web.home.personal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import net.zoneland.mvc.runtime.core.security.OperationContextHolder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import au.com.bytecode.opencsv.CSVReader;

import com.tiaotiaogift.common.dal.ContactMapper;
import com.tiaotiaogift.common.mysql.dataobject.Contact;
import com.tiaotiaogift.web.home.personal.form.ContactForm;
import com.tiaotiaogift.web.home.ums.UploadFile;

/**
 * 
 * @author gang
 * @version $Id: PersonalController.java, v 0.1 2012-12-15 上午9:28:05 gang Exp $
 */
@Controller
public class ContactController {

    @Autowired
    private ContactMapper contactMapper;

    @RequestMapping(value = "/personal/contact.htm", method = RequestMethod.GET)
    public String doGet(ContactForm form, BindingResult result, HttpServletRequest request,
                        ModelMap modelMap) {

        return "ums/personal/contact.vm";
    }

    @RequestMapping(value = "/personal/importTaobao.htm", method = RequestMethod.GET)
    public String showImportTaobao(ContactForm form, BindingResult result,
                                   HttpServletRequest request, ModelMap modelMap) {

        return "ums/personal/importTaobao.vm";
    }

    @RequestMapping(value = "/personal/importTaobao.htm", method = RequestMethod.POST)
    public String doImportTaobao(ContactForm form, BindingResult result,
                                 HttpServletRequest request, ModelMap modelMap) {
        String f = null;
        try {
            f = UploadFile.doUpload(request);

            CSVReader reader = new CSVReader(new BufferedReader(new InputStreamReader(
                new FileInputStream(f), "GBK")));
            String[] nextLine;
            List<Contact> list = new ArrayList<Contact>();
            //跳过标题行
            reader.readNext();
            while ((nextLine = reader.readNext()) != null) {
                Contact c = new Contact();
                c.setId(UUID.randomUUID().toString());
                c.setUserId(OperationContextHolder.getPrincipal().getUserId());
                c.setName(nextLine[1]);
                c.setPhone(nextLine[16].substring(1));
                c.setTaobaoId(nextLine[0]);
                c.setTaobaoName(nextLine[1]);
                c.setTaobaoOrderStatus(nextLine[10]);
                c.setAddress(nextLine[13]);
                c.setDeliveryName(nextLine[22]);
                c.setDeliveryNo(nextLine[21]);
                c.setGmtCreated(new Date());
                list.add(c);

            }
            Iterator<Contact> it = list.iterator();
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("userId", OperationContextHolder.getPrincipal().getUserId());
            while (it.hasNext()) {
                Contact c = it.next();
                params.put("phone", c.getPhone());
                contactMapper.delByPhone(params);
            }
            if (list.size() > 0) {
                int res = contactMapper.insertBatch(list);
                if (res > 0) {
                    modelMap.addAttribute("tips", "成功导入" + res + "个联系人");
                } else {
                    modelMap.addAttribute("tips", "抱歉，导入失败,服务器错误。");
                }
            } else {
                modelMap.addAttribute("tips", "抱歉，导入失败,请检查文件是否有记录。");
            }
        } catch (Exception e) {
            modelMap.addAttribute("tips", "抱歉，导入失败,服务器错误，错误信息:" + e.getMessage());
            e.printStackTrace();
        }
        if (f != null) {
            File ff = new File(f);
            ff.deleteOnExit();

        }
        return "ums/personal/importTaobaoSuccess.vm";
    }

}
