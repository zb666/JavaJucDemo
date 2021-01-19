package com.gupao.jucc;

import java.util.concurrent.*;

public class CallableDemo implements Callable<String> {
    public String call() throws Exception {
        Thread.sleep(3000);
        return "call result";
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CallableDemo callableDemo = new CallableDemo();
        Future<String> futureResult = executorService.submit(callableDemo);
        try {
            String res = futureResult.get();
            System.out.println("CallAble Result: "+res);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}
