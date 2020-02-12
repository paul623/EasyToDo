package com.paul.easytodo.Utils;

import android.os.Handler;
import android.os.Message;


import java.util.Timer;
import java.util.TimerTask;

public class TimeCounter {
    Timer timer;
    TimerTask timerTask;
    public Handler handler;


    public TimeCounter(final Handler handler){
        this.handler=handler;


    }

    public void start(){
        timer=new Timer();
        //声明定时任务
        timerTask = new TimerTask() {
            @Override
            public void run() {
                Message msg = new Message();
                msg.what=1;
                handler.sendMessage(msg);
            }
        };
        //启动定时器
        timer.schedule(timerTask, 1000, 1000);
    }

    public void stop(){
        timer.cancel();
        timer=null;
        timerTask.cancel();
        timerTask=null;
    }

}
