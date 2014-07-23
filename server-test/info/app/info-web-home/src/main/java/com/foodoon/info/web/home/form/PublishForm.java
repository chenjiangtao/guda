package com.foodoon.info.web.home.form;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

public class PublishForm {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NotBlank()
    private String classifyId;

    @NotBlank
    private String subClassifyId;

    @NotBlank
    private String provinceId;

    @NotBlank
    private String cityId;

    @NotBlank(message = "价格不能为空")
    private String price;

    @NotBlank(message = "标题不能为空")
    private String title;

    @NotBlank(message = "描述不能为空")
    private String content;

    @NotBlank(message = "联系人不能为空")
    private String contactUser;

    @NotBlank(message = "联系电话不能为空")
    @Pattern(regexp = "^1[0-9]{10}$", message = "手机号必须是11位数字")
    private String contactInfo;

    public String getSubClassifyId() {
        return subClassifyId;
    }

    public void setSubClassifyId(String subClassifyId) {
        this.subClassifyId = subClassifyId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContactUser() {
        return contactUser;
    }

    public void setContactUser(String contactUser) {
        this.contactUser = contactUser;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(String classifyId) {
        this.classifyId = classifyId;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

}
