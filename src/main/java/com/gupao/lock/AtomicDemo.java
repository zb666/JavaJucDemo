package com.gupao.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AtomicDemo {

    private static Lock sLock = new ReentrantLock(false);

    public static void main(String[] args) {
        sLock.lock();


    }
}
