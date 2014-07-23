package com.foodoon.demo.dao.test;

import java.io.IOException;
import java.sql.SQLException;

import com.foodoon.demo.dao.domain.OrgDO;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;

public class StaffGen extends BaseDaoGen{
	
	public static void main(String[] args){
		System.out.println(111);
		
		StaffGen staffGen = new StaffGen();
		staffGen.setDomainObjectName("StaffDO");
		staffGen.setTableName("staff");
		staffGen.setJavaDaoTargetPackage("com.foodoon.demo.dao");
		staffGen.setJavaDaoTargetProject("D:\\taocode\\server-test\\demo\\app\\demo-dao\\src\\main\\java");
		
		staffGen.setJavaModelTargetPackage("com.foodoon.demo.dao.domain");
		staffGen.setJavaModelTargetProject("D:\\taocode\\server-test\\demo\\app\\demo-dao\\src\\main\\java");
		
		staffGen.setSqlTargetPackage("mybatis");
		staffGen.setSqlTargetProject("D:\\taocode\\server-test\\demo\\app\\demo-dao\\src\\main\\resources");
	       	
		try {
			staffGen.gen();
		} catch (InvalidConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMLParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

}
