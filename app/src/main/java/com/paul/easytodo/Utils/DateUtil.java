package com.paul.easytodo.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static String getCurDate(){
        Date date=new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        return f.format(date);
    }
    /**
     * 根据当前时间判断时间段
     * 0为凌晨
     * 1为上午
     * 以此类推
     * */
    public static int getCurTimePart(){
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("HH");
        String str = df.format(date);
        int a = Integer.parseInt(str);
        if (a >= 0 && a <= 6) {
            //System.out.println("凌晨");
            return 0;
        }else if (a > 6 && a <= 12) {
            //System.out.println("上午");
            return 1;
        }else if (a > 12 && a <= 13) {
            //System.out.println("中午");
            return 2;
        }else if (a > 13 && a <= 18) {
            //System.out.println("下午");
            return 3;
        }else {
            //System.out.println("晚上");
            return 4;
        }
    }
    /**
     * 获得当前日期 HH:mm
     * @return 19:47
     */
    public static String getCurrentTime() {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        return df.format(date);
    }
    /**
     * 获取当前是星期几
     * @return 周几
     * */
    public static String getCurWeek(){
        String week = "";
        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        int weekday = c.get(Calendar.DAY_OF_WEEK);
        if (weekday == 1) {
            week = "周日";
        } else if (weekday == 2) {
            week = "周一";
        } else if (weekday == 3) {
            week = "周二";
        } else if (weekday == 4) {
            week = "周三";
        } else if (weekday == 5) {
            week = "周四";
        } else if (weekday == 6) {
            week = "周五";
        } else if (weekday == 7) {
            week = "周六";
        }
        return week;
    }

    public static String getDifferDays(){
        String goal_date="2020-12-19";
        Calendar a = Calendar.getInstance(),
                b = Calendar.getInstance();
        String[] splitdate = goal_date.split("-");
        b.set(Integer.parseInt(splitdate[0]), Integer.parseInt(splitdate[1]) - 1, Integer.parseInt(splitdate[2]));
        long diffDays = (b.getTimeInMillis() - a.getTimeInMillis())
                / (1000 * 60 * 60 * 24);
        return diffDays+"";

    }

    public static String getCurTimeAndDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date();
        return df.format(date);
    }
}
