package com.paul.easytodo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static String getCurDate(){
        Date date=new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        return f.format(date);
    }
}
