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
    //头像设置
    String homepage_headicon_imgdir;
    String key_homepage_headicon_imgdir="key_homepage_headicon_imgdir";
    //WebDav账户
    String username;
    String key_username="key_username";
    String password;
    String key_password="key_password";


    public SettingManager(Context context){
        this.context=context;
        sp=context.getSharedPreferences(dataBaseName,Context.MODE_PRIVATE);
        editor=sp.edit();
        //开始初始化各种变量
        homepage_bg_imgdir=sp.getString(key_homepage_bg_imgdir,"");
        username=sp.getString(key_username,"");
        password=sp.getString(key_password,"");
        homepage_headicon_imgdir=sp.getString(key_homepage_headicon_imgdir,"");
    }

    public Drawable getHomepage_bg_img() {
        Drawable drawable=null;
        if(homepage_bg_imgdir==null||homepage_bg_imgdir.equals("")){
            drawable = ContextCompat.getDrawable(context, R.drawable.bg_default);
        }else {
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        editor.putString(key_username,username);
        editor.apply();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        editor.putString(key_password,password);
        editor.apply();
    }
    public boolean canLogin(){
        if(username==null||username.equals("")){
            return false;
        }
        if(password==null||password.equals("")){
            return false;
        }
        return true;
    }
    public Bitmap getHomepage_headicon_img(){
        if(homepage_headicon_imgdir==null||homepage_headicon_imgdir.equals("")){
            return BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_head);
        }
        return BitmapFactory.decodeFile(homepage_headicon_imgdir);
    }
    public String getHomepage_headicon_imgdir() {
        return homepage_headicon_imgdir;
    }

    public void setHomepage_headicon_imgdir(String homepage_headicon_imgdir) {
        this.homepage_headicon_imgdir = homepage_headicon_imgdir;
        editor.putString(key_homepage_headicon_imgdir,homepage_headicon_imgdir);
        editor.apply();
    }
}
