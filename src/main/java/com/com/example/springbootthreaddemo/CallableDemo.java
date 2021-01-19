package com.com.example.springbootthreaddemo;

import java.util.concurrent.*;

/**
 * 风骚的Mic 老师
 * create-date: 2020/5/16-20:32
 */
public class CallableDemo implements Callable<String> {

    @Override
    public String call() throws Exception {
        System.out.println("come in");
        Thread.sleep(10000);
        return "SUCCESS";
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService=Executors.newFixedThreadPool(1);
        CallableDemo callableDemo=new CallableDemo();
        Future<String> future=executorService.submit(callableDemo);
        System.out.println(future.get()); //阻塞
    }
}
