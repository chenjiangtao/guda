/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.dal.bo;

/**
 * 
 * @author gag
 * @version $Id: UmsMsgOutStat.java, v 0.1 2012-8-30 上午9:05:52 gag Exp $
 */
public class UmsMsgOutStat {

    private String mediaId;

    private int    count;

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
