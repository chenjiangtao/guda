package com.foodoon.well.web.form;

import com.foodoon.well.dao.domain.StaffDO;


public class StaffEditForm extends StaffForm{

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public StaffDO toDO(){
        StaffDO staffDO  =super.toDO();
        staffDO.setId(this.id);
        return staffDO;
    }

    public void initForm(StaffDO staffDO){
        if(staffDO == null){
           return ;
        }
                this.setId(staffDO.getId());
                this.setName(staffDO.getName());
                this.setPassword(staffDO.getPassword());
                this.setGmtCreate(staffDO.getGmtCreate());
                this.setAmount(staffDO.getAmount());
            }

}
