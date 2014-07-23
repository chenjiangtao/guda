/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.util.page;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 分页工具类
 * @author XuFan
 * @version $Id: PageResult.java, v 0.1 Aug 15, 2012 1:44:58 PM XuFan Exp $
 */
public class PageResult<E> {
    private int     pagesize = 10; //每页条数
    private int     total    = 0; //结果总数
    private int     curPage  = 1; //当前页面
    private List<E> results;      //结果集
    private int     totalPage;    //总页数

    private int     firstrecode;
    private int     endrecode;

    public PageResult() {
        this.total = 0;
        this.curPage = 1;
    }

    public PageResult(int total, int curpage) {
        this.total = total;
        this.curPage = curpage;
        countTotalpage();
        changeCurPage();
        countrecodesNum();
    }

    public PageResult(int total, int curpage, int pagesize) {
        this.pagesize = pagesize;
        this.total = total;
        this.curPage = curpage;
        countTotalpage();
        changeCurPage();
        countrecodesNum();
    }

    public PageResult(int total, int curpage, int pagesize, List<E> results) {
        this.results = results;
        this.pagesize = pagesize;
        this.total = total;
        this.curPage = curpage;
        countTotalpage();
        changeCurPage();
        countrecodesNum();
    }

    private void countTotalpage() {
        this.totalPage = 1;
        if ((this.total % this.pagesize) > 0) {
            this.totalPage = this.total / this.pagesize + 1;
        } else {
            this.totalPage = this.total / this.pagesize;
        }
    }

    public void countrecodesNum() {
        this.firstrecode = (this.curPage - 1) * this.pagesize;
        this.endrecode = this.firstrecode + this.pagesize;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public List<E> getResults() {
        return results;
    }

    public void setResults(List<E> results) {
        this.results = results;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getFirstrecode() {
        return firstrecode;
    }

    public void setFirstrecode(int firstrecode) {
        this.firstrecode = firstrecode;
    }

    public int getEndrecode() {
        return endrecode;
    }

    public void setEndrecode(int endrecode) {
        this.endrecode = endrecode;
    }

    private void changeCurPage() {
        while (this.curPage > this.totalPage && this.curPage > 1) {
            this.curPage--;
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public static void main(String[] args) {
        PageResult pr = new PageResult(0, 1);
        System.out.println(pr.getFirstrecode());
    }

}
