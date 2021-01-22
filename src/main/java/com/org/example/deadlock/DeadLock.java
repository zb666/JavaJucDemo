package com.org.example.deadlock;

public class DeadLock {

    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();

    public static void methodA() throws InterruptedException {
        synchronized (lock1) {
            System.out.println(Thread.currentThread().getName()+": 拿到了锁");
            //拿到了资源1的锁，休眠是为了让线程B抢占到该锁
            Thread.sleep(100);
            Thread.sleep(5000);
            synchronized (lock2){
                System.out.println("获取到了资源2");
            }
        }
    }

    public static void methodB() throws InterruptedException {
        System.out.println(Thread.currentThread().getName()+": 拿到了锁");
        synchronized (lock2) {
            //拿到了资源1的锁，休眠是为了让线程B抢占到该锁
            Thread.sleep(100);
            synchronized (lock1){
                System.out.println("获取到了资源1");
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    methodA();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    methodB();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

}
