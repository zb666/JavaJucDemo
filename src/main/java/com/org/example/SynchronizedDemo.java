package com.org.example;

/**
 * 风骚的Mic 老师
 * create-date: 2020/5/17-20:17
 */
public class SynchronizedDemo {

    Object lock=new Object();
    //只针对于当前对象实例有效.
    public SynchronizedDemo(Object lock){
            this.lock=lock;
    }
     void demo(){
         synchronized(lock){

         }
    }
    void demo03(){
        Object o = new Object();
        synchronized (o){
            //线程安全性问题.
        }
    }
    //-------------//类锁. 针对所有对象都互斥
    synchronized  static void demo04(){
    }
    void demo05(){
        synchronized (SynchronizedDemo.class){
        }
    }
    //锁的范围
    // 实例锁，对象实例
    // 静态方法、类对象、 类锁
    // 代码块
    public static void main(String[] args) {
        Class clazz=SynchronizedDemo.class;
        Object object=new Object();
        Object object1=new Object();
        SynchronizedDemo synchronizedDemo=new SynchronizedDemo(object);
        SynchronizedDemo synchronizedDemo2=new SynchronizedDemo(object);
        //锁的互斥性。
        new Thread(()->{
            synchronizedDemo.demo();
        },"t1").start();

        new Thread(()->{
            synchronizedDemo2.demo();
        },"t2").start();

        new Thread(()->{
            synchronizedDemo.demo();
        },"t3").start();
        // t1与t3是有互斥性的
        // t1与t2没有互斥性

    }

}
