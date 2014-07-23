package com.foodoon.tools.mybatis;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.CommentGeneratorConfiguration;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.JDBCConnectionConfiguration;
import org.mybatis.generator.config.JavaClientGeneratorConfiguration;
import org.mybatis.generator.config.JavaModelGeneratorConfiguration;
import org.mybatis.generator.config.JavaTypeResolverConfiguration;
import org.mybatis.generator.config.ModelType;
import org.mybatis.generator.config.PluginConfiguration;
import org.mybatis.generator.config.SqlMapGeneratorConfiguration;
import org.mybatis.generator.config.TableConfiguration;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2014/5/11.
 */
public abstract class MybatisTools {

	public String javaModelTargetProject;

	public String javaModelTargetPackage;

	public String sqlTargetProject;

	public String sqlTargetPackage;

	public String javaDaoTargetPackage;

	public String javaDaoTargetProject;

	public String domainObjectName;

	public String tableName;

	public String schema;

	public String driverClass;

	public String connectionURL;

	public String userId;

	public String password;

	public String classpathLocation;

	public JavaModelGeneratorConfiguration javaModelGeneratorConfiguration;

	public Context context;

	public JDBCConnectionConfiguration jdbcConnectionConfiguration;

	public JavaTypeResolverConfiguration javaTypeResolverConfiguration;

	public SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration;

	public JavaClientGeneratorConfiguration javaClientGeneratorConfiguration;

	public TableConfiguration tableConfiguration;

	protected void gen() throws InvalidConfigurationException, IOException,
			XMLParserException, SQLException, InterruptedException {

		if (context == null) {
			context = new Context(ModelType.FLAT);
			context.setId("DB2Tables");
			context.setTargetRuntime("MyBatis3");
		}

		if (jdbcConnectionConfiguration == null) {
			jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
			jdbcConnectionConfiguration.setDriverClass(driverClass);
			jdbcConnectionConfiguration.setConnectionURL(connectionURL);
			jdbcConnectionConfiguration.setUserId(userId);
			jdbcConnectionConfiguration.setPassword(password);
		}
		context.setJdbcConnectionConfiguration(jdbcConnectionConfiguration);

		if (javaTypeResolverConfiguration == null) {
			javaTypeResolverConfiguration = new JavaTypeResolverConfiguration();
			javaTypeResolverConfiguration.addProperty("forceBigDecimals",
					"false");
			context.setJavaTypeResolverConfiguration(javaTypeResolverConfiguration);
		}

		if (javaModelGeneratorConfiguration == null) {
			javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
			javaModelGeneratorConfiguration
					.setTargetPackage(javaModelTargetPackage);
			javaModelGeneratorConfiguration
					.setTargetProject(javaModelTargetProject);
			javaModelGeneratorConfiguration.addProperty("enableSubPackages",
					"true");
			javaModelGeneratorConfiguration.addProperty("trimStrings", "true");
		}
		context.setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration);

		if (sqlMapGeneratorConfiguration == null) {
			sqlMapGeneratorConfiguration = new SqlMapGeneratorConfiguration();
			sqlMapGeneratorConfiguration.setTargetPackage(sqlTargetPackage);
			sqlMapGeneratorConfiguration.setTargetProject(sqlTargetProject);
			sqlMapGeneratorConfiguration.addProperty("enableSubPackages",
					"true");
		}
		context.setSqlMapGeneratorConfiguration(sqlMapGeneratorConfiguration);

		if (javaClientGeneratorConfiguration == null) {
			javaClientGeneratorConfiguration = new JavaClientGeneratorConfiguration();
			javaClientGeneratorConfiguration.setConfigurationType("XMLMAPPER");
			javaClientGeneratorConfiguration
					.setTargetPackage(javaDaoTargetPackage);
			javaClientGeneratorConfiguration
					.setTargetProject(javaDaoTargetProject);
			javaClientGeneratorConfiguration.addProperty("enableSubPackages",
					"true");
		}
		context.setJavaClientGeneratorConfiguration(javaClientGeneratorConfiguration);

