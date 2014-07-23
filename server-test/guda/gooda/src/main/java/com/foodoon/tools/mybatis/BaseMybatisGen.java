package com.foodoon.tools.mybatis;


import com.foodoon.tools.mybatis.MybatisTools;

public class BaseMybatisGen extends MybatisTools{
	
	public BaseMybatisGen(){
		this.setClasspathLocation();
		this.setConnectionURL();
		this.setDriverClass();
		this.setPassword();
		this.setUserId();
	}


	public void setClasspathLocation() {
		super.setClasspathLocation("e:\\repo\\mysql\\mysql-connector-java\\5.1.9\\mysql-connector-java-5.1.9.jar");
	}


	public void setConnectionURL() {
		super.setConnectionURL("jdbc:mysql://127.0.0.1:3306/demo?useUnicode=true&characterEncoding=utf8");
	}


	public void setContext() {
		super.setContext(context);
	}



	public void setDriverClass() {
		super.setDriverClass("com.mysql.jdbc.Driver");
	}

	

	public void setJavaDaoTargetPackage() {
		super.setJavaDaoTargetPackage(javaDaoTargetPackage);
	}

	public void setJavaDaoTargetProject() {
		super.setJavaDaoTargetProject(javaDaoTargetProject);
	}



	public void setJavaModelTargetPackage() {
		super.setJavaModelTargetPackage(javaModelTargetPackage);
	}

	public void setJavaModelTargetProject() {
		super.setJavaModelTargetProject(javaModelTargetProject);
	}


	public void setPassword() {
		super.setPassword("");
	}

	public void setSchema() {
		super.setSchema("");
	}

	public void setSqlTargetPackage() {
		super.setSqlTargetPackage(sqlTargetPackage);
	}

	public void setSqlTargetProject() {
		super.setSqlTargetProject(sqlTargetProject);
	}


	public void setTableName() {
		super.setTableName(tableName);
	}

	public void setUserId() {
		super.setUserId("root");
	}
	
	



}
