package com.org.example.threadutil;

import java.time.LocalDateTime;

public class WaitNotifyDemo {

    private static Object object = new Object();

    public static void main(String[] args) throws InterruptedException {

        InnerThread innerThread = new InnerThread();
        Thread thread = new Thread(innerThread, "Wait-Thread");

        thread.start();



    }

    //监视器必须是同一个，因为synchronized使用之后才能获取到监视器
    static class InnerThread implements Runnable {

        @Override
        public void run() {
            while (true) {
                synchronized (object) {
                    try {
                        object.wait(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(
                            LocalDateTime.now().getSecond()+"="+
                            Thread.currentThread().getName() + " = " + Thread.currentThread().getState());
                    object.notify();
                }
            }
        }
    }
}
