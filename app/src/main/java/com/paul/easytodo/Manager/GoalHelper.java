package com.paul.easytodo.Manager;

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
}
