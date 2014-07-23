package com.foodoon.demo.test;

import com.foodoon.gooda.gen.*;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;

import java.io.IOException;
import java.sql.SQLException;

public class StaffGen extends BaseDaoGen{
	
	public static void main(String[] args){
		System.out.println(111);
		
		StaffGen staffGen = new StaffGen();
		staffGen.setDomainObjectName("OrgDO");
		staffGen.setTableName("org");
		staffGen.setJavaDaoTargetPackage("com.foodoon.demo.dao");
		staffGen.setJavaDaoTargetProject("D:\\taocode\\server-test\\demo\\app\\demo-dao\\src\\main\\java");
		
		staffGen.setJavaModelTargetPackage("com.foodoon.demo.dao.domain");
		staffGen.setJavaModelTargetProject("D:\\taocode\\server-test\\demo\\app\\demo-dao\\src\\main\\java");
		
		staffGen.setSqlTargetPackage("mybatis");
		staffGen.setSqlTargetProject("D:\\taocode\\server-test\\demo\\app\\demo-dao\\src\\main\\resources");
	       	
		try {
			//staffGen.gen();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
            GenContext genContext = new GenContext("com.foodoon.demo.dao.domain.OrgDO","demo");
            GenDAO genDAO  = new GenDAO(genContext);
            genDAO.gen();

            GenBiz genBiz = new GenBiz(genContext);
            genBiz.gen();

            GenAction genAction = new GenAction(genContext);
            genAction.gen();

            GenVm genVM = new GenVm(genContext);

            genVM.gen();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
