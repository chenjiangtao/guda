package com.foodoon.gooda.gen;



import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by foodoon on 2014/6/28.
 */
public class GenContext {

    private String appName;

    private String doName;

    private String doNameLower;

    private String bizFile;

    private String bizImplFile;

    private String bizXmlFile;

    private String daoFile;

    private String controllerFile;

    private String daoXmlFile;

    private String dataSourceXmlFile;

    private String mybatisXmlFile;

    private String relativeDaoMapperXmlFile;

    private String vmPath;

    private String formFile;

    private String editFormFile;

    private List<DOField> doFieldList = new ArrayList<DOField>();

    public GenContext(String  clazz,String appName) throws ClassNotFoundException {

            this(Class.forName(clazz), appName);

    }

    public GenContext(Class clazz,String appName){
        this.appName = appName;
        doName = clazz.getSimpleName();
        if (!doName.endsWith("DO")) {
            throw new RuntimeException("do not end with DO");
        }
        doName = doName.substring(0, doName.length() - 2);
        doNameLower = doName.substring(0, 1).toLowerCase() + doName.substring(1);
        bizFile = GenConstants.appDir + File.separator + getBizDir() + File.separator + GenConstants.javaDir + File.separator + GenConstants.parentPackage
                +File.separator + appName + File.separator + "biz" + File.separator + doName +"Biz.java";
        bizImplFile = GenConstants.appDir + File.separator + getBizDir() + File.separator + GenConstants.javaDir + File.separator + GenConstants.parentPackage
                +File.separator + appName + File.separator + "biz" + File.separator + "impl"+File.separator+ doName +"BizImpl.java";

        bizXmlFile =  GenConstants.appDir + File.separator + getBizDir() + File.separator + GenConstants.resourceDir + File.separator + "spring" +File.separator + GenConstants.bizXML;

        daoFile = GenConstants.appDir + File.separator + getDaoDir() + File.separator + GenConstants.javaDir + File.separator + GenConstants.parentPackage
                +File.separator + appName + File.separator + "dao" + File.separator + doName +"DOMapper";
        dataSourceXmlFile = GenConstants.appDir + File.separator + getDaoDir() + File.separator + GenConstants.resourceDir + File.separator + "spring" +File.separator+ GenConstants.dataSourceXML;

        daoXmlFile =  GenConstants.appDir + File.separator + getDaoDir() + File.separator + GenConstants.resourceDir + File.separator + "spring" +File.separator+ GenConstants.daoXML;
        mybatisXmlFile  = GenConstants.appDir + File.separator + getDaoDir() + File.separator + GenConstants.resourceDir + File.separator + "mybatis" +File.separator+ GenConstants.mybatisConfigXML;

        relativeDaoMapperXmlFile = "mybatis/"+doName+"DOMapper.xml";

        vmPath = GenConstants.baseDir + File.separator + "htdocs" + File.separator + "home" +File.separator + doNameLower;
        controllerFile = GenConstants.appDir + File.separator + getWebDir() + File.separator + GenConstants.javaDir + File.separator + GenConstants.parentPackage
                +File.separator + appName + File.separator + "web" + File.separator  + "action" + File.separator  + doName+"Action.java";

        formFile = GenConstants.appDir + File.separator + getWebDir() + File.separator + GenConstants.javaDir + File.separator + GenConstants.parentPackage
                +File.separator + appName + File.separator + "web" + File.separator  + "form" + File.separator  + doName+ "Form.java";
        editFormFile = GenConstants.appDir + File.separator + getWebDir() + File.separator + GenConstants.javaDir + File.separator + GenConstants.parentPackage
                +File.separator + appName + File.separator + "web" + File.separator  + "form" + File.separator  + doName+ "EditForm.java";

        System.out.println("appName:"+appName);
        System.out.println("doNameLower:"+doNameLower);
        System.out.println("bizFile:"+bizFile);
        System.out.println("bizImplFile:"+bizImplFile);
        System.out.println("bizXmlFile:"+bizXmlFile);
        System.out.println("daoFile:"+daoFile);
        System.out.println("dataSourceXmlFile:"+dataSourceXmlFile);
        System.out.println("daoXmlFile:"+daoXmlFile);
        System.out.println("mybatisXmlFile:"+mybatisXmlFile);
        System.out.println("relativeDaoMapperXmlFile:"+relativeDaoMapperXmlFile);

        System.out.println("controllerFile:"+controllerFile);
        System.out.println("vmpath:"+vmPath);
        System.out.println("formFile:"+formFile);
        System.out.println("editFormFile:"+editFormFile);

        Field[] fieldArray=clazz.getDeclaredFields();

        for(Field field:fieldArray){
            if("serialVersionUID".equals(field.getName())){
                continue;
            }
            DOField dofield = new DOField();
            String name = field.getName();
            Class<?> type = field.getType();
            dofield.name = name;
            dofield.type = type;
            dofield.typeName = type.getSimpleName();
            dofield.upperName = name.substring(0, 1).toUpperCase() + name.substring(1);
            GenField annotation = field.getAnnotation(GenField.class);
            if(annotation !=null){
                dofield.cnName = annotation.cn();
                dofield.inSearchForm = annotation.inSearchForm();
                dofield.order = annotation.order();
                dofield.canNull = annotation.canNull();
            }else{
                dofield.cnName = name;
            }
            doFieldList.add(dofield);

        }
        Collections.sort(doFieldList,new Comparator<DOField>() {
            public int compare(DOField o1, DOField o2) {
                if(o1 == null || o2 == null)
                return 0;
                if(o1.order>o2.order){
                    return 1;
                }
                return -1;
            }
        });
        for(DOField doField:doFieldList){
            System.out.println(doField);
        }
    }

