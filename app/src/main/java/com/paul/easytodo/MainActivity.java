package com.paul.easytodo;


import android.view.View;

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

    @Override
    public void initViews() {

    }



    @Override
    public void initDatas(JumpParameter parameter) {
        List<Tab> tabs = new ArrayList<>();
        tabs.add(new Tab(this, "主页", R.drawable.icon_home));
        tabs.add(new Tab(this, "时间线", R.drawable.icon_history));
        //tabs.add(new Tab(this, "设置", R.mipmap.img_fragment_mine));
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
    }
    @Override
    public void initFragment(FragmentChangeUtil fragmentChangeUtil) {
        fragmentChangeUtil.addFragment(timeLineFragment);
        fragmentChangeUtil.addFragment(todoFragment);
        changeFragment(0);
    }
}
