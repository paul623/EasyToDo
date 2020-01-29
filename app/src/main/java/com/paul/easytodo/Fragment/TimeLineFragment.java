package com.paul.easytodo.Fragment;

import android.graphics.Color;

import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.kongzue.baseframework.BaseFragment;
import com.kongzue.baseframework.interfaces.BindView;
import com.kongzue.baseframework.interfaces.Layout;
import com.kongzue.dialog.interfaces.OnInputDialogButtonClickListener;
import com.kongzue.dialog.util.BaseDialog;
import com.kongzue.dialog.util.DialogSettings;
import com.kongzue.dialog.util.InputInfo;
import com.kongzue.dialog.util.TextInfo;
import com.kongzue.dialog.v3.InputDialog;
import com.paul.easytodo.Adapter.ToDoListAdapter;
import com.paul.easytodo.DataSource.EveryDayCheck;
import com.paul.easytodo.DataSource.Goal;

import com.paul.easytodo.MainActivity;
import com.paul.easytodo.Manager.EveryDayCheckManager;
import com.paul.easytodo.Manager.GoalHelper;
import com.paul.easytodo.R;
import com.paul.easytodo.Utils.DateUtil;
import com.paul.easytodo.Utils.OpenAppHelper;
import com.paul.easytodo.Utils.WordsUtil;


import org.litepal.LitePal;

import java.util.List;


import es.dmoral.toasty.Toasty;

@Layout(R.layout.fragment_timeline)
public class TimeLineFragment extends BaseFragment<MainActivity> {
    @BindView(R.id.tv_comeon_words)
    private TextView tv_comeon_words;
    @BindView(R.id.tv_head_date)
    private TextView tv_head_date;
    @BindView(R.id.tv_head_wakeup)
    private TextView tv_head_wakeup;
    @BindView(R.id.tv_head_sleep)
    private TextView tv_head_sleep;
    @BindView(R.id.btn_head_wakeup)
    private Button btn_head_wakeup;
    @BindView(R.id.btn_head_sleep)
    private Button btn_head_sleep;
    @BindView(R.id.cv_focus_time)
    private CardView cv_focus_time;
    @BindView(R.id.tv_focus_time)
    private TextView tv_focus_time;
    @BindView(R.id.tv_todo_size)
    private TextView tv_todo_size;
    @BindView(R.id.tv_timecounter_time)
    private TextView tv_timecounter_time;
    @BindView(R.id.lv_todo)
    private ListView lv_todo;
    @BindView(R.id.ly_todolist)
    private LinearLayout ly_todolist;
    @BindView(R.id.cv_todo)
    private CardView cv_todo;
    @BindView(R.id.iv_forest)
    private ImageView iv_forest;
    @BindView(R.id.iv_diary)
    private ImageView iv_diary;
    @BindView(R.id.iv_words)
    private ImageView iv_words;
    @BindView(R.id.iv_love)
    private ImageView iv_love;

