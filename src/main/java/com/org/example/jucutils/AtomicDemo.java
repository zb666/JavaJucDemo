package com.org.example.jucutils;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicDemo {

    private static AtomicInteger atomicInteger = new AtomicInteger(10);

//    private static volatile int i = 0;

    public static void inCreTest() throws InterruptedException {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        atomicInteger.getAndIncrement();
//        i++;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        AtomicDemo.inCreTest();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(atomicInteger.get());
//        System.out.println("当前的值是: "+i);
    }
}
