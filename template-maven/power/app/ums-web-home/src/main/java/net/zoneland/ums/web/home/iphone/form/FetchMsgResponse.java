/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.web.home.iphone.form;

import java.util.Collections;
import java.util.List;

import net.zoneland.ums.common.dal.bo.UmsMsgOutIphone;

/**
 * 获取消息返回数据
 * 
 * @author yangjuanying
 * @version $Id: FetchMsgResponse.java, v 0.1 2012-9-26 下午4:02:36 yangjuanying Exp $
 */
public class FetchMsgResponse {

    private String                code;                             // 返回“1”表示消息查询成功，其他查询失败

    private String                msg;                              // 错误等原因描述

    private int                   total;                            // 总记录数

    private int                   page;                             // 当前页码

    private int                   pageSize;                         // 每页的记录数

    private List<UmsMsgOutIphone> results = Collections.emptyList();

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<UmsMsgOutIphone> getResults() {
        return results;
    }

    public void setResults(List<UmsMsgOutIphone> results) {
        this.results = results;
    }

}
