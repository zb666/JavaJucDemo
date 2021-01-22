package com.org.example.jucutils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class App {

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<String>(10);
        blockingQueue.put("Mic");
        blockingQueue.take();
        System.out.println("Hello World");
    }
}
