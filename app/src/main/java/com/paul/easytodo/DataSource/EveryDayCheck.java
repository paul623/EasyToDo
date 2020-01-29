package com.paul.easytodo.DataSource;

import com.paul.easytodo.Utils.DateUtil;

import org.litepal.crud.LitePalSupport;

public class EveryDayCheck extends LitePalSupport {
    //日期
    private String date;
    //起床时间
    private String wakeup_time;
    //睡觉时间
    private String sleep_time;
    //专注时间
    private String focous_time;

    public EveryDayCheck(){
        date= DateUtil.getCurDate();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWakeup_time() {
        return wakeup_time;
    }

    public void setWakeup_time(String wakeup_time) {
        this.wakeup_time = wakeup_time;
    }

    public String getSleep_time() {
        return sleep_time;
    }

    public void setSleep_time(String sleep_time) {
        this.sleep_time = sleep_time;
    }

    public String getFocous_time() {
        return focous_time;
    }

    public void setFocous_time(String focous_time) {
        this.focous_time = focous_time;
    }
    public boolean isWakeupTimeNull(){
        if(wakeup_time==null||wakeup_time.equals("")){
            return true;
        }else {
            return false;
        }
    }
    public boolean isSleepTimeNull(){
        if(sleep_time==null||sleep_time.equals("")){
            return true;
        }else {
            return false;
        }
    }
    public boolean isFocusTimeNull(){
        if(focous_time==null||focous_time.equals("")){
            return true;
        }else {
            return false;
        }
    }
}
