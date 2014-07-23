/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.foodoon.info.web.home.vo;

import java.util.List;

import com.foodoon.info.common.dataobject.Classify;
import com.foodoon.info.common.dataobject.SubClassify;

/**
 * 
 * @author foodoon
 * @version $Id: ClassifyVo.java, v 0.1 2013年10月13日 下午3:25:40 foodoon Exp $
 */
public class ClassifyVo {

    private Classify          classify;

    private List<SubClassify> subClassify;

    public ClassifyVo() {

    }

    public ClassifyVo(Classify classify, List<SubClassify> subClassify) {
        this.classify = classify;
        this.subClassify = subClassify;
    }

    public Classify getClassify() {
        return classify;
    }

    public void setClassify(Classify classify) {
        this.classify = classify;
    }

    public List<SubClassify> getSubClassify() {
        return subClassify;
    }

    public void setSubClassify(List<SubClassify> subClassify) {
        this.subClassify = subClassify;
    }

}
