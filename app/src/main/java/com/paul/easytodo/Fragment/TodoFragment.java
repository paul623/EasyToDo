package com.paul.easytodo.Fragment;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.kongzue.baseframework.BaseFragment;
import com.kongzue.baseframework.interfaces.BindView;
import com.kongzue.baseframework.interfaces.DarkNavigationBarTheme;
import com.kongzue.baseframework.interfaces.DarkStatusBarTheme;
import com.kongzue.baseframework.interfaces.Layout;
import com.kongzue.baseframework.interfaces.NavigationBarBackgroundColor;
import com.paul.easytodo.DataSource.Goal;
import com.paul.easytodo.DataSource.MessageEvent;
import com.paul.easytodo.MainActivity;
import com.paul.easytodo.Manager.GoalHelper;
import com.paul.easytodo.R;
import com.paul.easytodo.Utils.ConvertGoals;
import com.paul.easytodo.Utils.DateUtil;
import com.yalantis.beamazingtoday.interfaces.AnimationType;
import com.yalantis.beamazingtoday.interfaces.BatModel;
import com.yalantis.beamazingtoday.listeners.BatListener;
import com.yalantis.beamazingtoday.listeners.OnItemClickListener;
import com.yalantis.beamazingtoday.listeners.OnOutsideClickedListener;
import com.yalantis.beamazingtoday.ui.adapter.BatAdapter;
import com.yalantis.beamazingtoday.ui.animator.BatItemAnimator;
import com.yalantis.beamazingtoday.ui.callback.BatCallback;
import com.yalantis.beamazingtoday.ui.widget.BatRecyclerView;
import com.yalantis.beamazingtoday.util.TypefaceUtil;

import org.greenrobot.eventbus.EventBus;
import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindInt;
@DarkStatusBarTheme(true)
@Layout(R.layout.fragment_todo)
public class TodoFragment extends BaseFragment<MainActivity> implements BatListener, OnItemClickListener, OnOutsideClickedListener {
    private BatAdapter mAdapter;
    private List<BatModel> mGoals;
    private BatItemAnimator mAnimator;
    @BindView(R.id.bat_recycler_view)
    private BatRecyclerView mRecyclerView;

    @BindView(R.id.toolbar)
    private Toolbar toolbar;

    private onTodoDataChangedListener listener;
    @Override
    public void add(String string) {
        mGoals.add(0, new Goal(string));
        Goal goal=new Goal(string);
        goal.save();
        mAdapter.notify(AnimationType.ADD, 0);
        listener.notifyDataChanged();
    }

    @Override
    public void delete(int position) {
        GoalHelper.deleteGaol(mGoals.get(position).getText(),mGoals.get(position).isChecked());
        mGoals.remove(position);
        mAdapter.notify(AnimationType.REMOVE, position);
        listener.notifyDataChanged();
    }

    @Override
    public void move(int from, int to) {
        if (from >= 0 && to >= 0) {
            mAnimator.setPosition(to);
            BatModel model = mGoals.get(from);
            ConvertGoals.changeSatusGoalsByGoalMode(model,me);
            mGoals.remove(model);
            mGoals.add(to, model);
            mAdapter.notify(AnimationType.MOVE, from, to);
            if (from == 0 || to == 0) {
                mRecyclerView.getView().scrollToPosition(Math.min(from, to));
            }
            listener.notifyDataChanged();
        }
    }

    @Override
    public void onClick(BatModel item, int position) {
        Toast.makeText(me, item.getText(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onOutsideClicked() {
        mRecyclerView.revertAnimation();
    }

    @Override
    public void initViews() {
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

        mAnimator = new BatItemAnimator();
        mRecyclerView.getView().setLayoutManager(new LinearLayoutManager(me));
        mRecyclerView.getView().setAdapter(mAdapter = new BatAdapter(mGoals = ConvertGoals.getBatGoals(me), this, mAnimator).setOnItemClickListener(this).setOnOutsideClickListener(this));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new BatCallback(this));
        itemTouchHelper.attachToRecyclerView(mRecyclerView.getView());
        mRecyclerView.getView().setItemAnimator(mAnimator);
        mRecyclerView.setAddItemListener(this);
    }

    @Override
    public void setEvents() {
        findViewById(R.id.root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerView.revertAnimation();
            }
        });
    }
    public interface onTodoDataChangedListener{
        public void notifyDataChanged();
    }
    public void setOnTodoDataChangedListener(onTodoDataChangedListener listener){
        this.listener = listener;
    }
    public void refreashToDoView(){
        initDatas();
    }
}
