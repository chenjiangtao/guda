package com.foodoon.demo.web.form;

import com.foodoon.demo.dao.domain.OrgDO;


public class OrgEditForm extends OrgForm{

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public OrgDO toDO(){
        OrgDO orgDO  =super.toDO();
        orgDO.setId(this.id);
        return orgDO;
    }

    public void initForm(OrgDO orgDO){
        if(orgDO == null){
           return ;
        }
                this.setId(orgDO.getId());
                this.setOrgName(orgDO.getOrgName());
                this.setCode(orgDO.getCode());
                this.setGmtCreate(orgDO.getGmtCreate());
                this.setGmtModify(orgDO.getGmtModify());
            }

}
