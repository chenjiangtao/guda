/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.service.socket.rule;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * 
 * @author gag
 * @version $Id: ParamRuleManager.java, v 0.1 2012-8-10 下午4:45:23 gag Exp $
 */
public class ParamRuleManager {

    private static String                                  paramRuleConfigPath = "spring/param-rule.xml";

    private static Map<String, Map<String, ParamRuleInfo>> ruleMap;

    static {
        try {
            ruleMap = loadParamRule();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, Map<String, ParamRuleInfo>> getRuleMap() {
        return ruleMap;
    }

    public static Map<String, Map<String, ParamRuleInfo>> loadParamRule()
                                                                         throws ParserConfigurationException,
                                                                         SAXException, IOException {
        ClassLoader currentClassLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = currentClassLoader.getResourceAsStream(paramRuleConfigPath);
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(inputStream);
        NodeList nodeList = doc.getChildNodes();

        Map<String, Map<String, ParamRuleInfo>> map = new HashMap<String, Map<String, ParamRuleInfo>>();

        filterNode(nodeList.item(0).getChildNodes(), map);
        return map;

    }

    public static void filterNode(NodeList nodeList, Map<String, Map<String, ParamRuleInfo>> map) {

        if (nodeList == null) {
            return;
        }
        for (int j = 0, lenJ = nodeList.getLength(); j < lenJ; ++j) {
            Node node = nodeList.item(j);
            if (node.hasAttributes()) {
                NamedNodeMap namedNodeMap = node.getAttributes();
                String code = namedNodeMap.getNamedItem("code").getNodeValue();
                Map<String, ParamRuleInfo> paramMap = new LinkedHashMap<String, ParamRuleInfo>();
                if (node.hasChildNodes()) {
                    NodeList childNodeList = node.getChildNodes();
                    for (int i = 0, leni = childNodeList.getLength(); i < leni; ++i) {
                        Node childnode = childNodeList.item(i);
                        if (childnode.hasAttributes()) {
                            NamedNodeMap childnamedNodeMap = childnode.getAttributes();
                            String name = childnamedNodeMap.getNamedItem("name").getNodeValue();
                            String length = childnamedNodeMap.getNamedItem("length").getNodeValue();
                            Node notNull = childnamedNodeMap.getNamedItem("notNull");

                            ParamRuleInfo pri = new ParamRuleInfo();
                            pri.setLength(Integer.parseInt(length.trim()));
                            if (notNull != null && "true".equalsIgnoreCase(notNull.getNodeValue())) {
                                pri.setNotNull(true);
                            }

                            paramMap.put(name, pri);
                        }
                    }
                }
                map.put(code, paramMap);

            }

        }

    }

    public static void main(String[] args) throws ParserConfigurationException, SAXException,
                                          IOException {

        try {
            Map<String, Map<String, ParamRuleInfo>> map = loadParamRule();
            Set<String> set = map.keySet();
            Iterator<String> it = set.iterator();
            while (it.hasNext()) {
                String code = it.next();
                Map<String, ParamRuleInfo> mm = map.get(code);
                Iterator<String> mit = mm.keySet().iterator();
                System.out.println(code);
                while (mit.hasNext()) {
                    System.out.println(mit.next());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
