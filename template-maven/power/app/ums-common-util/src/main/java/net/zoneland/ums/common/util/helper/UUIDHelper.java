/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.util.helper;

/**
 *用以辅助本项目中对UUID的操作
 * @author wangyong
 * @version $Id: UUIDHelper.java, v 0.1 2012-8-12 下午4:45:35 wangyong Exp $
 */
public class UUIDHelper {

    /**
     *本类的作用是做UUID字符串后面加个标识符
     *
     * @return
     */
    public static String generateUUID(String uuid, String identifier) {
        return uuid + identifier;
    }

    /**
     * 解析拥有标识的uuid并获得uuid
     *uuid_person通过本方法将获得uuid
     * @param input
     * @return
     */
    public static String getUUID(String input) {
        if (input == null) {
            return null;
        } else if (input.lastIndexOf("_") < 0) {
            return null;
        } else {
            return input.substring(0, input.lastIndexOf("_"));
        }
    }

    /**
     *解析拥有标识符的uuid，获得标识符
     *uuid_person通过这个方法我们讲获得person
     * @param input
     * @return
     */
    public static String getIdentifier(String input) {
        if (input == null) {
            return null;
        } else if (input.lastIndexOf("_") < 0) {
            return null;
        } else {
            return input.substring(input.lastIndexOf("_") + 1);
        }
    }
}
