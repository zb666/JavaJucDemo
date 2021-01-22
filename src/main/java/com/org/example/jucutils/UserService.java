package com.org.example.jucutils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserService {

    private final ExecutorService executorService = Executors.newFixedThreadPool(5);
    ArrayBlockingQueue<User> arrayBlockingQueue = new ArrayBlockingQueue<>(10);

    {
        init();
    }

    private void init() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    //10个人抢消费券
                    try {
                        User consumerUser = arrayBlockingQueue.take();
                        System.out.println("消费者获取到了消费券: " + consumerUser);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void producer() throws InterruptedException {
        int i =5;
        while (--i>=0){
            Thread.sleep(1000);
            arrayBlockingQueue.put(new User("金额: "+i));
        }

    }

    public static void main(String[] args) throws InterruptedException {
        UserService userService = new UserService();
        userService.producer();
    }
}
