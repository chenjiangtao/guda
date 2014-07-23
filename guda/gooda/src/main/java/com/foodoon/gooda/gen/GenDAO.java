package com.foodoon.gooda.gen;

import org.dom4j.Document;
import org.dom4j.Element;

import javax.swing.*;
import java.io.File;

/**
 * Created by foodoon on 2014/6/26.
 */
public class GenDAO {

    private GenContext genContext;

    public GenDAO(GenContext genContext) {
        this.genContext = genContext;
    }


    public void gen() {
        createDAOXML();
        createMapConfig();
    }

    public String createMapConfig() {


        String mapXml = genContext.getMybatisXmlFile();
        File file = new File(mapXml);
        if (!file.exists()) {
            Document document = MybatisConfigXml.createDocument();
            Element mapper = MybatisConfigXml.createRoot(document);
            MybatisConfigXml.appendVal(mapper, genContext.getRelativeDaoMapperXmlFile());
            SpringXml.write(mapXml, document);
            return mapXml;
        }
        Document document = SpringXml.loadXML(mapXml);
        if (!MybatisConfigXml.hasConfig(document, genContext.getRelativeDaoMapperXmlFile())) {
            Element mapper = MybatisConfigXml.getRoot(document);
            MybatisConfigXml.appendVal(mapper, genContext.getRelativeDaoMapperXmlFile());
            SpringXml.write(mapXml, document);
        }
        return mapXml;
    }

    public String createDAOXML() {
        String dalXML = genContext.getDaoXmlFile();
        File file = new File(dalXML);
        if (!file.exists()) {
            Document document = SpringXml.createDocument();
            Element springXmlHeader = SpringXml.createSpringXmlHeader(document);
            Element mapper = SpringXml.createBean(springXmlHeader, genContext.getDoNameLower() + "DOMapper", "org.mybatis.spring.mapper.MapperFactoryBean");
            //com.foodoon.demo.dao.StaffDOMapper
            SpringXml.appendVal(mapper, "mapperInterface", "com.foodoon." + genContext.getAppName() + ".dao." + genContext.getDoName() + "DOMapper");
            SpringXml.appendRef(mapper, "sqlSessionFactory", "sqlSessionFactory");
            SpringXml.write(dalXML, document);
            return dalXML;
        }
        Document document = SpringXml.loadXML(dalXML);
        if (!SpringXml.hasBean(document, genContext.getDoNameLower() + "DOMapper")) {
            Element mapper = SpringXml.createBean(SpringXml.getSpringRoot(document), genContext.getDoNameLower() + "DOMapper", "org.mybatis.spring.mapper.MapperFactoryBean");
            SpringXml.appendVal(mapper, "mapperInterface", "com.foodoon." + genContext.getAppName() + ".dao." + genContext.getDoName() + "DOMapper");
            SpringXml.appendRef(mapper, "sqlSessionFactory", "sqlSessionFactory");
            SpringXml.write(dalXML, document);
        }
        return dalXML;
    }

    public String createDatasourceXML() {

        String dalXML = genContext.getDataSourceXmlFile();
        File file = new File(dalXML);
        if (!file.exists()) {
            Document document = SpringXml.createDocument();
            Element springXmlHeader = SpringXml.createSpringXmlHeader(document);
            Element dataSource = SpringXml.createBean(springXmlHeader, "dataSource", "com.mchange.v2.c3p0.ComboPooledDataSource");
            SpringXml.appendVal(dataSource, "driverClass", "com.mysql.jdbc.Driver");
            SpringXml.appendVal(dataSource, "jdbcUrl", "${jdbc.url}");
            SpringXml.appendVal(dataSource, "username", "${jdbc.username}");
            SpringXml.appendVal(dataSource, "password", "${jdbc.password}");

            Element sqlSessionFactory = SpringXml.createBean(springXmlHeader, "sqlSessionFactory", "org.mybatis.spring.SqlSessionFactoryBean");
            SpringXml.appendRef(sqlSessionFactory, "dataSource", "dataSource");
            SpringXml.appendVal(sqlSessionFactory, "configLocation", "classpath:mybatis/sqlMapConfig.xml");

            SpringXml.write(dalXML, document);
            return dalXML;
        }
        Document document = SpringXml.loadXML(dalXML);
        boolean needUpdate = false;
        if (!SpringXml.hasBean(document, "dataSource")) {
            Element dataSource = SpringXml.createBean(SpringXml.getSpringRoot(document), "dataSource", "com.mchange.v2.c3p0.ComboPooledDataSource");
            SpringXml.appendVal(dataSource, "driverClass", "com.mysql.jdbc.Driver");
            SpringXml.appendVal(dataSource, "jdbcUrl", "${jdbc.url}");
            SpringXml.appendVal(dataSource, "username", "${jdbc.username}");
            SpringXml.appendVal(dataSource, "password", "${jdbc.password}");
            needUpdate = true;
        }
        if (!SpringXml.hasBean(document, "sqlSessionFactory")) {
            Element sqlSessionFactory = SpringXml.createBean(SpringXml.getSpringRoot(document), "sqlSessionFactory", "org.mybatis.spring.SqlSessionFactoryBean");
            SpringXml.appendRef(sqlSessionFactory, "dataSource", "dataSource");
            SpringXml.appendVal(sqlSessionFactory, "configLocation", "classpath:mybatis/sqlMapConfig.xml");
            needUpdate = true;
        }
        if (needUpdate) {
            SpringXml.write(dalXML, document);
        }
        return dalXML;
    }


}
