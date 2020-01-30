package com.paul.easytodo.Manager;


import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kongzue.dialog.interfaces.OnDialogButtonClickListener;
import com.kongzue.dialog.util.BaseDialog;
import com.kongzue.dialog.v3.MessageDialog;
import com.paul.easytodo.DataSource.EveryDayCheck;
import com.paul.easytodo.DataSource.Goal;
import com.paul.easytodo.Fragment.SettingFragment;
import com.paul.easytodo.R;
import com.thegrizzlylabs.sardineandroid.Sardine;
import com.thegrizzlylabs.sardineandroid.impl.OkHttpSardine;

import org.litepal.LitePal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * WebDav管理器
 * */
public class SyncManager {
    String serverHostUrl;
    String userName;
    String password;
    Sardine sardine;
    Context context;
    SettingManager settingManager;
    /**
     * 构造函数
     * @param context 上下文
     * */
    public SyncManager(Context context){
        this.context=context;
        settingManager=new SettingManager(context);
        serverHostUrl="https://dav.jianguoyun.com/dav/";
        userName=settingManager.getUsername();
        password=settingManager.getPassword();
        sardine=new OkHttpSardine();
    }
    /**
     * 更新Goal
     * @param handler 1成功 -1失败
     * */
    private void updateGoalFiles(List<Goal> goals, Handler handler){
        sardine.setCredentials(userName,password);
        Gson gson=new Gson();
        String jsons=gson.toJson(goals);
        byte[] data=jsons.getBytes();
        try {
            if(!sardine.exists(serverHostUrl+"EasyToDo/")){
                sardine.createDirectory(serverHostUrl+"EasyToDo/");
            }
            sardine.put(serverHostUrl+"EasyToDo/backupGoals.txt",data);
            Message message=new Message();
            message.what=1;
            handler.sendMessage(message);
        } catch (IOException e) {
            Message message=new Message();
            message.what=-1;
            handler.sendMessage(message);
            Log.d("啥情况",e.toString());
            e.printStackTrace();
        }
    }
    /**
     * 更新EveryDayCheck
     * @param handler 1成功 -1失败
     * */
    private void updateEveryDayCheckFiles(List<EveryDayCheck> everyDayChecks, Handler handler){
        sardine.setCredentials(userName,password);
        Gson gson=new Gson();
        String jsons=gson.toJson(everyDayChecks);
        byte[] data=jsons.getBytes();
        try {
            if(!sardine.exists(serverHostUrl+"EasyToDo/")){
                sardine.createDirectory(serverHostUrl+"EasyToDo/");
            }
            sardine.put(serverHostUrl+"EasyToDo/backupEveryDayCheck.txt",data);
            Message message=new Message();
            message.what=1;
            handler.sendMessage(message);
        } catch (IOException e) {
            Message message=new Message();
            message.what=-1;
            handler.sendMessage(message);
            Log.d("啥情况",e.toString());
            e.printStackTrace();
        }
    }
    /**
     * 更新EveryDayCheck
     * @param handler 回调
     * */
    public void upDateEveryDayCheck(final Handler handler){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    updateEveryDayCheckFiles(EveryDayCheckManager.getAll(context),handler);
                }
            }).start();
    }
    /**
     * 更新Goal
     * @param handler 回调
     * */
    public void upDateGoals(final Handler handler){
        new Thread(new Runnable() {
            @Override
            public void run() {
                updateGoalFiles(GoalHelper.getAll(context),handler);
            }
        }).start();
    }
    /**
     * 获取云端存储的Goal
     * @param handler 2成功 -2失败
     * */
    public void getCloudGoalFiles(final Handler handler){
        if(userName.equals("")||password.equals("")){
            Toasty.info(context,"请设置账号",Toasty.LENGTH_SHORT).show();
            Intent intent=new Intent(context, SettingFragment.class);
            context.startActivity(intent);
        }else {
            Toasty.info(context,"恢复中",Toasty.LENGTH_SHORT).show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    getGoalFiles(handler);
                }
            }).start();
        }
    }
    /**
     * 获取云端存储的EveryDayCheck
     * @param handler 2成功 -2失败
     * */
    public void getCloudEveryDayCheckFiles(final Handler handler){
        if(userName.equals("")||password.equals("")){
            Toasty.info(context,"请设置账号",Toasty.LENGTH_SHORT).show();
            Intent intent=new Intent(context, SettingFragment.class);
            context.startActivity(intent);
        }else {
            Toasty.info(context,"恢复中",Toasty.LENGTH_SHORT).show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    getGoalFiles(handler);
                }
            }).start();
        }
    }
    /**
     * 获取云端存储的Goal
     * 特别注意，Gson自动生成的对象无法直接调用save保存
     * */
    private void getGoalFiles(Handler handler){
        sardine.setCredentials(userName,password);
        try {
            InputStream inputStream=sardine.get(serverHostUrl+"EasyToDo/backupGoals.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8")); // 实例化输入流，并获取网页代
            String s; // 依次循环，至到读的值为空
            StringBuilder sb = new StringBuilder();
            while ((s = reader.readLine()) != null) {
                sb.append(s);
            }
            reader.close();
            String str = sb.toString();
            Gson gson=new Gson();
            List<Goal> list=gson.fromJson(str,new TypeToken<List<Goal>>() {}.getType());
            LitePal.initialize(context);
            LitePal.deleteAll(Goal.class);
            for(Goal goal:list){
                Goal item=new Goal();
                item.setChecked(goal.isChecked());
                item.setFinshDate(goal.getFinshDate());
                item.setName(goal.getName());
                item.setSetDate(goal.getSetDate());
                item.save();
            }
            Message message=new Message();
            message.what=2;
            handler.sendMessage(message);
        } catch (IOException e) {
            Message message=new Message();
            message.what=-2;
            handler.sendMessage(message);
            e.printStackTrace();
        }

    }
    /**
     * 获取云端存储的EveryDayCheck
     * 特别注意，Gson自动生成的对象无法直接调用save保存
     * */
    private void getEveryDayCheckFiles(Handler handler){
        sardine.setCredentials(userName,password);
        try {
            InputStream inputStream=sardine.get(serverHostUrl+"EasyToDo/backupEveryDayCheck.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8")); // 实例化输入流，并获取网页代
            String s; // 依次循环，至到读的值为空
            StringBuilder sb = new StringBuilder();
            while ((s = reader.readLine()) != null) {
                sb.append(s);
            }
            reader.close();
            String str = sb.toString();
            Gson gson=new Gson();
            List<EveryDayCheck> list=gson.fromJson(str,new TypeToken<List<EveryDayCheck>>() {}.getType());
            LitePal.initialize(context);
            LitePal.deleteAll(Goal.class);
            for(EveryDayCheck item:list){
               EveryDayCheck everyDayCheck=new EveryDayCheck();
               everyDayCheck.setFocous_time(item.getFocous_time());
               everyDayCheck.setWakeup_time(item.getWakeup_time());
               everyDayCheck.setSleep_time(item.getSleep_time());
               everyDayCheck.setDate(item.getDate());
               everyDayCheck.save();
            }
            Message message=new Message();
            message.what=2;
            handler.sendMessage(message);
        } catch (IOException e) {
            Message message=new Message();
            message.what=-2;
            handler.sendMessage(message);
            e.printStackTrace();
        }

    }


    public void updateAll(Handler handler){
        if(settingManager.canLogin()) {
            upDateEveryDayCheck(handler);
            upDateGoals(handler);
        }

    }
    public void getAll(Handler handler){
        if(settingManager.canLogin()) {
            getEveryDayCheckFiles(handler);
            getGoalFiles(handler);
        }

    }

}
