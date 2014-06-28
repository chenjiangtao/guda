/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.foodoon.mvc.runtime.core.xml;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *  
 * @author gag
 * @version $Id: XmlPropertiesReplace.java, v 0.1 2012-4-26 下午9:52:25 gag Exp $
 */
public class XmlResovle {

    public static final String arributeName  = "name";

    public static final String arributeValue = "defaultValue";

    /**
     * 
     * @param xmlUri
     * @return
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public Document parseXml(String xmlUri) throws ParserConfigurationException, SAXException,
                                           IOException {

        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        return docBuilder.parse(xmlUri);

    }

    /**
     * 
     * @param xmlFile
     * @return
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public Document parseXml(File xmlFile) throws ParserConfigurationException, SAXException,
                                          IOException {

        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        return docBuilder.parse(xmlFile);

    }

    /**
     * 
     * @param nodeList
     * @param props
     */
    public void filterNode(NodeList nodeList, Properties props) {
        if (nodeList == null) {
            return;
        }
        for (int j = 0, lenJ = nodeList.getLength(); j < lenJ; ++j) {
            Node node = nodeList.item(j);
            if (node.hasAttributes()) {
                NamedNodeMap namedNodeMap = node.getAttributes();
                for (int i = 0, len = namedNodeMap.getLength(); i < len; ++i) {
                    Node attrNode = namedNodeMap.item(i);
                    //attrNode.getNodeName();
                    String resolveVal = PropertiesReplace.resolveStringVal(attrNode.getNodeValue(),
                        props);
                    if (StringUtils.isNotBlank(resolveVal)) {
                        attrNode.setNodeValue(resolveVal);
                    }
                }

            }
            filterNode(node.getChildNodes(), props);
        }

    }

    /**
     * 
     * @param srcFile
     * @param destFile
     * @param props
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     * @throws TransformerException
     */
    public void resolveXmlToXml(String srcFile, String destFile, Properties props)
                                                                                  throws ParserConfigurationException,
                                                                                  SAXException,
                                                                                  IOException,
                                                                                  TransformerException {
        if (srcFile != null && srcFile.endsWith(".xml")) {
            Document doc = parseXml(srcFile);
            NodeList nodeList = doc.getChildNodes();
            filterNode(nodeList, props);
            DOMSource domSource = new DOMSource(doc);
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            Result result = new StreamResult(new File(destFile));
            transformer.transform(domSource, result);
        } else {
            throw new RuntimeException("resolve xml error! src file is null or not end with xml");

        }
    }

    public void fillNodeToProperties(NodeList nodeList, Properties props) {
        if (nodeList == null) {
            return;
        }
        for (int j = 0, lenJ = nodeList.getLength(); j < lenJ; ++j) {
            Node node = nodeList.item(j);
            if (node.hasAttributes()) {
                NamedNodeMap namedNodeMap = node.getAttributes();
                for (int i = 0, len = namedNodeMap.getLength(); i < len; ++i) {
                    Node attrNode = namedNodeMap.getNamedItem(arributeName);
                    Node attrValueNode = namedNodeMap.getNamedItem(arributeValue);
                    if (attrNode != null && attrValueNode != null) {
                        String key = attrNode.getNodeValue();
                        String val = attrValueNode.getNodeValue();
                        if (key != null) {
                            if (StringUtils.isBlank(val)) {
                                val = "";
                            }
                            props.put(key, val);
                        }
                    }
                }

            }
            fillNodeToProperties(node.getChildNodes(), props);
        }

    }

    public void resolveXmlToProperties(String srcFile, Properties props)
                                                                        throws ParserConfigurationException,
                                                                        SAXException, IOException,
                                                                        TransformerException {
        Document doc = parseXml(srcFile);
        NodeList nodeList = doc.getChildNodes();
        fillNodeToProperties(nodeList, props);

    }

    public static void main(String[] args) throws ParserConfigurationException, SAXException,
                                          IOException, TransformerException {
        XmlResovle xml = new XmlResovle();
        Properties props = new Properties();
        props.put("app.root", "/home/admin");
        props.put("app.output", "/home/admin/logs");
        xml.resolveXmlToXml("D:\\work-dir\\test\\template\\assembly\\config\\auto-config.xml",
            "D:\\work-dir\\test\\template\\assembly\\config\\auto-config.xml", props);

    }
}
