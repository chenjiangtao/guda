package com.foodoon.well.web.form;

import java.util.Date;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.foodoon.well.dao.domain.StaffDO;
import com.sun.istack.internal.NotNull;

public class StaffForm {
         private Integer id;

         private String name;

         private String password;

         private Date gmtCreate;

         private Long amount;

       public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Date getGmtCreate() {
        return gmtCreate;
    }
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
    public Long getAmount() {
        return amount;
    }
    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public StaffDO toDO(){
StaffDO staffDO  = new StaffDO();
                staffDO.setName(this.name);
                staffDO.setPassword(this.password);
                staffDO.setGmtCreate(this.gmtCreate);
                staffDO.setAmount(this.amount);
        return staffDO;
    }

}
