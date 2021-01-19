package com.org.example;

import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;

/**
 * 风骚的Mic 老师
 * create-date: 2020/5/17-20:46
 */
public class ClassLayoutDemo {

    public static void main(String[] args) {
        ClassLayoutDemo classLayoutDemo=new ClassLayoutDemo();
        synchronized (classLayoutDemo){
            System.out.println("get synchronized lock");
            System.out.println(ClassLayout.parseInstance(classLayoutDemo).toPrintable());
        }

    }

    @Test
    public void testPrintLayout()
    {
        ClassLayoutDemo classLayoutDemo=new ClassLayoutDemo();
        synchronized (classLayoutDemo){
            System.out.println("locking");
            System.out.println(ClassLayout.parseInstance(classLayoutDemo).toPrintable());
        }

        /**
         *org.example.ClassLayoutDemo object internals:
         *  OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
         *       0     4        (object header)                           d8 e3 71 02 (11011000 11100011 01110001 00000010) (41018328)
         *       4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
         *       8     4        (object header)                           2b c9 00 f8 (00101011 11001001 00000000 11111000) (-134166229)
         *      12     4        (loss due to the next object alignment)
         * Instance size: 16 bytes
         * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total  -->  这里有4个字节丢失了，16-4=12字节（12*8 = 96位）
         * 也可以从行数可以看出最后一行丢失了，所以只剩下3行：3*32=96位<br>
         */
        /**
         * 只要看前面64位，比如这里：
         *  0     4        (object header)                           d8 e3 71 02 (11011000 11100011 01110001 00000010) (41018328)
         *  4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
         *  那么它的排列方式是：
         *  00 00 00 00 --> d8 e3 71 02 (为什么这么排列涉及到大端存储小端存储) --> 这个排列方式要改一下
         */
    }

    @Test
    public void testPrintLayoutUseCompress()
    {
        ClassLayoutDemo classLayoutDemo=new ClassLayoutDemo();
        synchronized (classLayoutDemo){
            System.out.println("locking");
            System.out.println(ClassLayout.parseInstance(classLayoutDemo).toPrintable());
        }

        /**
         * org.example.ClassLayoutDemo object internals:
         *  OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
         *       0     4        (object header)                           c8 e1 ea 02 (11001000 11100001 11101010 00000010) (48947656)
         *       4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
         *       8     4        (object header)                           c8 36 1b 1c (11001000 00110110 00011011 00011100) (471545544)
         *      12     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
         * Instance size: 16 bytes
         * Space losses: 0 bytes internal + 0 bytes external = 0 bytes total
         */
    }

}
