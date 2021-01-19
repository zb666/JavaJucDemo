package com.com.example.springbootthreaddemo.demo03;

import java.util.concurrent.TimeUnit;

/**
 * 风骚的Mic 老师
 * create-date: 2020/5/16-21:41
 */
public class InterruptDemoWithInterruptedFlag implements Runnable{

    //stop

    private int i=1;
    @Override
    public void run() {
//        Thread.currentThread().isInterrupted()=false;\
//        表示一个中断的标记  interrupted=fasle
        while(!Thread.currentThread().isInterrupted()){
            //
            System.out.println("Test:"+ (i++) + Thread.currentThread().isInterrupted());
        }
        System.out.println("after while:" + Thread.currentThread().isInterrupted());
        //
    }

    /**
    public void run() {
        while(true){
            //这个线程是没办法终止的会一直运行<br>
            System.out.println("Test:"+i++);
        }
    }
     */

    public static void main(String[] args) {
        Thread thread=new Thread(new InterruptDemoWithInterruptedFlag());
        thread.start();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("执行thread.interrupt()方法");
        thread.interrupt(); //设置 interrupted=true;
    }
}
