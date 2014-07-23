package com.foodoon.gooda.gen;

import com.foodoon.gooda.velocity.VelocityHelper;
import org.dom4j.Document;
import org.dom4j.Element;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by foodoon on 2014/6/27.
 */
public class GenBiz {

    private String bizVmName = "biz.vm";

    private String bizImplVmName = "biz-impl.vm";

    private GenContext genContext;

    public GenBiz(GenContext genContext){
        this.genContext = genContext;
    }

    public void gen() throws Exception {
        genBizXml();
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("doName",genContext.getDoName());
        params.put("appName",genContext.getAppName());
        params.put("doNameLower",genContext.getDoNameLower());
        String render = VelocityHelper.render(bizVmName, params);
        String bizClass = genContext.getBizFile();
        File file = new File(bizClass);
        file.getParentFile().mkdirs();
        if (file.exists()) {
            bizClass+=".c";
        }
        file = new File(bizClass);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(render.getBytes("UTF-8"));
        fileOutputStream.close();

        render = VelocityHelper.render(bizImplVmName, params);
        bizClass = genContext.getBizImplFile();
        file = new File(bizClass);
        file.getParentFile().mkdirs();
        if (file.exists()) {
            bizClass+=".c";
        }
        file = new File(bizClass);
        fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(render.getBytes("UTF-8"));
        fileOutputStream.close();

    }

    public void genBizXml(){
        String bizXML = genContext.getBizXmlFile();
        File file = new File(bizXML);
        if (!file.exists()) {
            Document document = SpringXml.createDocument();
            Element springXmlHeader = SpringXml.createSpringXmlHeader(document);
            SpringXml.createBean(springXmlHeader, genContext.getDoNameLower() + "Biz", "com.foodoon." + genContext.getAppName() + ".biz.impl." + genContext.getDoName() + "BizImpl");
            SpringXml.write(bizXML, document);

        }
        Document document = SpringXml.loadXML(bizXML);
        if (!SpringXml.hasBean(document, genContext.getDoNameLower() + "Biz")) {
            SpringXml.createBean(SpringXml.getSpringRoot(document), genContext.getDoNameLower() + "Biz", "com.foodoon." + genContext.getAppName() + ".biz.impl." + genContext.getDoName() + "BizImpl");
            SpringXml.write(bizXML, document);
        }

    }

}
