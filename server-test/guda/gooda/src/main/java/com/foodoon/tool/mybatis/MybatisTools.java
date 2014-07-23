package com.foodoon.tool.mybatis;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2014/5/11.
 */
public class MybatisTools {

    public static void gen(String fileName) throws InvalidConfigurationException, IOException, XMLParserException, SQLException, InterruptedException {
        List<String> warnings = new ArrayList<String>();
        final boolean overwrite = true;
        ClassLoader classLoader = MybatisTools.class.getClassLoader();
        InputStream resourceAsStream = classLoader.getResourceAsStream(fileName);
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration configuration = cp.parseConfiguration(resourceAsStream);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator mybatisGenerator = new MyBatisGenerator(configuration, callback, warnings);
        mybatisGenerator.generate(null);
    }
}
