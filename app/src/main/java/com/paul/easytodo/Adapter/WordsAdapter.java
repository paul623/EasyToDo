package com.paul.easytodo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.cardview.widget.CardView;


import com.paul.easytodo.DataSource.WordsBean;
import com.paul.easytodo.R;
import com.paul.easytodo.Utils.ColorPool;
import com.paul.easytodo.Utils.DateUtil;

import com.paul.easytodo.Utils.WordsUtil;
import com.paul.easytodo.domain.WordsAllResult;

import java.util.ArrayList;
import java.util.List;


public class WordsAdapter extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflater;
    List<WordsAllResult.DataBeanBean> list;
    int []imagearrays=new int[]{R.drawable.img_1,
    R.drawable.img_2,
    R.drawable.img_3,
    R.drawable.img_4,
    R.drawable.img_5,
    R.drawable.img_6,
    R.drawable.img_7,
    R.drawable.img_8};

    public WordsAdapter(Context context,List<WordsAllResult.DataBeanBean> dataBeanBeans) {
        this.context = context;
        this.context=context;
        layoutInflater=LayoutInflater.from(context);
        list=dataBeanBeans;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=layoutInflater.inflate(R.layout.words_item,null);
        TextView textView=view.findViewById(R.id.tv_words);
        CardView cardView=view.findViewById(R.id.cv_words);
        cardView.setCardBackgroundColor(ColorPool.getRandomCardColor(position));
        textView.setText(list.get(position).getContent());
        return view;
    }
}
