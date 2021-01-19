package com.org.example.demo;

import java.util.Queue;

/**
 * 风骚的Mic 老师
 * create-date: 2020/5/17-21:48
 */
public class Consumer implements Runnable{
    private Queue<String> msg;
    private int maxSize;

    public Consumer(Queue<String> msg, int maxSize) {
        this.msg = msg;
        this.maxSize = maxSize;
    }

    @Override
    public void run() {
        while(true){
            synchronized (msg){
                //需要while循环，因为唤醒了要继续放<br>
                while(msg.isEmpty()){
                    //如果消息队列为空了
                    try {
                        msg.wait(); //阻塞当前线程
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("消费者消费消息："+msg.remove());
                //这里肯定消费了，前面msg.remove()了嘛，所以这里notify<br>
                msg.notify(); //唤醒处于阻塞状态下的生产者
            }
        }
    }
}
