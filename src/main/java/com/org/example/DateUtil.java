package com.org.example;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static String nowDateStr() {
        Date date = new Date();
        System.out.println(date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss");
        String dateNowStr = sdf.format(date);
        return dateNowStr;
    }
}
