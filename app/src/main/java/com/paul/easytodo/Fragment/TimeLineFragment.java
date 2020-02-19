package com.paul.easytodo.Fragment;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.paul.easytodo.Manager.SettingManager;
import com.paul.easytodo.R;
import com.paul.easytodo.RequestAPI.EatWhatAPI;
import com.paul.easytodo.RequestAPI.WordsAPI;
import com.paul.easytodo.Utils.DateUtil;
import com.paul.easytodo.Utils.MessageFactory;
import com.paul.easytodo.Utils.MySharedPerference;
import com.paul.easytodo.Utils.OpenAppHelper;
import com.paul.easytodo.Utils.RetrofitFactory;
import com.paul.easytodo.Utils.WordsUtil;
import com.paul.easytodo.domain.EatWhatResult;
import com.paul.easytodo.domain.WordsResult;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;


import org.litepal.LitePal;
import org.w3c.dom.Text;

import java.util.Date;
import java.util.List;


import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Layout(R.layout.fragment_timeline)
public class TimeLineFragment extends BaseFragment<MainActivity> {
    @BindView(R.id.tv_comeon_words)
    private TextView tv_comeon_words;
    @BindView(R.id.tv_comeon_author)
    private TextView tv_comeon_author;
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
    @BindView(R.id.ly_timeline)
    private LinearLayout ly_timeline;
    @BindView(R.id.profile_image)
    private CircleImageView profile_image;
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;
    private EveryDayCheck everyDayCheck;
    private List<Goal> goalList;
    private ToDoListAdapter toDoListAdapter;
    private DataChangedListener listener;
    private SettingManager settingManager;
    MySharedPerference mySharedPerference;
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
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            Window window = me.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.setStatusBarColor(getResources().getColor(R.color.blue));
        }*/

    }

    @Override
    public void initDatas() {
        LitePal.initialize(me);
        settingManager=new SettingManager(me);
        mySharedPerference=new MySharedPerference(me,"TimeLineFragment");
        goalList= GoalHelper.getNeedToDoGoals(me);
        everyDayCheck= EveryDayCheckManager.getEveryDayCheck(me);
        toDoListAdapter=new ToDoListAdapter(me,handler);
        ly_timeline.setBackground(settingManager.getHomepage_bg_img());
        profile_image.setImageBitmap(settingManager.getHomepage_headicon_img());
        initHeadView();
        initColorCardView();
        initListView();
        autoRefreash();

    }

    @Override
    public void setEvents() {
        initImageView();
        setRefreshLayout();
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
            TranslateAnimation hideAnim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                    Animation.RELATIVE_TO_SELF, 1.0f,
                    Animation.RELATIVE_TO_SELF, 0.0f,
                    Animation.RELATIVE_TO_SELF, 0.0f);
            hideAnim.setDuration(500);
            ly_todolist.startAnimation(hideAnim);
            ly_todolist.setVisibility(View.GONE);
        }else if(ly_todolist.getVisibility()==View.GONE){
            TranslateAnimation showAnim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1.0f,
                    Animation.RELATIVE_TO_SELF, 0.0f,
                    Animation.RELATIVE_TO_SELF, 0.0f,
                    Animation.RELATIVE_TO_SELF, 0.0f);
            showAnim.setDuration(500);
            ly_todolist.startAnimation(showAnim);
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
    public void initListView(){
        lv_todo.setAdapter(toDoListAdapter);
        //动画集合
        AnimationSet animationSet = new AnimationSet(true);
        //alpha动画
        Animation animation = new AlphaAnimation(0.0f,1.0f);
        animation.setDuration(1300);
        animationSet.addAnimation(animation);
        //位移动画 效果 从Y方向下落到自己的位置
        //RELATIVE_TO_SELF 相对自身
        //-1.0f 起始Y坐标为自身的高度
        //TranslateAnimation(int fromXType, float fromXValue,
        //                   int toXType,   float toXValue,
        //                   int fromYType, float fromYValue,
        //                   int toYType,   float toYValue)
        animation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF,0.0f,Animation.RELATIVE_TO_SELF,0.0f,
                Animation.RELATIVE_TO_SELF,-1.0f,Animation.RELATIVE_TO_SELF,0.0f);
        animation.setDuration(1300);
        animationSet.addAnimation(animation);
        //设置子视图动画及持续时间
        LayoutAnimationController controller = new LayoutAnimationController(animationSet,0.5f);
        //绑定到listview
        lv_todo.setLayoutAnimation(controller);
    }
    public void refreashHeadIcon(Bitmap bitmap){
        profile_image.setImageBitmap(bitmap);
    }
    public void refreashBack(Bitmap bitmap){
        ly_timeline.setBackground(new BitmapDrawable(me.getResources(), bitmap));
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        View view=getView();
        if (view != null) {
            if (hidden) {
                view.setFitsSystemWindows(false);
            } else {
                view.setFitsSystemWindows(true);
            }
            view.requestApplyInsets();
        }
        super.onHiddenChanged(hidden);
    }
    private void getWordsFromServer(){

            WordsAPI api= RetrofitFactory.getRetrofit().create(WordsAPI.class);
            Call<WordsResult> call=api.getTodayWords();
            call.enqueue(new Callback<WordsResult>() {
                @Override
                public void onResponse(Call<WordsResult> call, Response<WordsResult> response) {
                    WordsResult wordsResult=response.body();
                   if(wordsResult!=null){
                       setWords(wordsResult);
                       mySharedPerference.putObject("words",wordsResult);
                       refreshLayout.finishRefresh(true);
                   }

                }

                @Override
                public void onFailure(Call<WordsResult> call, Throwable t) {
                    tv_comeon_words.setText("风会记得一朵花的香");
                    tv_comeon_author.setText("paul");
                    refreshLayout.finishRefresh(false);
                }
            });


    }
    private void getEatWhatFromServer(){

            EatWhatAPI api=RetrofitFactory.getRetrofit().create(EatWhatAPI.class);
            Call<EatWhatResult> call=api.getEatWhat();
            call.enqueue(new Callback<EatWhatResult>() {
                @Override
                public void onResponse(Call<EatWhatResult> call, Response<EatWhatResult> response) {
                    EatWhatResult eatWhatResult=response.body();
                    if(eatWhatResult!=null){
                        setFood(eatWhatResult);
                        mySharedPerference.putObject("food",eatWhatResult);
                        refreshLayout.finishRefresh(true);
                    }

                }

                @Override
                public void onFailure(Call<EatWhatResult> call, Throwable t) {
                    refreshLayout.finishRefresh(false);
                }
            });

    }

    private void setFood(EatWhatResult eatWhatResult){
        if(eatWhatResult.getCode()==1) {
            int[] foodname = {R.id.tv_morning_food, R.id.tv_noon_food, R.id.tv_night_food};
            int[] locname = {R.id.tv_morning_loc, R.id.tv_noon_loc, R.id.tv_night_loc};
            TextView food, loc;
            for (int i = 0; i < foodname.length; i++) {
                food = findViewById(foodname[i]);
                loc = findViewById(locname[i]);
                food.setText(eatWhatResult.getDataBean().get(i).getFood());
                loc.setText(eatWhatResult.getDataBean().get(i).getLoc());
            }
            DateUtil.putRefreshTAG(me,"food_date");
        }
    }
    private void setWords(WordsResult wordsResult){
        tv_comeon_words.setText(wordsResult.getContent());
        tv_comeon_author.setText(wordsResult.getAuthor());
        DateUtil.putRefreshTAG(me,"words_date");
    }
    private void setRefreshLayout(){
        refreshLayout.setRefreshHeader(new ClassicsHeader(me));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                DateUtil.clearRefreshTAG(me,"food_date","words_date");
                getWordsFromServer();
                getEatWhatFromServer();
            }
        });
    }
    private void autoRefreash(){
        if(DateUtil.canRefresh(me,"food_date")){
            refreshLayout.autoRefresh();
            getEatWhatFromServer();
        }else {
            EatWhatResult result=mySharedPerference.getObject("food",EatWhatResult.class);
            setFood(result);
        }
        if(DateUtil.canRefresh(me,"words_date")){
            getWordsFromServer();
        }else {
            WordsResult result=mySharedPerference.getObject("words",WordsResult.class);
            setWords(result);
        }
    }
}
