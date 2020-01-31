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
    Handler handler;
    SettingManager settingManager;
    /**
     * 构造函数
     * @param context 上下文
     * */
    public SyncManager(Context context,Handler handler){
        this.handler=handler;
        this.context=context;
        settingManager=new SettingManager(context);
        serverHostUrl="https://dav.jianguoyun.com/dav/";
        userName=settingManager.getUsername();
        password=settingManager.getPassword();
        sardine=new OkHttpSardine();
    }
    /**
     * 更新Goal
     * handler 1成功 -1失败
     * */
    private void updateGoalFiles(List<Goal> goals){
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
     *handler 1成功 -1失败
     * */
    private void updateEveryDayCheckFiles(List<EveryDayCheck> everyDayChecks){
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
     * handler 回调
     * */
    private void upDateEveryDayCheck(){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    updateEveryDayCheckFiles(EveryDayCheckManager.getAll(context));
                }
            }).start();
    }
    /**
     * 更新Goal
     * handler 回调
     * */
    private void upDateGoals(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                updateGoalFiles(GoalHelper.getAll(context));
            }
        }).start();
    }
    /**
     * 获取云端存储的Goal
     *handler 2成功 -2失败
     * */
    private void getCloudGoalFiles(){
        Toasty.info(context,"恢复中",Toasty.LENGTH_SHORT).show();
        new Thread(new Runnable() {
                @Override
                public void run() {
                    getGoalFiles();
                }
            }).start();
    }
    /**
     * 获取云端存储的EveryDayCheck
     * handler 2成功 -2失败
     * */
    private void getCloudEveryDayCheckFiles(){
        Toasty.info(context,"恢复中",Toasty.LENGTH_SHORT).show();
        new Thread(new Runnable() {
                @Override
                public void run() {
                    getGoalFiles();
                }
            }).start();

    }
    /**
     * 获取云端存储的Goal
     * 特别注意，Gson自动生成的对象无法直接调用save保存
     * */
    private void getGoalFiles(){
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
            Log.d("测试","总共获取Goal"+list.size()+
                    "个");
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
    private void getEveryDayCheckFiles(){
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

    /**
     * 更新
     * */
    public void updateAll(){
        if(settingManager.canLogin()) {
            upDateEveryDayCheck();
            upDateGoals();
        }

    }
    /**
     * 下拉
     * */
    public void getAll(){
        if(settingManager.canLogin()) {
            Log.d("测试","准备恢复");
            getCloudGoalFiles();
            getCloudEveryDayCheckFiles();
        }

    }

}
