package com.paul.easytodo;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;

import com.kongzue.baseframework.BaseActivity;
import com.kongzue.baseframework.BaseFragment;
import com.kongzue.baseframework.interfaces.BindView;
import com.kongzue.baseframework.interfaces.DarkNavigationBarTheme;
import com.kongzue.baseframework.interfaces.DarkStatusBarTheme;
import com.kongzue.baseframework.interfaces.FragmentLayout;
import com.kongzue.baseframework.interfaces.Layout;
import com.kongzue.baseframework.interfaces.NavigationBarBackgroundColor;
import com.kongzue.baseframework.interfaces.OnFragmentChangeListener;
import com.kongzue.baseframework.util.FragmentChangeUtil;
import com.kongzue.baseframework.util.JumpParameter;
import com.kongzue.tabbar.Tab;
import com.kongzue.tabbar.TabBarView;
import com.kongzue.tabbar.interfaces.OnTabChangeListener;
import com.paul.easytodo.Fragment.FocusFragment;
import com.paul.easytodo.Fragment.SettingFragment;
import com.paul.easytodo.Fragment.TimeLineFragment;
import com.paul.easytodo.Fragment.TodoFragment;

import java.util.ArrayList;
import java.util.List;

@Layout(R.layout.activity_main)
@DarkStatusBarTheme(true)
@NavigationBarBackgroundColor(a = 100, r = 0, g = 0, b = 0)
@DarkNavigationBarTheme(true)
@FragmentLayout(R.id.frame)
public class MainActivity extends BaseActivity {
    @BindView(R.id.tabbar)
    private TabBarView tabbar;

    private TodoFragment todoFragment=new TodoFragment();
    private TimeLineFragment timeLineFragment=new TimeLineFragment();
    private SettingFragment settingFragment=new SettingFragment();
    private FocusFragment focusFragment=new FocusFragment();

    @Override
    public void initViews() {
        String [] premissions={"android.permission.WRITE_EXTERNAL_STORAGE","android.permission.READ_EXTERNAL_STORAGE"};
        requestPermissions(premissions,0);
        if (Build.VERSION.SDK_INT >= 21) {
            // 好的当前活动的DecorView,在改变UI显示
            View decorView = me.getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            // 使其状态栏呈现透明色
            me.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }




    @Override
    public void initDatas(JumpParameter parameter) {
        List<Tab> tabs = new ArrayList<>();
        tabs.add(new Tab(this, "主页", R.drawable.icon_home));
        tabs.add(new Tab(this, "打卡", R.drawable.icon_history));
        tabs.add(new Tab(this,"专注",R.drawable.icon_focus));
        tabs.add(new Tab(this, "设置", R.drawable.ic_setting));
        tabbar.setTab(tabs);
    }

    @Override
    public void setEvents() {
        tabbar.setOnTabChangeListener(new OnTabChangeListener() {
            @Override
            public void onTabChanged(View v, int index) {
                changeFragment(index);
            }
        });
        getFragmentChangeUtil().setOnFragmentChangeListener(new OnFragmentChangeListener() {
            @Override
            public void onChange(int index, BaseFragment fragment) {
                tabbar.setNormalFocusIndex(index);
            }
        });
       todoFragment.setOnTodoDataChangedListener(new TodoFragment.onTodoDataChangedListener() {
           @Override
           public void notifyDataChanged() {
               timeLineFragment.refreashToDoView();
           }
       });
       timeLineFragment.setOnDataChangedListener(new TimeLineFragment.DataChangedListener() {
           @Override
           public void onToDoDataChanged() {
               todoFragment.refreashToDoView();

           }
       });
       settingFragment.setOnRefreashImageListener(new SettingFragment.refreashImageListener() {
           @Override
           public void refreashHeadIcon(Bitmap bitmap) {
               timeLineFragment.refreashHeadIcon(bitmap);
           }

           @Override
           public void refreashBackground(Bitmap bitmap) {
                timeLineFragment.refreashBack(bitmap);
           }

           @Override
           public void refreashAllData() {
               todoFragment.refreashToDoView();
               timeLineFragment.refreashToDoView();
           }
       });
    }
    @Override
    public void initFragment(FragmentChangeUtil fragmentChangeUtil) {
        fragmentChangeUtil.addFragment(timeLineFragment);
        fragmentChangeUtil.addFragment(todoFragment);
        fragmentChangeUtil.addFragment(focusFragment);
        fragmentChangeUtil.addFragment(settingFragment);
        changeFragment(0);
    }
}