    public String getFormFile() {
        return formFile;
    }

    public void setFormFile(String formFile) {
        this.formFile = formFile;
    }

    public String getEditFormFile() {
        return editFormFile;
    }

    public void setEditFormFile(String editFormFile) {
        this.editFormFile = editFormFile;
    }

    public String getVmPath() {
        return vmPath;
    }

    public void setVmPath(String vmPath) {
        this.vmPath = vmPath;
    }

    public List<DOField> getDoFieldList() {
        return doFieldList;
    }

    public void setDoFieldList(List<DOField> doFieldList) {
        this.doFieldList = doFieldList;
    }

    public String getControllerFile() {
        return controllerFile;
    }

    public void setControllerFile(String controllerFile) {
        this.controllerFile = controllerFile;
    }

    public String getRelativeDaoMapperXmlFile() {
        return relativeDaoMapperXmlFile;
    }

    public void setRelativeDaoMapperXmlFile(String relativeDaoMapperXmlFile) {
        this.relativeDaoMapperXmlFile = relativeDaoMapperXmlFile;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getDoName() {
        return doName;
    }

    public void setDoName(String doName) {
        this.doName = doName;
    }

    public String getDoNameLower() {
        return doNameLower;
    }

    public void setDoNameLower(String doNameLower) {
        this.doNameLower = doNameLower;
    }

    public String getBizFile() {
        return bizFile;
    }

    public void setBizFile(String bizFile) {
        this.bizFile = bizFile;
    }

    public String getBizImplFile() {
        return bizImplFile;
    }

    public void setBizImplFile(String bizImplFile) {
        this.bizImplFile = bizImplFile;
    }

    public String getBizXmlFile() {
        return bizXmlFile;
    }

    public void setBizXmlFile(String bizXmlFile) {
        this.bizXmlFile = bizXmlFile;
    }

    public String getDaoFile() {
        return daoFile;
    }

    public void setDaoFile(String daoFile) {
        this.daoFile = daoFile;
    }

    public String getDaoXmlFile() {
        return daoXmlFile;
    }

    public void setDaoXmlFile(String daoXmlFile) {
        this.daoXmlFile = daoXmlFile;
    }

    public String getDataSourceXmlFile() {
        return dataSourceXmlFile;
    }

    public void setDataSourceXmlFile(String dataSourceXmlFile) {
        this.dataSourceXmlFile = dataSourceXmlFile;
    }

    public String getMybatisXmlFile() {
        return mybatisXmlFile;
    }

    public void setMybatisXmlFile(String mybatisXmlFile) {
        this.mybatisXmlFile = mybatisXmlFile;
    }


    public String getBizDir() {
        File file = new File(GenConstants.appDir);
        if (!file.exists()) {
            throw new RuntimeException("app dir not exists");
        }
        String[] list = file.list();
        for (String s : list) {
            if (s.endsWith("-biz")) {
                return s;
            }
        }
        return null;
    }

    public String getWebDir() {
        File file = new File(GenConstants.appDir);
        if (!file.exists()) {
            throw new RuntimeException("app dir not exists");
        }
        String[] list = file.list();
        for (String s : list) {
            if (s.endsWith("-web")) {
                return s;
            }
        }
        return null;
    }

    public static String getDaoDir(){
        File file = new File(GenConstants.appDir);
        if (!file.exists()) {
            throw new RuntimeException("app dir not exists");
        }
        String[] list = file.list();
        for (String s : list) {
            if (s.endsWith("-dao")) {
                return s;
            }
        }
        return null;
    }
}
