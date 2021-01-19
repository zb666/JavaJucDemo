package com.org.example;

import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;

/**
 * 风骚的Mic 老师
 * create-date: 2020/5/17-20:46
 */
public class ClassLayoutInLineNoSyncDemo {

    @Test
    public void testPrintLayout()
    {
        ClassLayoutInLineNoSyncDemo classLayoutDemo=new ClassLayoutInLineNoSyncDemo();
//        synchronized (classLayoutDemo){
            System.out.println("locking");
            System.out.println(ClassLayout.parseInstance(classLayoutDemo).toPrintable());
//        }

        /**
         *org.example.ClassLayoutInLineNoSyncDemo object internals:
         *  OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
         *       0     4        (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)
         *       4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
         *       8     4        (object header)                           2b c9 00 f8 (00101011 11001001 00000000 11111000) (-134166229)
         *      12     4        (loss due to the next object alignment)
         * Instance size: 16 bytes
         * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
         * 如果
         */
    }
}
