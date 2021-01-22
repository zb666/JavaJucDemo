package com.org.example.threadlocalDemo;
//ThreadLocal原理是：ThreadLocal类里面有一个ThreadLocalMap，
//ThreadLocalMap是一个hashmap,key是ThreadLocal，value
//通过当前线程去获得一个ThreadLocalMap，然后通过ThreadLocal作为key去获取一个object对象



public class ThreadLocalDemo {

    //private static  int num=0;
    private static ThreadLocal<Integer> local=new ThreadLocal<Integer>(){
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };

    public static void main(String[] args) {
        for(int i=0;i<5;i++){
            new Thread(()->{
                int localNum=local.get().intValue()+5;
                System.out.println("ThreanName:"+Thread.currentThread().getName()+"localNum="+localNum);
            },"Thread-"+i).start();
        }
    }
}
