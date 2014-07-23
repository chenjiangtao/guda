/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.foodoon.info.web.home.vo;

import com.foodoon.info.common.dataobject.DetailWithBLOBs;

/**
 * 
 * @author foodoon
 * @version $Id: DetailVo.java, v 0.1 2013年10月26日 上午8:02:46 foodoon Exp $
 */
public class DetailVo {

    private DetailWithBLOBs detail;
    private String          imgPath;

    public DetailWithBLOBs getDetail() {
        return detail;
    }

    public void setDetail(DetailWithBLOBs detail) {
        this.detail = detail;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

}
