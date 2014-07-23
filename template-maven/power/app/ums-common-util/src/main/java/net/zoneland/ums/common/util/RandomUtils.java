/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.common.util;

import java.util.Random;

/**
 * 
 * @author gang
 * @version $Id: RandomUtils.java, v 0.1 2012-8-23 下午10:43:00 gang Exp $
 */
public class RandomUtils {

    private static Random   random     = new Random();

    public static final int MAX_NUMBER = 1000000;

    private static int[]    initRandom = new int[MAX_NUMBER];

    private static int[]    randoms    = new int[MAX_NUMBER];

    private static int      count      = 0;

    static {
        for (int i = 0; i < MAX_NUMBER; i++) {
            initRandom[i] = i;
            randoms[i] = i;
        }
    }

    public synchronized static int getRandom() {
        int i = random.nextInt(MAX_NUMBER);

        while (randoms[i] == 0) {
            i = random.nextInt(MAX_NUMBER);
        }
        randoms[i] = 0;
        ++count;
        if (count > MAX_NUMBER / 2) {
            randoms = initRandom;
            count = 0;
        }
        return i;
    }

}
