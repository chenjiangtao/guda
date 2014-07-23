/*
 * Copyright 2013 foodoon.com All right reserved. This software is the
 * confidential and proprietary information of foodoon.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with foodoon.com.
 */
package com.foodoon.info.web.home.form;

/**
 * 类PageForm.java的实现描述：TODO 类实现描述
 * 
 * @author zhigang.ge 2013年10月18日 上午9:21:01
 */
public class PageForm {

    private String pageId;

    private int    page = 1;

    private int    rows = 20;

    public void calPage() {
        if (pageId == null) {
            return;
        }
        try {
            page = Integer.parseInt(pageId);
            if (page < 1) {
                page = 1;
            }
        } catch (Exception e) {

        }
    }

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

}
