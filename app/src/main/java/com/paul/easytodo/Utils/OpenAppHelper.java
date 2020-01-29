package com.paul.easytodo.Utils;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;

import es.dmoral.toasty.Toasty;

public class OpenAppHelper {
    static String forst_appName="cc.forestapp";
    static String rememberwords_appName="cn.com.langeasy.LangEasyLexis";
    static String onenote_appName="com.chenupt.day";
    static String love_appName="cn.xuexi.android";
    private static boolean openAppByName(Context context,String packageName){
        if(isAppInstalled(packageName)){
            Intent LaunchIntent = context.getPackageManager().getLaunchIntentForPackage(packageName);
            context.startActivity(LaunchIntent);
            return true;
        }else {
            return false;
        }

    }
    /**
     * 应用是否安装
     */
    public static boolean isAppInstalled(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }

    public static void openForest(Context context){
        boolean flag=openAppByName(context,forst_appName);
        if(!flag){
            Toasty.error(context,"您还没有安装Forest哦~",Toasty.LENGTH_SHORT).show();
        }
    }
    public static void openWords(Context context){
        boolean flag=openAppByName(context,rememberwords_appName);
        if(!flag){
            Toasty.error(context,"您还没有安装不背单词哦~",Toasty.LENGTH_SHORT).show();
        }
    }
    public static void openDiary(Context context){
        boolean flag=openAppByName(context,onenote_appName);
        if(!flag){
            Toasty.error(context,"您还没有安装一本日记哦~",Toasty.LENGTH_SHORT).show();
        }
    }
    public static void openLove(Context context){
        boolean flag=openAppByName(context,love_appName);
        if(!flag){
            Toasty.error(context,"您还没有安装学习强国哦~",Toasty.LENGTH_SHORT).show();
        }
    }

}
