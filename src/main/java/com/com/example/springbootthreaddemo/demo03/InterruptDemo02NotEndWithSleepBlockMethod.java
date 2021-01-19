package com.com.example.springbootthreaddemo.demo03;

import java.util.concurrent.TimeUnit;

/**
 * 风骚的Mic 老师
 * create-date: 2020/5/16-21:47
 * 这个是带有阻塞方法的，interrupte的是会后另一个线程刚好sleep，这个可以跟InterruptDemoWithoutZuseMethod.java文件结果比较（while循环）不阻塞
 * JDK在try(InterruptedException e)自动做了复位
 */
public class InterruptDemo02NotEndWithSleepBlockMethod implements Runnable{
    @Override
    public void run() {
        Integer obj = 1;
        System.identityHashCode(obj);

        //标记
        int count = 1;
        while(!Thread.currentThread().isInterrupted()){ //false
            try {
                System.out.println("run开始sleep()方法");
                System.out.println("第几次执行：" + count);
                count ++;

                System.out.println("before-sleep:" + Thread.currentThread().isInterrupted());

                //不会等200秒才响应中断
                //因为一个sleep状态线程会被中断，join也会
                TimeUnit.SECONDS.sleep(200);
                /*
                 * COMMENT-HIGHLIGHT：如果这里SLEEP了你在main函数那里interrupt了，但是又不响应那么中断的意义就不大<br>
                 */
                System.out.println("after-sleep:" + Thread.currentThread().isInterrupted());
                // wait/join/park
                //但凡让线程阻塞的机制都有一个InterruptedException抛出来，才能去响应它
            } catch (InterruptedException e) { //触发了线程的复位，又变成了false（这个是JVM来实现的）
                //但凡线程阻塞的操作，就会有一个InterruptedException抛出来就是为了结束阻塞响应中断。
                //你们想这个线程在Sleep状态，我main方法调用t1.interrupt()方法的时候怎么唤醒睡眠状态下的线程：
                //在os_linux.cpp源码里会有unpark()方法唤醒睡眠状态下线程
                System.out.println("interrupted-catch:" + Thread.currentThread().isInterrupted());

                e.printStackTrace();

                System.out.println("interrupted-catch-interrupte-agin:" + Thread.currentThread().isInterrupted());
                //你可以调用interrput()的方法（不睡了while循环结束了），也可以不调用（继续睡觉）
                //抛出异常。。
            }
        }
        System.out.println("processor End");
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1=new Thread(new InterruptDemo02NotEndWithSleepBlockMethod());
        t1.start();
        System.out.println("开始sleep()方法");
        Thread.sleep(1000);// 是为了让t1线程sleep的时候才中断<br>
        System.out.println("执行t1.interrupteed方法");
        t1.interrupt(); //有作用 true
        //Thread.interrupted() ;//复位 静态方法复位，复位就是把标记编程false<br>
        //为什么要复位，想让处理的权限变成我自己
    }

    /**
     * 执行结果，虽然抛出了异常但是程序没有中断，因为处理中断异常的时候触发了线程的复位<br>
     * run开始sleep()方法
     * 第几次执行：2
     * before-sleep:false
     * java.lang.InterruptedException: sleep interrupted
     * 	at java.lang.Thread.sleep(Native Method)
     * 	at java.lang.Thread.sleep(Thread.java:340)
     * 	at java.util.concurrent.TimeUnit.sleep(TimeUnit.java:386)
     * 	at com.example.springbootthreaddemo.demo03.InterruptDemo02NotEndWithSleepZuseMethod.run(InterruptDemo02NotEndWithSleepZuseMethod.java:29)
     * 	at java.lang.Thread.run(Thread.java:748)
     */
}
