package com.paul.easytodo.Fragment;

import android.graphics.Color;
import android.os.Build;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
import com.paul.easytodo.DataSource.EveryDayCheck;
import com.paul.easytodo.DataSource.Goal;
import com.paul.easytodo.MainActivity;
import com.paul.easytodo.Manager.EveryDayCheckManager;
import com.paul.easytodo.R;
import com.paul.easytodo.Utils.DateUtil;
import com.paul.easytodo.Utils.WordsUtil;

import org.litepal.LitePal;
import org.w3c.dom.Text;

import java.util.List;
import java.util.logging.Level;

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


    private EveryDayCheck everyDayCheck;
    private List<Goal> goalList;
    @Override
    public void initViews() {
        DialogSettings.style= DialogSettings.STYLE.STYLE_IOS;
    }

    @Override
    public void initDatas() {
        LitePal.initialize(me);
        goalList=LitePal.findAll(Goal.class);
        everyDayCheck= EveryDayCheckManager.getEveryDayCheck(me);
        tv_comeon_words.setText(WordsUtil.getWordsByRandom(me));
        initHeadView();
        initColorCardView();
    }

    @Override
    public void setEvents() {

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
        tv_todo_size.setText(""+goalList.size());
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

    }
}
