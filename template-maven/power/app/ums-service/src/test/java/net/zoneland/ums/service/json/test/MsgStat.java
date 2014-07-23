/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.service.json.test;

import java.io.Serializable;

/**
 * 
 * @author gag
 * @version $Id: MsgStat.java, v 0.1 2012-8-27 下午6:17:24 gag Exp $
 */
public class MsgStat implements Serializable {

    /**  */
    private static final long serialVersionUID = -1976772916981829941L;

    private long              success          = 100;

    private long              failure          = 3;

    public long getSuccess() {
        return success;
    }

    public void setSuccess(long success) {
        this.success = success;
    }

    public long getFailure() {
        return failure;
    }

    public void setFailure(long failure) {
        this.failure = failure;
    }

}
