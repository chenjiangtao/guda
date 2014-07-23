/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.mvc.runtime.core.queue.test;

/**
 * 
 * @author gag
 * @version $Id: Main.java, v 0.1 2012-5-5 ����5:53:47 gag Exp $
 */
public class Main {

    public static void main(String[] args) {
        //        Main m = new Main();
        //        TestObj obj = new TestObj();
        //        Thread thread = new Thread(m.new Test(obj));
        //        thread.start();
        //        obj.exchange();
        int hashCode = 17;
        hashCode = 31 * hashCode + "gag".hashCode();

        System.out.println(Integer.valueOf(hashCode));
    }

    class Test implements Runnable {
        private TestObj obj;

        public Test(TestObj obj) {
            this.obj = obj;
        }

        /** 
         * @see java.lang.Runnable#run()
         */
        public void run() {
            System.out.println(obj.get());
        }

    }
}
