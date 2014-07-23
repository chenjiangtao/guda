package com.foodoon.news.template;


public class CmsModuleGenerator {
	private static String packName = "com.foodoon.news.template";
	private static String fileName = "jeecms.properties";

	public static void main(String[] args) {
		new ModuleGenerator(packName, fileName).generate();
	}
}
