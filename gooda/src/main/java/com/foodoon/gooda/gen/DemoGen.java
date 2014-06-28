package com.foodoon.gooda.gen;

import java.io.File;

public class DemoGen extends BaseDaoGen{
	
	private String jdbcPath = "e:\\repo\\mysql\\mysql-connector-java\\5.1.9\\mysql-connector-java-5.1.9.jar";
	
	private String jdbUrl = "jdbc:mysql://127.0.0.1:3306/demo?useUnicode=true&characterEncoding=utf8";
	
	private String jdbcUsername = "root";
	
	private String jdbcPassword = "";
	
	public  void genDAO(String tableName,String appName){
        if(tableName == null){
        	throw new RuntimeException("table name can not null");
        }

		DemoGen demoGen = new DemoGen();
		String domainName = tableName.substring(0, 1).toUpperCase() + tableName.substring(1)+"DO";
		demoGen.setDomainObjectName(domainName);
		demoGen.setTableName(tableName);
		demoGen.setJavaDaoTargetPackage("com.foodoon."+appName+".dao");
        String daoFile = GenConstants.appDir + File.separator + GenContext.getDaoDir() + File.separator + GenConstants.javaDir ;
		demoGen.setJavaDaoTargetProject(daoFile);
		
		demoGen.setJavaModelTargetPackage("com.foodoon."+appName+".dao.domain");
		demoGen.setJavaModelTargetProject(daoFile);
		
		demoGen.setSqlTargetPackage("mybatis");
		String resourceFile = GenConstants.appDir + File.separator + GenContext.getDaoDir() + File.separator + GenConstants.resourceDir ;
		demoGen.setSqlTargetProject(resourceFile);

      	
		try {
			demoGen.gen();
		} catch (Exception e) {
			e.printStackTrace();
		}
        

    }
	
	
    public String getJdbcPath() {
		return jdbcPath;
	}


	public void setJdbcPath(String jdbcPath) {
		this.jdbcPath = jdbcPath;
	}


	public String getJdbUrl() {
		return jdbUrl;
	}


	public void setJdbUrl(String jdbUrl) {
		this.jdbUrl = jdbUrl;
	}


	public String getJdbcUsername() {
		return jdbcUsername;
	}


	public void setJdbcUsername(String jdbcUsername) {
		this.jdbcUsername = jdbcUsername;
	}


	public String getJdbcPassword() {
		return jdbcPassword;
	}


	public void setJdbcPassword(String jdbcPassword) {
		this.jdbcPassword = jdbcPassword;
	}


    public static void genAll(String tableName,String appName){
        String domainName = tableName.substring(0, 1).toUpperCase() + tableName.substring(1)+"DO";
        try {
            System.out.println("start gen dao xml===================");
            GenContext genContext = new GenContext("com.foodoon."+appName+".dao.domain."+domainName,appName);
            GenDAO genDAO  = new GenDAO(genContext);
            genDAO.gen();
            System.out.println("start gen dao xml finish===================");

            System.out.println("start gen biz  start===================");
            GenBiz genBiz = new GenBiz(genContext);
            genBiz.gen();
            System.out.println("start gen biz  finish===================");

            System.out.println("start gen action  start===================");
            GenAction genAction = new GenAction(genContext);
            genAction.gen();
            System.out.println("start gen action  finish===================");

            System.out.println("start gen vm  start===================");
            GenVm genVM = new GenVm(genContext);
            genVM.gen();
            System.out.println("start gen vm  finish===================");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public static void genDaoXML(String tableName,String appName){
    	String domainName = tableName.substring(0, 1).toUpperCase() + tableName.substring(1)+"DO";
    	try {

            GenContext genContext = new GenContext("com.foodoon."+appName+".dao.domain."+domainName,appName);
            GenDAO genDAO  = new GenDAO(genContext);
            genDAO.gen();

        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public static void genBiz(String tableName,String appName){
		String domainName = tableName.substring(0, 1).toUpperCase() + tableName.substring(1)+"DO";
    	try {
            GenContext genContext = new GenContext("com.foodoon."+appName+".dao.domain."+domainName,appName);

            GenBiz genBiz = new GenBiz(genContext);
            genBiz.gen();

         
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
    public static void genAction(String tableName,String appName){
    	String domainName = tableName.substring(0, 1).toUpperCase() + tableName.substring(1)+"DO";
    	try {
            GenContext genContext = new GenContext("com.foodoon."+appName+".dao.domain."+domainName,appName);

            GenAction genAction = new GenAction(genContext);
            genAction.gen();

            GenVm genVM = new GenVm(genContext);
            genVM.gen();
        } catch (Exception e) {
            e.printStackTrace();
        }
		
	}

}
