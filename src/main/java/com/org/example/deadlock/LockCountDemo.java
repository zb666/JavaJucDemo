package com.org.example.deadlock;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockCountDemo {
    private int start = 10;
    private int middle = 90;
    private int end = 200;

    private volatile int tmpAns1 = 0;
    private volatile int tmpAns2 = 0;


    private Lock lock = new ReentrantLock();

    private Condition condition = lock.newCondition();

    private AtomicInteger count = new AtomicInteger(0);

    public int add(int i,int j){
        try {
            lock.lock();
            int sum = 0;
            for (int tmp =i;tmp<j;tmp++){
              sum+=tmp;
            }
            return sum;
        }finally {
            atomic();
            lock.unlock();
        }
    }

    private void atomic() {
        if(count.addAndGet(1)==2){
            condition.signal();//唤醒
        }
    }

}
