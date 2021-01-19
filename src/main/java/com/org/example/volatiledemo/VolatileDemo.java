package com.org.example.volatiledemo;

import com.org.example.DateUtil;
import org.junit.Test;

/**
 * 我不用像NoVolatileDemo一样，在里面加synchronize关键字和println等来结束，这里只要加volatile关键字就可以
 */
public class VolatileDemo {
    public static  volatile  boolean stop = false;

    /**
     * 由于是volatile所以也结束了<br>
     */
    @Test
    public void test1()
    {
        Thread t1 = new Thread(
                ()-> {
                    System.out.println("t1 thread begin");
                    int i =0;
                    while (!stop)
                    {
                        i++;
                        //System.out.println("t1->i = " + i);
                    }
                    System.out.println("t1 thread will be end.");
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
        System.out.println("executed end.");
    }

    @Test
    public void testT1End()
    {
        System.out.println("开始执行时间：" + DateUtil.nowDateStr());
        Thread t1 = new Thread(
                ()-> {
                    //System.out.println("t1 thread begin");
                    int i =0;
                    while (!stop)
                    {
                        i++;
                        //System.out.println("t1->i = " + i);
                    }
                    System.out.println("线程t1要结束  i=" + i);
                }
        );
        t1.start();
        try {
            Thread.sleep(15000);
//            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stop = true;
        System.out.println("结束执行时间：" + DateUtil.nowDateStr());
    }
    /**
     * 上述执行结果：<br>
     * Wed Dec 02 21:31:41 CST 2020
     * 开始执行时间：2020-12-02 21:31:41.041
     * Wed Dec 02 21:31:56 CST 2020
     * 线程t1要结束  i=467304549
     * 结束执行时间：2020-12-02 21:31:56.056
     */


}
