/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.util.helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.log4j.Logger;

/**
 *
 * @author wangyong
 * @version $Id: ObjectHelper.java, v 0.1 2012-8-17 上午12:11:39 wangyong Exp $
 */
public class ObjectHelper {
    private static final Logger logger = Logger.getLogger(ObjectHelper.class);

    public static Object deepClone(Object obj) {
        try {
            ByteArrayOutputStream buf = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(buf);
            out.writeObject(obj);
            out.close();
            byte[] ary = buf.toByteArray();
            ByteArrayInputStream bais = new ByteArrayInputStream(ary);
            ObjectInputStream in = new ObjectInputStream(bais);
            Object goal = in.readObject();
            in.close();
            return goal;
        } catch (Exception e) {
            logger.error("转化出错", e);
            return null;
        }
    }
}
