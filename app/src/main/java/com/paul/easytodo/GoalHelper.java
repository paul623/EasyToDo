package com.paul.easytodo;

import android.util.Log;

import org.litepal.LitePal;

import java.util.List;

public class GoalHelper {

    public static void deleteGaol(String name,boolean ischecked){
        Log.d("测试",name+" "+ischecked);
        List<Goal> list=LitePal.findAll(Goal.class);
        for(Goal item:list){
            Log.d("测试","查找 "+item.getName()+item.isChecked());
            if(item.getName().equals(name)&&item.isChecked()==ischecked){
                item.delete();
                return;
            }
        }
    }
}
