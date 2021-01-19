package com.gupao.local;

public class App {

    private static volatile Object[] objects = new Object[10];

    public static void main(String[] args) {
        objects[0]=1; //Lock指令
        objects = new Object[2];

    }

}
