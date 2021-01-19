package com.org.example;

/**
 * Hello world!
 *
 */
public class App {
    public static int count=0;
    static Object lock=new Object();
    public static void incr(){
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //线程级别因为刚开始 count++ 不具有原子性<br>
        synchronized (lock) {
            count++;
        }
    }
    public static void main( String[] args ) throws InterruptedException {
        for(int i=0;i<1000;i++){
            new Thread(()->App.incr()).start();
        }
        Thread.sleep(3000); //保证线程执行结束
        System.out.println("运行结果："+count);
    }
}
