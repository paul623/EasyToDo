package com.paul.easytodo.Manager;

import android.content.Context;
import android.util.Log;

import com.paul.easytodo.DataSource.Goal;

import org.litepal.LitePal;

import java.util.List;

public class GoalHelper {

    public static void deleteGaol(String name,boolean ischecked){
        List<Goal> list=LitePal.findAll(Goal.class);
        for(Goal item:list){
            if(item.getName().equals(name)&&item.isChecked()==ischecked){
                item.delete();
                return;
            }
        }

    }
    /**
     * 筛选出未完成的任务
     * */
    public static List<Goal> getNeedToDoGoals(Context context){
        //true为1 false为0
        LitePal.initialize(context);
        return LitePal.where("isChecked=?","0").find(Goal.class);
    }
}