		if (tableConfiguration == null) {
			tableConfiguration = new TableConfiguration(context);
			tableConfiguration.setSchema(schema);
			tableConfiguration.setTableName(tableName);
			tableConfiguration.setDomainObjectName(domainObjectName);
			//tableConfiguration.setCountByExampleStatementEnabled(false);
			tableConfiguration.setDeleteByExampleStatementEnabled(false);

			tableConfiguration.setUpdateByExampleStatementEnabled(false);
			
			//tableConfiguration.setSelectByExampleStatementEnabled(false);

		}
		context.addTableConfiguration(tableConfiguration);
		
		CommentGeneratorConfiguration commentGeneratorConfiguration  = new CommentGeneratorConfiguration ();
		commentGeneratorConfiguration.addProperty("suppressAllComments", "true");
		context.setCommentGeneratorConfiguration(commentGeneratorConfiguration);
		
		PluginConfiguration pluginConfiguration  = new PluginConfiguration ();
		pluginConfiguration.setConfigurationType("org.mybatis.generator.plugins.RenameExampleClassPlugin");
		pluginConfiguration.addProperty("searchString", "Example$");
		pluginConfiguration.addProperty("replaceString", "Criteria");
		
		context.addPluginConfiguration(pluginConfiguration);
		
		PluginConfiguration pluginConfiguration2  = new PluginConfiguration ();
		pluginConfiguration2.setConfigurationType("com.foodoon.tools.mybatis.PaginationPlugin");
		context.addPluginConfiguration(pluginConfiguration2);
		
		List<String> warnings = new ArrayList<String>();
		final boolean overwrite = true;
		Configuration configuration = new Configuration();
		configuration.addContext(context);
		configuration.addClasspathEntry(classpathLocation);
		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		MyBatisGenerator mybatisGenerator = new MyBatisGenerator(configuration,
				callback, warnings);
		mybatisGenerator.generate(null);

	}

	public void setJavaModelTargetProject(String javaModelTargetProject) {
		this.javaModelTargetProject = javaModelTargetProject;
	}

	public void setJavaModelTargetPackage(String javaModelTargetPackage) {
		this.javaModelTargetPackage = javaModelTargetPackage;
	}

	public void setSqlTargetProject(String sqlTargetProject) {
		this.sqlTargetProject = sqlTargetProject;
	}

	public void setSqlTargetPackage(String sqlTargetPackage) {
		this.sqlTargetPackage = sqlTargetPackage;
	}

	public void setJavaDaoTargetPackage(String javaDaoTargetPackage) {
		this.javaDaoTargetPackage = javaDaoTargetPackage;
	}

	public void setJavaDaoTargetProject(String javaDaoTargetProject) {
		this.javaDaoTargetProject = javaDaoTargetProject;
	}

	public void setDomainObjectName(String domainObjectName) {
		this.domainObjectName = domainObjectName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public void setConnectionURL(String connectionURL) {
		this.connectionURL = connectionURL;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setClasspathLocation(String classpathLocation) {
		this.classpathLocation = classpathLocation;
	}

	public void setJavaModelGeneratorConfiguration(
			JavaModelGeneratorConfiguration javaModelGeneratorConfiguration) {
		this.javaModelGeneratorConfiguration = javaModelGeneratorConfiguration;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public void setJdbcConnectionConfiguration(
			JDBCConnectionConfiguration jdbcConnectionConfiguration) {
		this.jdbcConnectionConfiguration = jdbcConnectionConfiguration;
	}

	public void setJavaTypeResolverConfiguration(
			JavaTypeResolverConfiguration javaTypeResolverConfiguration) {
		this.javaTypeResolverConfiguration = javaTypeResolverConfiguration;
	}

	public void setSqlMapGeneratorConfiguration(
			SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration) {
		this.sqlMapGeneratorConfiguration = sqlMapGeneratorConfiguration;
	}

	public void setJavaClientGeneratorConfiguration(
			JavaClientGeneratorConfiguration javaClientGeneratorConfiguration) {
		this.javaClientGeneratorConfiguration = javaClientGeneratorConfiguration;
	}

	public void setTableConfiguration(TableConfiguration tableConfiguration) {
		this.tableConfiguration = tableConfiguration;
	}



}
