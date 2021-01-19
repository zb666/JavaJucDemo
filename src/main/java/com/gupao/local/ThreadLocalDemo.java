package com.gupao.local;

public class ThreadLocalDemo {

    private static ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>(){
        @Override
        protected Integer initialValue() {
            return 0; //这里返回的值是每个线程所独有的
        }
    };

    private static int sNum = 0;

    public static void main(String[] args) {
        final Thread[] threads = new Thread[5];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new Runnable() {
                public void run() {
                    Integer value = threadLocal.get();
                    threadLocal.set(sNum+=5);
                    threadLocal.remove();
                    System.out.println(Thread.currentThread().getName()+": "+threadLocal.get()+"="+value);
                }
            });
        }
        for (Thread thread : threads) {
            thread.start();
        }
    }




}