    private EveryDayCheck everyDayCheck;
    private List<Goal> goalList;
    private ToDoListAdapter toDoListAdapter;
    private DataChangedListener listener;
    private Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    Toasty.success(me,"完成",Toasty.LENGTH_SHORT).show();
                    refreashToDoData();
                    listener.onToDoDataChanged();
                    break;
            }
            return false;
        }
    });

    @Override
    public void initViews() {
        DialogSettings.style= DialogSettings.STYLE.STYLE_IOS;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            Window window = me.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.setStatusBarColor(getResources().getColor(R.color.blue));
        }
    }

    @Override
    public void initDatas() {
        LitePal.initialize(me);
        goalList= GoalHelper.getNeedToDoGoals(me);
        everyDayCheck= EveryDayCheckManager.getEveryDayCheck(me);
        tv_comeon_words.setText(WordsUtil.getWordsByRandom(me));
        toDoListAdapter=new ToDoListAdapter(me,handler);
        initHeadView();
        initColorCardView();
        lv_todo.setAdapter(toDoListAdapter);
    }

    @Override
    public void setEvents() {
        initImageView();
    }

    private void initHeadView(){
        tv_head_date.setText(DateUtil.getCurDate()+"\n"+DateUtil.getCurWeek());
        if(everyDayCheck.isSleepTimeNull()){
            tv_head_sleep.setText("请打卡");
        }else {
            tv_head_sleep.setText(everyDayCheck.getSleep_time());
        }
        if(everyDayCheck.isWakeupTimeNull()){
            tv_head_wakeup.setText("请打卡");
        }else {
            tv_head_wakeup.setText(everyDayCheck.getWakeup_time());
        }
        btn_head_sleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DateUtil.getCurTimePart()!=4){
                    Toasty.info(me,"大白天睡啥觉哦！",Toasty.LENGTH_SHORT).show();
                }else {
                    tv_head_sleep.setText(DateUtil.getCurrentTime());
                    everyDayCheck.setSleep_time(DateUtil.getCurrentTime());
                    everyDayCheck.save();
                    Toasty.success(me,"打卡成功，好梦~",Toasty.LENGTH_SHORT).show();
                }

            }
        });
        btn_head_wakeup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DateUtil.getCurTimePart()>=2){
                    Toasty.info(me,"我勒个去,你这起床时间逆天了吧···",Toasty.LENGTH_SHORT).show();

                }
                if(DateUtil.getCurTimePart()==0){
                    Toasty.info(me,"亲，多睡会吧，这才几点啊！",Toasty.LENGTH_SHORT).show();
                }
                tv_head_wakeup.setText(DateUtil.getCurrentTime());
                everyDayCheck.setWakeup_time(DateUtil.getCurrentTime());
                everyDayCheck.save();
                Toasty.success(me,"早安!",Toasty.LENGTH_SHORT).show();
            }
        });
    }
    private void initColorCardView(){
        tv_timecounter_time.setText(DateUtil.getDifferDays());
        refreashToDoData();
        if(everyDayCheck.isFocusTimeNull()){
            tv_focus_time.setText("0");
        }else {
            tv_focus_time.setText(everyDayCheck.getFocous_time());
        }
        cv_focus_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputDialog.show(me, "设置今日专注时长", "请输入数字", "确定")
                        .setInputInfo(new InputInfo()
                                .setMAX_LENGTH(2)     //限制最大输入长度
                                .setInputType(InputType.TYPE_CLASS_NUMBER)     //仅输入密码类型
                                .setTextInfo(new TextInfo()       //设置文字样式
                                        .setFontColor(Color.BLACK)     //修改文字样式颜色为红色
                                )
                                .setMultipleLines(true)       //支持多行输入
                        )
                        .setOnOkButtonClickListener(new OnInputDialogButtonClickListener() {
                            @Override
                            public boolean onClick(BaseDialog baseDialog, View v, String inputStr) {
                                everyDayCheck.setFocous_time(inputStr);
                                everyDayCheck.save();
                                tv_focus_time.setText(inputStr);
                                return false;
                            }
                        })
                ;
            }
        });
        cv_todo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jump(1);
            }
        });

    }
    private void refreashToDoData(){
        goalList= GoalHelper.getNeedToDoGoals(me);
        tv_todo_size.setText(""+goalList.size());
        if(goalList.size()==0){
            ly_todolist.setVisibility(View.GONE);
        }else {
            ly_todolist.setVisibility(View.VISIBLE);
        }
    }
    public void refreashToDoView(){
        refreashToDoData();
        toDoListAdapter.refreash();
    }

    public interface DataChangedListener{
        public void onToDoDataChanged();
    }
    public void setOnDataChangedListener(DataChangedListener listener){
        this.listener=listener;
    }
    public void initImageView(){
        iv_forest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenAppHelper.openForest(me);
            }
        });
        iv_diary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenAppHelper.openDiary(me);
            }
        });
        iv_love.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenAppHelper.openLove(me);
            }
        });
        iv_words.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenAppHelper.openWords(me);
            }
        });

    }
}
