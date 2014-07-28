/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.foodoon.mvc.runtime.core.uribox;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import com.foodoon.mvc.runtime.core.spring.GudaPropertyConfigurer;
import com.foodoon.mvc.runtime.core.spring.SpringBeanFactoryTool;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.foodoon.mvc.runtime.core.autoconfig.AppConfigurer;
import com.foodoon.mvc.runtime.core.xml.PropertiesReplace;

/**
 * 
 * @author gag
 * @version $Id: URIBoxManager.java, v 0.1 2012-5-29 下午5:26:48 gag Exp $
 */
public class URIBoxManager {

    private String uriConfigPath = "spring/uri-config.xml";

    public Map<String, URIBox> loadURIBox() throws ParserConfigurationException, SAXException,
                                           IOException {
        ClassLoader currentClassLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = currentClassLoader.getResourceAsStream(uriConfigPath);
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(inputStream);
        NodeList nodeList = doc.getChildNodes();

        Map<String, URIBox> map = new HashMap<String, URIBox>();
        filterNode(nodeList, map);
        return map;

    }

    public void filterNode(NodeList nodeList, Map<String, URIBox> map) {

        if (nodeList == null) {
            return;
        }
        for (int j = 0, lenJ = nodeList.getLength(); j < lenJ; ++j) {
            Node node = nodeList.item(j);
            if (node.hasAttributes()) {
                NamedNodeMap namedNodeMap = node.getAttributes();
                String name = namedNodeMap.getNamedItem("name").getNodeValue();
                String val = resolveVal(namedNodeMap.getNamedItem("value").getNodeValue());
                Node extendsNode = namedNodeMap.getNamedItem("extends");
                if (extendsNode == null) {
                    map.put(name, new DefaultURIBox(val));
                } else {
                    URIBox uriBox = map.get(extendsNode.getNodeValue());
                    if (uriBox == null) {
                        throw new RuntimeException("can not find parent node:["
                                                   + extendsNode.getNodeValue() + "].");
                    }
                    DefaultURIBox box = new DefaultURIBox();
                    box.copy((DefaultURIBox) uriBox);
                    box.addPath(val);
                    map.put(name, box);
                }

            }
            filterNode(node.getChildNodes(), map);

        }

    }

    public String resolveVal(String strKey) {
        GudaPropertyConfigurer p = (GudaPropertyConfigurer)  SpringBeanFactoryTool.getBeanFactory().getBean("gudaPropertyConfigurer");
        if (strKey.startsWith(PropertiesReplace.DEFAULT_PLACEHOLDER_PREFIX)) {
            String key = PropertiesReplace.resolveStringKey(strKey);
            return String.valueOf(p.getAppProperties().get(key));
        }
        return strKey;
    }

    /**
     * Setter method for property <tt>uriConfigPath</tt>.
     * 
     * @param uriConfigPath value to be assigned to property uriConfigPath
     */
    public void setUriConfigPath(String uriConfigPath) {
        this.uriConfigPath = uriConfigPath;
    }

}
