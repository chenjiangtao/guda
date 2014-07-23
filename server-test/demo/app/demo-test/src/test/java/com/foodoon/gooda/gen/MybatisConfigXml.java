package com.foodoon.gooda.gen;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.Iterator;

/**
 * Created by foodoon on 2014/6/27.
 */
public class MybatisConfigXml {

    public static Document createDocument() {
        Document document = DocumentHelper.createDocument();
        return document;
    }

    public static Element createRoot(Document document) {
        Element configuration = document.addElement("configuration");
        Element mappers = configuration.addElement("mappers");
        return mappers;
    }

    public static Element getRoot(Document document) {
        Element mappers = document.getRootElement().element("mappers");
        return mappers;
    }

    public static Element getMapper(Document document) {
        Element e = document.getRootElement();
        return e.element("mappers");
    }

    public static Element appendVal(Element element, String val) {
        if (element == null) {
            return element;
        }
        Element property = element.addElement("mapper");
        property.addAttribute("resource", val);
        return property;
    }

    public static boolean hasConfig(Document document,String resource) {
        Element e = getMapper(document);
        Iterator iterator = e.elementIterator();
        while(iterator.hasNext()){
            Element next = (Element)iterator.next();
            Attribute resource1 = next.attribute("resource");
            if(resource.equals(resource1.getValue())){
                return true;
            }
        }
        return false;
    }
}
