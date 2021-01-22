package com.org.example.jucutils;

import java.util.concurrent.CountDownLatch;

public class CountDownLaunchDemo {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(3);

        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"->begin");
            countDownLatch.countDown();
            System.out.println(Thread.currentThread().getName()+"->end");
        },"t1").start();

        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"->begin");
            countDownLatch.countDown();
            System.out.println(Thread.currentThread().getName()+"->end");
        },"t2").start();

        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"->begin");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            countDownLatch.countDown();
            System.out.println(Thread.currentThread().getName()+"->end");
        },"t3").start();

        //阻塞
        System.out.println("阻塞开始");
        countDownLatch.await();
        System.out.println("阻塞结束");
    }

}
