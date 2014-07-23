package com.foodoon.demo.web.form;

import java.util.Date;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.foodoon.demo.dao.domain.OrgDO;
import com.sun.istack.internal.NotNull;

public class OrgForm {
         private Integer id;

         private String orgName;

         private Long code;

         private Date gmtCreate;

         private Date gmtModify;

       public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getOrgName() {
        return orgName;
    }
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
    public Long getCode() {
        return code;
    }
    public void setCode(Long code) {
        this.code = code;
    }
    public Date getGmtCreate() {
        return gmtCreate;
    }
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
    public Date getGmtModify() {
        return gmtModify;
    }
    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }

    public OrgDO toDO(){
OrgDO orgDO  = new OrgDO();
                orgDO.setOrgName(this.orgName);
                orgDO.setCode(this.code);
                orgDO.setGmtCreate(this.gmtCreate);
                orgDO.setGmtModify(this.gmtModify);
        return orgDO;
    }

}
