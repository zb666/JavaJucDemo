package com.org.example.volatiledemo;

import com.org.example.DateUtil;
import org.junit.Test;

public class VolatileOnIDemo {
    public static boolean stop = false;
    private volatile static int i = 0; //这里的i也会触发切换<br>

    /**
     * 由于是volatile所以也结束了<br>
     */
    @Test
    public void test1()
    {
        System.out.println("main 开始执行时间：" + DateUtil.nowDateStr());

        Thread t1 = new Thread(
                ()-> {
                    System.out.println("t1 thread begin");
                    int i =0;
                    while (!stop)
                    {
                        i++;
                    }
                    System.out.println("t1 结束执行时间：" + DateUtil.nowDateStr());
                }
        );
        t1.start();
        try {
            Thread.sleep(1000);
//            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stop = true;
        System.out.println("main 结束执行时间：" + DateUtil.nowDateStr());

    }

    /**
     * 这个结束了，标志stop没有加volatile但是i++加了volatile这个跟内存屏障有关系<br>
     * Wed Dec 02 22:12:39 CST 2020
     * main 开始执行时间：2020-12-02 22:12:39.039
     * t1 thread begin
     * Wed Dec 02 22:12:40 CST 2020
     * main 结束执行时间：2020-12-02 22:12:40.040
     */
}
