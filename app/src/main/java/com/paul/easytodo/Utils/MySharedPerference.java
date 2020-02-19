package com.paul.easytodo.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MySharedPerference {
    Context context;
    String DataName;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    public MySharedPerference(Context context,String DataName){
        this.context=context;
        this.DataName=DataName;
        sp=context.getSharedPreferences(DataName,Context.MODE_PRIVATE);
        editor=sp.edit();
    }
    public String getString(String key){
        return sp.getString(key,"");
    }
    public void putString(String key,String value){
        editor.putString(key,value);
        editor.apply();
    }
    public <T> T getObject(String key,Class<T> classOfT){
        String value=sp.getString(key,"");
        Gson gson=new Gson();
        return gson.fromJson(value,classOfT);
    }
    public void putObject(String key,Object o){
        Gson gson=new Gson();
        String value=gson.toJson(o);
        editor.putString(key,value);
        editor.apply();
    }
}
