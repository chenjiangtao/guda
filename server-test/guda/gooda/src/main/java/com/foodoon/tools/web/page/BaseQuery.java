package com.foodoon.tools.web.page;


import com.foodoon.gooda.helper.BeanHelper;
import java.util.*;


public class BaseQuery {

    private int pageSize = 20;

    private int startRow = 0;

    private int pageNo = 1;

    private int totalCount;

    private int pre;

    private int next;

    private int totalPage;

    private int offset = 4;

    private List<Integer> pages = new ArrayList<Integer>();

    public int getPre() {
        if (pageNo - 1 > 0) {
            pre = pageNo - 1;
        } else {
            pre = 1;
        }
        return pre;
    }

    public void setPre(int pre) {
        this.pre = pre;
    }

    public int getNext() {
        if (pageNo + 1 <= totalPage) {
            next = pageNo + 1;
        } else {
            next = totalPage;
        }
        return next;
    }

    public void setNext(int next) {
        this.next = next;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        if (pageSize <= 1) {
            pageSize = 1;
        }
        startRow = (pageNo - 1) * pageSize;
        this.pageSize = pageSize;
    }

    public List<Integer> getPages() {
        pages.clear();
        int s = (pageNo - offset) > 0 ? (pageNo - offset) : 1;
        int e = (pageNo + offset) > totalPage ? totalPage : (pageNo + offset);
        if(e-s < 3){
            return pages;
        }
        for (int i = s; i <= e; i++) {
            pages.add(i);
        }

        return pages;
    }

    public int getStartRow() {
        return startRow;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        if (pageNo <= 1) {
            pageNo = 1;
        }
        startRow = (pageNo - 1) * pageSize;
        this.pageNo = pageNo;
    }


    public int getMaxPage() {
        if (totalCount <= 1) {
            return 1;
        }
        if (pageSize <= 1) {
            return totalCount;
        }
        return (totalCount + pageSize - 1) / pageSize;
    }


    public int getPageCount() {
        return getMaxPage();
    }

    public boolean hasNextPage() {
        int currentPage = getCurrentPage();
        return currentPage < getMaxPage();
    }

    public boolean hasPrevPage() {
        int currentPage = getCurrentPage();
        return currentPage > 1;
    }

    public int getNextPage() {
        int currentPage = getCurrentPage();
        if (hasNextPage()) {
            return (currentPage + 1);
        }
        return currentPage;
    }

    public int getPrevPage() {
        int currentPage = getCurrentPage();
        if (hasPrevPage()) {
            return currentPage - 1;
        }
        return currentPage;
    }

    public boolean turnNext() {
        if (hasNextPage()) {
            pageNo = getNextPage();
            return true;
        }
        return false;
    }

    public boolean turnPrev() {
        if (hasPrevPage()) {
            pageNo = getPrevPage();
            return true;
        }
        return false;
    }

    public boolean turn(int page) {
        int maxPage = getMaxPage();
        if (page > maxPage) {
            pageNo = maxPage;
            return false;
        }
        if (page < 1) {
            pageNo = 1;
            return false;
        }
        pageNo = page;
        return true;
    }

    public int getCurrentPage() {
        int maxPage = getMaxPage();
        if (pageNo > maxPage) {
            return maxPage;
        }
        if (maxPage < 1) {
            return 1;
        }
        return pageNo;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
        int page = (totalCount + pageSize - 1) / pageSize + 1;
        if (pageNo > page) {
            pageNo = page;
        }
        getTotalPage();
        getPre();
        getNext();
        getPages();
    }

    public int getTotalPage() {
        if (totalCount % pageSize == 0) {
            totalPage = totalCount / pageSize;
        } else {
            totalPage = totalCount / pageSize + 1;
        }
        return totalPage;
    }

    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("&amp;pageSize=").append(getPageSize());
        try {

            Map<String, Object> property = BeanHelper.getProperty(this);

            Set<Map.Entry<String, Object>> entries = property.entrySet();
            for (Map.Entry<String, Object> entry : entries) {
                buf.append("&amp;").append(entry.getKey()).append("=").append(entry.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buf.toString();
    }


}
