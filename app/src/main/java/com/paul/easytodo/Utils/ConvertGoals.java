package com.paul.easytodo.Utils;

import android.content.Context;
import android.util.Log;

import com.paul.easytodo.DataSource.Goal;
import com.yalantis.beamazingtoday.interfaces.BatModel;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class ConvertGoals {

    public static List<BatModel> getBatGoals(Context context){
        LitePal.initialize(context);
        List<Goal> goalList=LitePal.findAll(Goal.class);
        List<BatModel> batModels=new ArrayList<>();
        batModels.addAll(goalList);
        return batModels;
    }

    public static void changeSatusGoalsByGoalMode(BatModel model,Context context){
        LitePal.initialize(context);
        List<Goal> goalList=LitePal.findAll(Goal.class);
        for(Goal i:goalList){
            if(i.getName().equals(model.getText())&&i.isChecked()==model.isChecked()){
                i.setChecked(!model.isChecked());
                i.setFinshDate(DateUtil.getCurTimeAndDate());
                i.save();
                return;
            }
        }
    }
}
