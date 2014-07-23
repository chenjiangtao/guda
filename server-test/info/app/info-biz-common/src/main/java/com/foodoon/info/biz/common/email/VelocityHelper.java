package com.foodoon.info.biz.common.email;

import java.io.StringWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

public class VelocityHelper {

    public static VelocityEngine ve = null;

    public static synchronized void init(String basePath) {
        if (ve == null) {
            ve = new VelocityEngine();
            ve.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, basePath);
            ve.setProperty("resource.loader", "file");
            ve.setProperty("input.encoding", "utf-8");
            // 若是从文件体系中读取模板，那么属性值为org.apache.velocity.runtime.resource.loader.FileResourceLoader
            ve.setProperty("class.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
            try {
                ve.init();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String renderVm(String basePath, String vmPath, Map<String, Object> params)
                                                                                             throws ResourceNotFoundException,
                                                                                             ParseErrorException,
                                                                                             Exception {
        if (ve == null) {
            init(basePath);
        }
        Template t = ve.getTemplate(vmPath);
        VelocityContext context = new VelocityContext();
        Set<Entry<String, Object>> set = params.entrySet();
        Iterator<Entry<String, Object>> it = set.iterator();
        while (it.hasNext()) {
            Entry<String, Object> en = it.next();
            context.put(en.getKey(), en.getValue());
        }
        StringWriter writer = new StringWriter();
        t.merge(context, writer);
        return (writer.toString());
    }

    public static String renderString(String template, Map<String, Object> params)
                                                                                  throws ResourceNotFoundException,
                                                                                  ParseErrorException,
                                                                                  Exception {

        Properties props = new Properties();

        props.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
        props.setProperty(Velocity.RESOURCE_LOADER, "class");
        props.setProperty("class.resource.loader.class",
            "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        VelocityEngine engine = new VelocityEngine(props);
        VelocityContext context = new VelocityContext();
        Set<Entry<String, Object>> set = params.entrySet();
        Iterator<Entry<String, Object>> it = set.iterator();
        while (it.hasNext()) {
            Entry<String, Object> en = it.next();
            context.put(en.getKey(), en.getValue());
        }
        StringWriter writer = new StringWriter();
        engine.evaluate(context, writer, "", template);
        return writer.toString();
    }

}
