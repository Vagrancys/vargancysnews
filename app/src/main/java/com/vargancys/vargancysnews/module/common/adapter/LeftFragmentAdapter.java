package com.vargancys.vargancysnews.module.common.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.vargancys.vargancysnews.R;
import com.vargancys.vargancysnews.module.common.fragment.LeftMenuFragment;
import com.vargancys.vargancysnews.module.content.news.api.NewsDataInfo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2020/02/17
 * version:1.0
 */
public class LeftFragmentAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<NewsDataInfo.News> data;

    public LeftFragmentAdapter(Context context,ArrayList<NewsDataInfo.News> data){
        this.mContext = context;
        this.data = data;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = (TextView) View.inflate(mContext,R.layout.item_left_menu,null);
        textView.setText(data.get(position).getTitle());
        if(position == LeftMenuFragment.prePosition){
            textView.setEnabled(true);
        }else{
            textView.setEnabled(false);
        }
        return textView;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getCount() {
        return data.size();
    }

}
