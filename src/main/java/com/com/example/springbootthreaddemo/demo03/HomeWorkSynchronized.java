package com.com.example.springbootthreaddemo.demo03;

import java.io.IOException;

public class HomeWorkSynchronized {
    //change to lock ,what is the result
    static String lock = "a";
    static Integer count=0;

    public static void incr(){

        synchronized (count) {

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("count-hash-before++=" + count + " hashcode: "+ System.identityHashCode(count));
            count++;
            System.out.println("count-hash-after++="+ count + " hashcode: " + System.identityHashCode(count));

        }

    }

    public static void main(String[] args) throws IOException, InterruptedException {

        for(int i=0;i<1000;i++){
            new Thread(()-> HomeWorkSynchronized.incr()).start();
        }

        Thread.sleep(5000);

        System.out.println("result:"+count);

    }

}