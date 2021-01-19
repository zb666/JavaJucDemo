package com.org.example;

import org.openjdk.jol.info.ClassLayout;

/**
 * 风骚的Mic 老师
 * create-date: 2020/5/17-21:34
 */
public class LockDemo {

    public static void main(String[] args) throws InterruptedException {
        LockDemo lockDemo=new LockDemo();
        Thread t1=new Thread(()->{
            synchronized (lockDemo){
                System.out.println("t1 抢占到锁");
                System.out.println(ClassLayout.parseInstance(lockDemo).toPrintable());
            }
        });
        t1.start();
        synchronized (lockDemo){
            System.out.println("Main 抢占锁");
            System.out.println(ClassLayout.parseInstance(lockDemo).toPrintable());
        }
    }
}
