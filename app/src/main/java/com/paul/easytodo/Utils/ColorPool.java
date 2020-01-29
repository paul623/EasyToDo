package com.paul.easytodo.Utils;

import android.graphics.Color;

import com.paul.easytodo.R;

import java.util.PropertyResourceBundle;
import java.util.Random;

public class ColorPool {
    private static String[] colors={"#A3DE83","#FA4659","#C9EFF9","#9896F1","#D59BF6","#EDB1F1","#EB89B5"};
    public static int getRandomColor(){
        Random random=new Random();
        return Color.parseColor(colors[random.nextInt(colors.length)]);
    }
}
