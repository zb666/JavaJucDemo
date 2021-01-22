package com.org.example.jucutils;

import java.util.concurrent.Semaphore;

//相当于线程限流
public class SemaphoreDemo {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(5);
        for (int i = 0; i < 10; i++) {
            new Car(semaphore, i).start();
        }
    }

    static class Car extends Thread {

        private Semaphore semaphore;

        private int num;

        public Car(Semaphore semaphore, int num) {
            this.semaphore = semaphore;
            this.num = num;
        }

        @Override
        public void run() {
            //获取令牌
            try {
                semaphore.acquire();
                System.out.println("第" + num + "线程占用了第一个令牌");
                Thread.sleep(5000);
                System.out.println("第" + num + "线程释放了令牌");
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
