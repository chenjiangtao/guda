/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.foodoon.monitor.agent.config;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.ProcessingInstruction;
import org.dom4j.VisitorSupport;
import org.dom4j.io.SAXReader;

import com.foodoon.monitor.agent.enums.ResultTypeEnum;
import com.foodoon.monitor.agent.start.App;

/**
 * 
 * @author gang
 * @version $Id: TaskParse.java, v 0.1 2013-4-21 上午10:29:02 gang Exp $
 */
public class TaskParser {

    public static void main(String[] args) {
        parseTask();
    }

    public static void parseTask() {
        try {
            SAXReader reader = new SAXReader();

            File f = new File(App.root_dir + File.separator + "config.xml");
            if (!f.exists()) {
                throw new RuntimeException("无法在当前目录找到config.xml");
            }
            Document doc = reader.read(new FileInputStream(f));
            Element root = doc.getRootElement();
            if (root.element("sysInfo") != null) {
                Iterator<Element> iterator = root.element("sysInfo").elementIterator();

                TaskParser tp = new TaskParser();
                while (iterator.hasNext()) {

                    Element sys = iterator.next();
                    SysInfoVisitor sysInfoVisitor = tp.new SysInfoVisitor();
                    sys.accept(sysInfoVisitor);
                    TaskFactory.addSysInfo(sysInfoVisitor.sysInfo);
                }
            }
            if (root.element("dbInfo") != null) {
                TaskParser tp = new TaskParser();
                Iterator<Element> iteratorDB = root.element("dbInfo").elementIterator();
                while (iteratorDB.hasNext()) {
                    DbInfo dbInfo = new DbInfo();
                    Element sys = iteratorDB.next();
                    dbInfo.setDriverClass(sys.attribute("driverClass").getValue());
                    List<Node> url = sys.selectNodes("sqlUrl");
                    List<Node> userName = sys.selectNodes("sqlUserName");
                    List<Node> password = sys.selectNodes("sqlPassword");
                    dbInfo.setUrl(url.get(0).getText());
                    dbInfo.setPassword(password.get(0).getText());
                    dbInfo.setUserName(userName.get(0).getText());
                    List<Node> sqlNode = sys.selectNodes("sql");
                    Iterator<Node> nodes = sqlNode.iterator();
                    while (nodes.hasNext()) {
                        Node n = nodes.next();
                        SqlNodeVisitor v = tp.new SqlNodeVisitor();
                        n.accept(v);
                        dbInfo.addSqlNode(v.sqlNode);
                    }

                    TaskFactory.addDbInfo(dbInfo);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void readNode() {

    }

    class SysInfoVisitor extends VisitorSupport {

        private SysInfo sysInfo = new SysInfo(); ;

        public void visit(Attribute node) {

            if ("key".equals(node.getName())) {
                sysInfo.setKey(node.getValue());
            } else if ("comments".equals(node.getName())) {
                sysInfo.setComments(node.getValue());
            } else if ("monitor".equals(node.getName())) {
                if ("true".equalsIgnoreCase(node.getValue())) {
                    sysInfo.setMonitor(true);
                }
            } else if ("interval".equals(node.getName())) {
                sysInfo.setInterval(node.getValue());
            } else if ("order".equals(node.getName())) {

                sysInfo.setOrder(Integer.parseInt(node.getValue()));

            }

        }

        public void visit(Element node) {

        }

        @Override
        public void visit(ProcessingInstruction node) {

        }
    }

    class SqlNodeVisitor extends VisitorSupport {

        private SqlNode sqlNode = new SqlNode();

        public void visit(Attribute node) {
            if ("interval".equalsIgnoreCase(node.getName())) {
                sqlNode.setInterval(node.getValue());
            } else if ("key".equalsIgnoreCase(node.getName())) {
                sqlNode.setKey(node.getValue());
            } else if ("resultType".equalsIgnoreCase(node.getName())) {
                ResultTypeEnum e = ResultTypeEnum.getByValue(node.getValue());
                if (e == null) {
                    throw new RuntimeException("can not find ResultTypeEnum,value:["
                                               + node.getValue() + "]");
                }
                sqlNode.setResultType(e);
            } else if ("suffixColumn".equalsIgnoreCase(node.getName())) {
                sqlNode.setSuffixColumn(node.getValue().toUpperCase());
            } else if ("valueColumn".equalsIgnoreCase(node.getName())) {
                sqlNode.setValueColumn(node.getValue().toUpperCase());
            } else if ("order".equals(node.getName())) {
                sqlNode.setOrder(Integer.parseInt(node.getValue()));

            }

        }

        public void visit(Element node) {
            if ("query".equals(node.getName())) {
                sqlNode.setSql(node.getText());
            } else if ("map".equals(node.getName())) {
                String key = node.attribute("column").getValue();
                String val = node.attribute("showName").getValue();
                sqlNode.put(key.toUpperCase(), val);
            }
        }

    }

}
