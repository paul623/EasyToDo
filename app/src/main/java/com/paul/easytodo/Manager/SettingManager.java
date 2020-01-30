package com.paul.easytodo.Manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.paul.easytodo.R;
import com.paul.easytodo.Utils.ImageUtil;

import java.util.concurrent.CopyOnWriteArrayList;

public class SettingManager {
    /**
     * 设置管理器
     * */
    Context context;
    String dataBaseName="SettingDataBase";
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    //背景设置
    String homepage_bg_imgdir;
    String key_homepage_bg_imgdir="key_homepage_bg_imgdir";

    public SettingManager(Context context){
        this.context=context;
        sp=context.getSharedPreferences(dataBaseName,Context.MODE_PRIVATE);
        editor=sp.edit();
        //开始初始化各种变量
        homepage_bg_imgdir=sp.getString(key_homepage_bg_imgdir,"");
    }

    public Drawable getHomepage_bg_img() {
        Drawable drawable=null;
        if(homepage_bg_imgdir==null||homepage_bg_imgdir.equals("")){
            drawable = ContextCompat.getDrawable(context, R.drawable.bg_default);
        }else {
            Log.d("测试",homepage_bg_imgdir);
            Bitmap bitmap = ImageUtil.getBitmapByPath(context,homepage_bg_imgdir);
            drawable = new BitmapDrawable(context.getResources(), bitmap);
        }
        return drawable;
    }

    public void setHomepage_bg_imgdir(String homepage_bg_imgdir) {
        this.homepage_bg_imgdir = homepage_bg_imgdir;
        editor.putString(key_homepage_bg_imgdir,homepage_bg_imgdir);
        editor.apply();
    }
}
