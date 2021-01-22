package com.org.example.volatiledemo;

public class IntepreterVolatileDemo {
   //自己要尝试一下加volatile与不加volatile的区别，加了有个lock指令<br>

    private static volatile boolean isStop = false;

    void testVar(){
        isStop = true;
    }
}
