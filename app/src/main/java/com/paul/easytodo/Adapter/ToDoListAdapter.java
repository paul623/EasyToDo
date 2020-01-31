package com.paul.easytodo.Adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.paul.easytodo.DataSource.Goal;
import com.paul.easytodo.Fragment.TodoFragment;
import com.paul.easytodo.Manager.GoalHelper;
import com.paul.easytodo.R;
import com.paul.easytodo.Utils.ColorPool;
import com.paul.easytodo.Utils.DateUtil;
import com.paul.easytodo.Utils.MessageFactory;

import java.text.MessageFormat;
import java.util.List;

import static android.view.FrameMetrics.ANIMATION_DURATION;


public class ToDoListAdapter extends BaseAdapter {
    List<Goal> goals;
    Context context;
    LayoutInflater layoutInflater;
    Handler handler;
    public ToDoListAdapter(Context context, Handler handler){
        this.context=context;
        goals= GoalHelper.getNeedToDoGoals(context);
        layoutInflater=LayoutInflater.from(context);
        this.handler=handler;
    }
    public void refreash(){
        goals.clear();
        goals= GoalHelper.getNeedToDoGoals(context);
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return goals.size();
    }

    @Override
    public Object getItem(int position) {
        return goals.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final View view=layoutInflater.inflate(R.layout.todo_item,null);
        TextView textView=view.findViewById(R.id.tv_todoname);
        CheckBox checkBox=view.findViewById(R.id.cb_todo);
        textView.setText(goals.get(position).getName());
        textView.setTextColor(ColorPool.getRandomColor());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    deletePattern(view,position);
                }
            }
        });
        return view;
    }


    private void deletePattern(final View view, final int position) {

        Animation.AnimationListener al = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                goals.get(position).setChecked(true);
                goals.get(position).setFinshDate(DateUtil.getCurTimeAndDate());
                goals.get(position).save();
                goals.remove(position);
                handler.sendMessage(MessageFactory.getMessage(1));
                notifyDataSetChanged();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };
        collapse(view, al);

    }

    private void collapse(final View view, Animation.AnimationListener al) {
        final int originHeight = view.getMeasuredHeight();

        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1.0f) {
                    view.setVisibility(View.GONE);
                } else {
                    view.getLayoutParams().height = originHeight - (int) (originHeight * interpolatedTime);
                    view.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        if (al != null) {
            animation.setAnimationListener(al);
        }
        animation.setDuration(300);
        view.startAnimation(animation);
    }

}
