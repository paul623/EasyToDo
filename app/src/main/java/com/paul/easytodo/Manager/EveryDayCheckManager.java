package com.paul.easytodo.Manager;

import android.content.Context;

import com.paul.easytodo.DataSource.EveryDayCheck;
import com.paul.easytodo.Utils.DateUtil;

import org.litepal.LitePal;

import java.util.List;

public class EveryDayCheckManager {
    /**
     * 获取今日打卡
     * */
    public static EveryDayCheck getEveryDayCheck(Context context){
        LitePal.initialize(context);
        String curDate= DateUtil.getCurDate();
        List<EveryDayCheck> everyDayChecks=LitePal.where("date=?",curDate).find(EveryDayCheck.class);
        if(everyDayChecks.size()==1){
            return everyDayChecks.get(0);
        }else {
            return new EveryDayCheck();
        }
    }
    /**
     * 获取所有
     * */
    public static List<EveryDayCheck> getAll(Context context){
        LitePal.initialize(context);
        return LitePal.findAll(EveryDayCheck.class);
    }
}
