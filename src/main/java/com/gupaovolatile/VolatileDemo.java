package com.gupaovolatile;

public class VolatileDemo {

    static volatile boolean isStop = false;

    public static void main(String[] args) {
        new Thread(new Runnable() {
            public void run() {
                int i = 0;
                while (!isStop) {
                    i++;
                }
                System.out.println("Cur I: "+i);
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                isStop = true;
            }
        }).start();

    }
}
