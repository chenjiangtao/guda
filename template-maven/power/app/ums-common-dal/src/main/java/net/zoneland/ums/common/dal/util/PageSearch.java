/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.dal.util;

import net.zoneland.ums.common.dal.bo.BasePojo;

/**
 * 
 * @author XuFan
 * @version $Id: PageSearch.java, v 0.1 Aug 14, 2012 5:34:07 PM XuFan Exp $
 */
public class PageSearch {
    private int      recordFirst; //起始值
    private int      recordEnd;  //结束值
    private BasePojo searchObj;  //查询条件对象

    public PageSearch(BasePojo pojo, int first, int end) {
        searchObj = pojo;
        recordEnd = end;
        recordFirst = first;
    }

    public int getRecordFirst() {
        return recordFirst;
    }

    public void setRecordFirst(int recordFirst) {
        this.recordFirst = recordFirst;
    }

    public int getRecordEnd() {
        return recordEnd;
    }

    public void setRecordEnd(int recordEnd) {
        this.recordEnd = recordEnd;
    }

    public BasePojo getSearchObj() {
        return searchObj;
    }

    public void setSearchObj(BasePojo searchObj) {
        this.searchObj = searchObj;
    }

}
