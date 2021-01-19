package com.org.example.volatiledemo;

import org.example.DateUtil;
import org.junit.Test;

import java.io.File;

public class NoVolatileDemo {
    public static boolean stop = false;

    /**
     * 这里禁用JIT你配置了 java.JITcompile=NONE，应该不优化了<br>
     */
    @Test
    public void testT1NotEnd()
    {
        System.out.println("开始执行时间：" + DateUtil.nowDateStr());
        Thread t1 = new Thread(
                ()-> {
                    //System.out.println("t1 thread begin");
                    int i =0;
                    while (!stop)
                    {
                        i++;
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
     * 在server端
     *  while (!stop)
     *  {
     *     i++;
     *     System.out.println("t1->i = " + i);
     *     //这个只是在server端有效-->活性失败，如果是client端的话就不会结束
     *  }
     *  会被优化为
     *  if(stop)
     *  {
     *     while(true)
     *     {
     *         i++;
     *     }
     *  }
     *  所以这个testT1NotEnd为什么不会结束
     */


    /**
     * 我在VM Options那里配置了：-Djava.compiler=NONE <br>
     */
    @Test
    public void testT1EndTurnOffJIT()
    {
        System.out.println("开始执行时间：" + DateUtil.nowDateStr());
        Thread t1 = new Thread(
                ()-> {
                    //System.out.println("t1 thread begin");
                    int i =0;
                    while (!stop)
                    {
                        i++;
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
     * Wed Dec 02 21:56:00 CST 2020
     * 开始执行时间：2020-12-02 21:56:00.000
     * 线程t1要结束  i=-827693935  运行结果输出了这个（）
     * Wed Dec 02 21:56:15 CST 2020
     * 结束执行时间：2020-12-02 21:56:15.015
     */
    @Test
    public void testT1NotEnd2()
    {
        System.out.println("开始执行时间：" + DateUtil.nowDateStr());
        Thread t1 = new Thread(
                ()-> {
                    //System.out.println("t1 thread begin");
                    int i =0;
                    while (!stop)
                    {
                        i++;
                    }
                    System.out.println("线程t1要结束  i=" + i);
                }
        );
        t1.start();
        try {
            Thread.sleep(15000);
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stop = true;
        System.out.println("结束执行时间：" + DateUtil.nowDateStr());
    }

    /**
     * test1()执行结果：
     * Wed Dec 02 21:08:24 CST 2020
     * 开始执行时间：2020-12-02 21:08:24.024
     *
     * t1永远不会结束，因为有t1.join()，主线程要等待t1结束，所以主线程也不会结束<br>
     */

    @Test
    public void test2EndWithPrintlnInWhile()
    {

        System.out.println("main 开始执行时间：" + DateUtil.nowDateStr());
        Thread t1 = new Thread(
                ()-> {
                    System.out.println("t1 thread begin");
                    int i =0;
                    while (!stop)
                    {
                        i++;
                        System.out.println("t1->i = " + i);
                        // System.out.print里面有个同步关键字，释放锁的操作会强制把工作内存数据同步到主内存，下次读取到最新值
                        //这个只是在server端有效-->活性失败，如果是client端的话就不会结束
                    }
                    System.out.println("t1 结束时间：" + DateUtil.nowDateStr());
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
        System.out.println("main结束执行时间：" + DateUtil.nowDateStr());
    }
    /**
     * ......
     * Wed Dec 02 21:36:16 CST 2020
     * Wed Dec 02 21:36:16 CST 2020
     * 开始执行时间：2020-12-02 21:36:16.016
     * t1 结束时间：2020-12-02 21:36:16.016
     */


    @Test
    public void test2EndWithSynchronized()
    {

        System.out.println("main 开始执行时间：" + DateUtil.nowDateStr());
        Thread t1 = new Thread(
                ()-> {
                    System.out.println("t1 thread begin");
                    int i =0;
                    while (!stop)
                    {
                        i++;
                        synchronized (NoVolatileDemo.class)
                        {}
                        // System.out.print里面有个同步关键字，释放锁的操作会强制把工作内存数据同步到主内存，下次读取到最新值
                        //这个只是在server端有效-->活性失败，如果是client端的话就不会结束
                    }
                    System.out.println("t1 结束时间：" + DateUtil.nowDateStr());
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
        System.out.println("main结束执行时间：" + DateUtil.nowDateStr());
    }
    /**
     * Wed Dec 02 22:01:04 CST 2020
     * main 开始执行时间：2020-12-02 22:01:04.004
     * t1 thread begin
     * Wed Dec 02 22:01:05 CST 2020
     * Wed Dec 02 22:01:05 CST 2020
     * t1 结束时间：2020-12-02 22:01:05.005
     * main结束执行时间：2020-12-02 22:01:05.005
     */

    @Test
    public void test2EndWithIO()
    {

        System.out.println("main 开始执行时间：" + DateUtil.nowDateStr());
        Thread t1 = new Thread(
                ()-> {
                    System.out.println("t1 thread begin");
                    int i =0;
                    while (!stop)
                    {
                        i++;
                        File f = new File("demo.txt");
                        //这个只是在server端有效-->活性失败，如果是client端的话就不会结束
                    }
                    System.out.println("t1 结束时间：" + DateUtil.nowDateStr());
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
        System.out.println("main结束执行时间：" + DateUtil.nowDateStr());
    }
    /**
     * 结束了
     * Wed Dec 02 22:02:45 CST 2020
     * main 开始执行时间：2020-12-02 22:02:45.045
     * t1 thread begin
     * Wed Dec 02 22:02:46 CST 2020
     * Wed Dec 02 22:02:46 CST 2020
     * main结束执行时间：2020-12-02 22:02:46.046
     * t1 结束时间：2020-12-02 22:02:46.046
     */

    /**
     * 也可以结束，是由于while循环里有Thread.sleep
     */
    @Test
    public void test3EndWithSleep()
    {
        System.out.println("main 开始执行时间：" + DateUtil.nowDateStr());
        Thread t1 = new Thread(
                ()-> {
                    System.out.println("t1 thread begin");
                    int i =0;
                    while (!stop)
                    {
                        i++;
                        try {
                            Thread.sleep(5);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("t1 开始执行时间：" + DateUtil.nowDateStr());
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
     * 原理参考：https://docs.oracle.com/javase/specs/jls/se7/html/jls-17.html ，从下面英文解释说是Thread.sleep不会导致循环结束，但是从执行结果上来看确实会的
     * 可能的解释是Thread.sleep会导致线程切换<br>
     * Thread.sleep causes the currently executing thread to sleep (temporarily cease execution) for the specified duration, subject to the precision and accuracy of system timers and schedulers. The thread does not lose ownership of any monitors, and resumption of execution will depend on scheduling and the availability of processors on which to execute the thread.
     * It is important to note that neither Thread.sleep nor Thread.yield have any synchronization semantics. In particular, the compiler does not have to flush writes cached in registers out to shared memory before a call to Thread.sleep or Thread.yield, nor does the compiler have to reload values cached in registers after a call to Thread.sleep or Thread.yield.
     * For example, in the following (broken) code fragment, assume that this.done is a non-volatile boolean field:
     * while (!this.done)
     *     Thread.sleep(1000);
     * The compiler is free to read the field this.done just once, and reuse the cached value in each execution of the loop. This would mean that the loop would never terminate, even if another thread changed the value of this.done.
     */

    /**
     * Wed Dec 02 21:41:21 CST 2020
     * main 开始执行时间：2020-12-02 21:41:21.021
     * t1 thread begin
     * Wed Dec 02 21:41:22 CST 2020
     * Wed Dec 02 21:41:22 CST 2020
     * t1 开始执行时间：2020-12-02 21:41:22.022
     * main 结束执行时间：2020-12-02 21:41:22.022
     */

    /**
     * 特别奇怪，这个JIT应该优化成这个样子，但是跑也结束了<br>
     */
    @Test
    public void test4()
    {
        Thread t1 = new Thread(
                ()-> {
                    System.out.println("t1 thread begin");
                    int i =0;
                    if(!stop) {
                        while (true) {
                            i++;
                        }
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
        System.out.println("main executed end.");
    }

    @Test
    public void testSynchronizedNullBlock()
    {
        Thread t1 = new Thread(
                ()-> {
                    //System.out.println("t1 thread begin");
                    int i =0;
                    while (!stop)
                    {
                        i++;
                       synchronized (NoVolatileDemo.class)
                       {

                       }
                    }
                    //System.out.println("t1 thread will be end.");
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
        System.out.println("main executed end.");
    }

    @Test
    public void testWithNewFileIn()
    {
        Thread t1 = new Thread(
                ()-> {
                    //System.out.println("t1 thread begin");
                    int i =0;
                    while (!stop)
                    {
                        i++;
                        new File("a.txt");
                    }
                    //System.out.println("t1 thread will be end.");
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
        System.out.println("main executed end.");
    }

}
