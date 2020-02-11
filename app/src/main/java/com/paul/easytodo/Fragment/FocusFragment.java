package com.paul.easytodo.Fragment;

import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.kongzue.baseframework.BaseFragment;
import com.kongzue.baseframework.interfaces.BindView;
import com.kongzue.baseframework.interfaces.DarkStatusBarTheme;
import com.kongzue.baseframework.interfaces.Layout;
import com.paul.easytodo.MainActivity;
import com.paul.easytodo.R;
import com.paul.easytodo.Utils.StatusBarUtils;
import com.paul.easytodo.Utils.TimeCounter;
import com.paul.easytodo.Widget.ClockProgressBar;
import com.paul.easytodo.Widget.RippleWrapper;

import org.greenrobot.eventbus.EventBus;

import java.beans.PropertyChangeEvent;
import java.util.Random;

@DarkStatusBarTheme(true)
@Layout(R.layout.fragment_focus)
public class FocusFragment extends BaseFragment<MainActivity> {

    @BindView(R.id.btn_start)
    private Button mBtnStart;
    @BindView(R.id.btn_stop)
    private Button mBtnStop;
    @BindView(R.id.text_count_down)
    private TextView mTextCountDown;
    @BindView(R.id.text_time_title)
    private TextView mTextTimeTile;
    @BindView(R.id.focus_hint)
    private TextView focus_tint;
    @BindView(R.id.tick_progress_bar)
    private ClockProgressBar mProgressBar;
    @BindView(R.id.ripple_wrapper)
    private RippleWrapper mRippleWrapper;
    @BindView(R.id.clock_bg)
    private ImageView clock_bg;
    @BindView(R.id.bt_music)
    private ImageButton bt_music;
    private static int[] imageArray = new int[]{R.drawable.ic_img2,
            R.drawable.ic_img3,
            R.drawable.ic_img4,
            R.drawable.ic_img5,
            R.drawable.ic_img6,
            R.drawable.ic_img7,
            R.drawable.ic_img8,
            R.drawable.ic_img9,
            R.drawable.ic_img10,
            R.drawable.ic_img11,
            R.drawable.ic_img12};

    private int MINUTE = 30;
    private int SECOND = 0;
    private int PROCRSS_TIME=0;
    private TimeCounter timeCounter;
    private Boolean flag=false;//暂停标志
    private Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    if(flag){

                    }else {
                        timeCount();
                    }
                    break;
            }
            return false;
        }
    });
    @Override
    public void initViews() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            Window window = me.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.setStatusBarColor(getResources().getColor(R.color.white));
        }
    }

    @Override
    public void initDatas() {
        Random random=new Random();
        timeCounter=new TimeCounter(handler);
        mTextTimeTile.setText("您今天已经专注3小时了");
        clock_bg.setImageResource(imageArray[random.nextInt(imageArray.length)]);
        setStatus(1);
        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeCounter.start();
                mRippleWrapper.start();
                setStatus(2);
            }
        });
        mBtnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeCounter.stop();
                setStatus(1);
                mRippleWrapper.stop();
            }
        });
    }

    @Override
    public void setEvents() {

    }
    private void timeCount(){
        if(MINUTE>=0){
            PROCRSS_TIME++;
            SECOND--;
            String minute = String.valueOf(MINUTE);
            String second = String.valueOf(SECOND);
            if(SECOND>=0){
                if(SECOND<10){
                    second = "0"+second;
                }
                if(MINUTE<10){
                    minute = "0"+minute;
                }
                mTextCountDown.setText(minute+":"+second);
            }else{
                SECOND = 60;
                MINUTE--;
            }
            mProgressBar.setProgress(PROCRSS_TIME);
        }else{
            mTextCountDown.setText("");
            timeCounter.stop();
            mRippleWrapper.stop();
            setStatus(1);
        }
    } //timeCount()

    private void setStatus(int status){
        switch (status){
            case 1:
                //未计时
                mTextCountDown.setText("30:00");
                mProgressBar.setMaxProgress(MINUTE*60000);
                mProgressBar.setProgress(0);
                MINUTE = 30;
                SECOND = 0;
                mBtnStop.setVisibility(View.GONE);
                mBtnStart.setVisibility(View.VISIBLE);
                break;
            case 2:
                mTextCountDown.setText("30:00");
                mTextTimeTile.setText("");
                mBtnStop.setVisibility(View.VISIBLE);
                mBtnStart.setVisibility(View.GONE);
                break;
        }
    }
}
