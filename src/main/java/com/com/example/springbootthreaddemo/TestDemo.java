package com.com.example.springbootthreaddemo;

/**
 * 风骚的Mic 老师
 * create-date: 2020/5/16-20:28
 */
public class TestDemo extends Thread{
    @Override
    public void run() {
       //线程会执行的指令
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Come in");
    }

    public static void main(String[] args) {
        TestDemo testDemo=new TestDemo();
        testDemo.start();

        testDemo.stop(); //不建议 强制终止这个线程。 线程终止，
        /**
         * 1. 就是执行完了就终止了<br>
         * 2. 强制终止这个线程，run 方法运行一半强制终止<br>
         * 3. 不想强制终止，我发送终止的通知<br>
         */

        //发送终止通知


    }
}
