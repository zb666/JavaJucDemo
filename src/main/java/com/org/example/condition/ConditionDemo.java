package com.org.example.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionDemo {

    private Lock lock = new ReentrantLock();

    Condition condition = lock.newCondition();

    private String tvName = "广告";

    static class TV extends Thread {
        private ConditionDemo conditionDemo;

        public TV(ConditionDemo conditionDemo) {
            this.conditionDemo = conditionDemo;
        }

        @Override
        public void run() {
            conditionDemo.waitTv();
        }
    }

    private void waitTv() {
        lock.lock();
        try {
            while (tvName.equals("广告")){
                try{
                    condition.await();
                    if(tvName.equals("广告")){
                        System.out.println(Thread.currentThread().getName()+"AAA");
                    }
                }catch (InterruptedException ex){
                    ex.printStackTrace();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

}
