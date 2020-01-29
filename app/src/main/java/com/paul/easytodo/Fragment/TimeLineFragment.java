package com.paul.easytodo.Fragment;

import android.widget.TextView;

import com.kongzue.baseframework.BaseFragment;
import com.kongzue.baseframework.interfaces.BindView;
import com.kongzue.baseframework.interfaces.Layout;
import com.paul.easytodo.DataSource.Goal;
import com.paul.easytodo.MainActivity;
import com.paul.easytodo.R;

import org.litepal.LitePal;

import java.util.List;

@Layout(R.layout.fragment_timeline)
public class TimeLineFragment extends BaseFragment<MainActivity> {

    @Override
    public void initViews() {

    }

    @Override
    public void initDatas() {
        LitePal.initialize(me);
        List<Goal> goalList=LitePal.findAll(Goal.class);

    }

    @Override
    public void setEvents() {

    }
}
