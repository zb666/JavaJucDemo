package com.org.example.demo;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 风骚的Mic 老师
 * create-date: 2020/5/17-21:54
 */
public class TestMain {

    public static void main(String[] args) {
        Queue<String> queue=new LinkedList<>();
        int maxSize=5;
        Product product=new Product(queue,maxSize);
        Consumer consumer=new Consumer(queue,maxSize);
        Thread t1=new Thread(product);
        Thread t2=new Thread(consumer);
        t1.start();
        t2.start();
    }
}
