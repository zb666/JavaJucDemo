package com.org.example.deadlock;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockDemo {

    private Lock lock = new ReentrantLock();

    public void workOn(){
        System.out.println(Thread.currentThread().getName()+"：上班了");
    }

    public void workOff(){
        System.out.println(Thread.currentThread().getName()+"：下班");
    }

    public void work(){
        lock.lock();
        try{
            workOn();
            System.out.println(Thread.currentThread().getName()+"工作中!!!");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        LockDemo lockDemo = new LockDemo();

        Thread[] arrayThread = new Thread[10];

        for (Thread thread : arrayThread) {

        }
    }

}
