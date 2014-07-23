/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.msg.process;

import net.zoneland.ums.biz.msg.PrimitiveMessage;

/**
 *
 * @author gag
 * @version $Id: Checker.java, v 0.1 2012-9-17 上午8:56:53 gag Exp $
 */
public interface Checker {
    /**
     * 关键字检查,通过返回true,不通过返回false;
     *
     * @param primitiveMessage
     * @return
     */
    boolean doCheck(PrimitiveMessage primitiveMessage);

}
