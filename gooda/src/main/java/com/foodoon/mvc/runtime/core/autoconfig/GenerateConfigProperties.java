/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.foodoon.mvc.runtime.core.autoconfig;

import java.io.IOException;
import java.util.Properties;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import com.foodoon.mvc.runtime.core.xml.XmlResovle;

/**
 * 
 * @author gag
 * @version $Id: GenerateConfigProperties.java, v 0.1 2012-4-26 ����5:54:23 gag Exp $
 */
public class GenerateConfigProperties {

    public static final String autoConfig = "auto-config.xml";

    private XmlResovle         xmlResovle = new XmlResovle();

    public void GenerateConfig(String srcFile) throws ParserConfigurationException, SAXException,
                                              IOException, TransformerException {
        Properties props = new Properties();
        xmlResovle.resolveXmlToProperties(srcFile, props);
    }
}
