/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.foodoon.mvc.runtime.core.queue;

import java.util.concurrent.Delayed;

/**
 * 
 * @author gag
 * @version $Id: MergeMsg.java, v 0.1 2012-5-3 下午11:34:42 gag Exp $
 */
public interface Merge extends Delayed {

    /**
     * 对元素进行合并
     * @param msg
     */
    void merge(Merge msg);

    /**
     * 元素是否需要合并
     * @return
     */
    boolean needMerge();

}
