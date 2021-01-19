package com.com.example.springbootthreaddemo.demo02;

import java.util.concurrent.TimeUnit;

/**
 * 怎么看到线程里面的状态呢？<br>
 * 1. 运行程序<br>
 * 2. jps拿到进程ID<br>
 * 3. 通过jstack拿到堆栈信息<br>
 */

/**
 * 风骚的Mic 老师
 * create-date: 2020/5/16-21:11
 */
public class Demo {

    public static void main(String[] args) {
        new Thread(()->{
            while(true){
                try {
//                    "STATUS_01" #11 prio=5 os_prio=0 tid=0x000000001e26a000 nid=0x4788 waiting on condition [0x000000001ea1f000]
//                    java.lang.Thread.State: TIMED_WAITING (sleeping)
                    //time-waiting状态
                    TimeUnit.SECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"STATUS_01").start();  //阻塞状态

        new Thread(()->{
            while(true){
                synchronized (Demo.class){
                    try {
                        //waiting 状态
                        Demo.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
//            "STATUS_02" #12 prio=5 os_prio=0 tid=0x000000001e275000 nid=0x4ecc in Object.wait() [0x000000001eb1f000]
//            java.lang.Thread.State: WAITING (on object monitor)
        },"STATUS_02").start(); //阻塞状态

//        "BLOCKED-DEMO-01" #14 prio=5 os_prio=0 tid=0x000000001e27a000 nid=0x3494 waiting on condition [0x000000001ec1f000]
//        java.lang.Thread.State: TIMED_WAITING (sleeping)
        //因为获得锁，所以TMED_WATING
        new Thread(new BlockedDemo(),"BLOCKED-DEMO-01").start();

//        "BLOCKED-DEMO-02" #16 prio=5 os_prio=0 tid=0x000000001e27a800 nid=0x385c waiting for monitor entry [0x000000001ed1f000]
//        java.lang.Thread.State: BLOCKED (on object monitor)
        new Thread(new BlockedDemo(),"BLOCKED-DEMO-02").start();

    }
    //jps   jstack
    static class BlockedDemo extends  Thread{
        @Override
        public void run() {
            synchronized (BlockedDemo.class){
                while(true){
                    try {
                        TimeUnit.SECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

/**
 jstack 17328
         2020-09-29 17:41:51
         Full thread dump Java HotSpot(TM) 64-Bit Server VM (25.241-b07 mixed mode):

         "DestroyJavaVM" #17 prio=5 os_prio=0 tid=0x0000000002ad6000 nid=0x243c waiting on condition [0x0000000000000000]
         java.lang.Thread.State: RUNNABLE

         "BLOCKED-DEMO-02" #16 prio=5 os_prio=0 tid=0x000000001e27a800 nid=0x385c waiting for monitor entry [0x000000001ed1f000]
         java.lang.Thread.State: BLOCKED (on object monitor) COMMENT-HIGHLIGHT 为什么是阻塞状态
         at com.example.springbootthreaddemo.demo02.Demo$BlockedDemo.run(Demo.java:48)
         - waiting to lock <0x000000076ba381e0> (a java.lang.Class for com.example.springbootthreaddemo.demo02.Demo$BlockedDemo)
        at java.lang.Thread.run(Thread.java:748)

        "BLOCKED-DEMO-01" #14 prio=5 os_prio=0 tid=0x000000001e27a000 nid=0x3494 waiting on condition [0x000000001ec1f000]
        java.lang.Thread.State: TIMED_WAITING (sleeping) //COMMENT-HIGHLIGHT 这个为什么是TIME-WAITING状态
        at java.lang.Thread.sleep(Native Method)
        at java.lang.Thread.sleep(Thread.java:340)
        at java.util.concurrent.TimeUnit.sleep(TimeUnit.java:386)
        at com.example.springbootthreaddemo.demo02.Demo$BlockedDemo.run(Demo.java:48)
        - locked <0x000000076ba381e0> (a java.lang.Class for com.example.springbootthreaddemo.demo02.Demo$BlockedDemo)
        at java.lang.Thread.run(Thread.java:748)

        "STATUS_02" #12 prio=5 os_prio=0 tid=0x000000001e275000 nid=0x4ecc in Object.wait() [0x000000001eb1f000]
        java.lang.Thread.State: WAITING (on object monitor)
        at java.lang.Object.wait(Native Method)
        - waiting on <0x000000076b5c51e0> (a java.lang.Class for com.example.springbootthreaddemo.demo02.Demo)
        at java.lang.Object.wait(Object.java:502)
        at com.example.springbootthreaddemo.demo02.Demo.lambda$main$1(Demo.java:30)
        - locked <0x000000076b5c51e0> (a java.lang.Class for com.example.springbootthreaddemo.demo02.Demo)
        at com.example.springbootthreaddemo.demo02.Demo$$Lambda$2/1066516207.run(Unknown Source)
        at java.lang.Thread.run(Thread.java:748)

        "STATUS_01" #11 prio=5 os_prio=0 tid=0x000000001e26a000 nid=0x4788 waiting on condition [0x000000001ea1f000]
        java.lang.Thread.State: TIMED_WAITING (sleeping)
        at java.lang.Thread.sleep(Native Method)
        at java.lang.Thread.sleep(Thread.java:340)
        at java.util.concurrent.TimeUnit.sleep(TimeUnit.java:386)
        at com.example.springbootthreaddemo.demo02.Demo.lambda$main$0(Demo.java:18)
        at com.example.springbootthreaddemo.demo02.Demo$$Lambda$1/780237624.run(Unknown Source)
        at java.lang.Thread.run(Thread.java:748)

        "Service Thread" #10 daemon prio=9 os_prio=0 tid=0x000000001d822000 nid=0x50c runnable [0x0000000000000000]
        java.lang.Thread.State: RUNNABLE

        "C1 CompilerThread2" #9 daemon prio=9 os_prio=2 tid=0x000000001d785800 nid=0x321c waiting on condition [0x0000000000000000]
        java.lang.Thread.State: RUNNABLE

        "C2 CompilerThread1" #8 daemon prio=9 os_prio=2 tid=0x000000001d784800 nid=0x3c8c waiting on condition [0x0000000000000000]
        java.lang.Thread.State: RUNNABLE

        "C2 CompilerThread0" #7 daemon prio=9 os_prio=2 tid=0x000000001d780800 nid=0x1a38 waiting on condition [0x0000000000000000]
        java.lang.Thread.State: RUNNABLE

        "Monitor Ctrl-Break" #6 daemon prio=5 os_prio=0 tid=0x000000001d77c000 nid=0x41c8 runnable [0x000000001dc1e000]
        java.lang.Thread.State: RUNNABLE
        at java.net.SocketInputStream.socketRead0(Native Method)
        at java.net.SocketInputStream.socketRead(SocketInputStream.java:116)
        at java.net.SocketInputStream.read(SocketInputStream.java:171)
        at java.net.SocketInputStream.read(SocketInputStream.java:141)
        at sun.nio.cs.StreamDecoder.readBytes(StreamDecoder.java:284)
        at sun.nio.cs.StreamDecoder.implRead(StreamDecoder.java:326)
        at sun.nio.cs.StreamDecoder.read(StreamDecoder.java:178)
        - locked <0x000000076b64fcc0> (a java.io.InputStreamReader)
        at java.io.InputStreamReader.read(InputStreamReader.java:184)
        at java.io.BufferedReader.fill(BufferedReader.java:161)
        at java.io.BufferedReader.readLine(BufferedReader.java:324)
        - locked <0x000000076b64fcc0> (a java.io.InputStreamReader)
        at java.io.BufferedReader.readLine(BufferedReader.java:389)
        at com.intellij.rt.execution.application.AppMainV2$1.run(AppMainV2.java:64)

        "Attach Listener" #5 daemon prio=5 os_prio=2 tid=0x000000001d553800 nid=0x31d8 waiting on condition [0x0000000000000000]
        java.lang.Thread.State: RUNNABLE

        "Signal Dispatcher" #4 daemon prio=9 os_prio=2 tid=0x000000001c1c3000 nid=0x40a0 runnable [0x0000000000000000]
        java.lang.Thread.State: RUNNABLE

        "Finalizer" #3 daemon prio=8 os_prio=1 tid=0x0000000002bc9800 nid=0x2584 in Object.wait() [0x000000001d4fe000]
        java.lang.Thread.State: WAITING (on object monitor)
        at java.lang.Object.wait(Native Method)
        - waiting on <0x000000076b388ee0> (a java.lang.ref.ReferenceQueue$Lock)
        at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:144)
        - locked <0x000000076b388ee0> (a java.lang.ref.ReferenceQueue$Lock)
        at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:165)
        at java.lang.ref.Finalizer$FinalizerThread.run(Finalizer.java:216)

        "Reference Handler" #2 daemon prio=10 os_prio=2 tid=0x0000000002bc6800 nid=0x4708 in Object.wait() [0x000000001d3ff000]
        java.lang.Thread.State: WAITING (on object monitor)
        at java.lang.Object.wait(Native Method)
        - waiting on <0x000000076b386c00> (a java.lang.ref.Reference$Lock)
        at java.lang.Object.wait(Object.java:502)
        at java.lang.ref.Reference.tryHandlePending(Reference.java:191)
        - locked <0x000000076b386c00> (a java.lang.ref.Reference$Lock)
        at java.lang.ref.Reference$ReferenceHandler.run(Reference.java:153)

        "VM Thread" os_prio=2 tid=0x000000001c177800 nid=0x4468 runnable

        "GC task thread#0 (ParallelGC)" os_prio=0 tid=0x0000000002aeb000 nid=0x13d0 runnable

        "GC task thread#1 (ParallelGC)" os_prio=0 tid=0x0000000002aed000 nid=0x2ad4 runnable

        "GC task thread#2 (ParallelGC)" os_prio=0 tid=0x0000000002aee800 nid=0x358 runnable

        "GC task thread#3 (ParallelGC)" os_prio=0 tid=0x0000000002af1000 nid=0x2a40 runnable

        "VM Periodic Task Thread" os_prio=2 tid=0x000000001d856800 nid=0x4edc waiting on condition

        JNI global references: 317
 */
