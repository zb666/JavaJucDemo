package com.org.example.threadlocalDemo;

public class ThreadLocalTest1 {

//    private volatile static int num = 0 ;

    static ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>(){
        @Override
        protected Integer initialValue(){
            return 0;
        }
    };
    public static void main(String[] args) {
         new Thread(()->{
                int num = threadLocal.get();
                System.out.println("num=" + num);
                threadLocal.set(num+=5);
                System.out.println(Thread.currentThread().getName()+"->"+num);
                threadLocal.set(num+=5);
                System.out.println("after set threadLocal again");
         },"线程1").start();
    }
}
