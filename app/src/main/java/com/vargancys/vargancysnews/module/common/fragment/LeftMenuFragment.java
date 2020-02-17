package com.vargancys.vargancysnews.module.common.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.vargancys.vargancysnews.R;
import com.vargancys.vargancysnews.base.BaseFragment;
import com.vargancys.vargancysnews.module.common.MainActivity;
import com.vargancys.vargancysnews.module.common.adapter.LeftFragmentAdapter;
import com.vargancys.vargancysnews.module.content.news.NewsFragment;
import com.vargancys.vargancysnews.module.content.news.api.NewsDataInfo;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2020/02/15
 * version:1.0
 */
public class LeftMenuFragment extends BaseFragment {
    private ArrayList<NewsDataInfo.News> data;
    private LeftFragmentAdapter mAdapter;
    public static int prePosition;

    @BindView(R.id.list_view)
    ListView listView;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_left;
    }

    @Override
    public void initView() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                prePosition = position;
                mAdapter.notifyDataSetChanged();
                MainActivity mainActivity = (MainActivity) context;
                mainActivity.getDrawerToggle();
                switchPager(prePosition);
            }
        });
    }

    private void switchPager(int position){
        MainActivity mainActivity = (MainActivity) context;
        ContentFragment contentFragment = mainActivity.getContentFragment();
        NewsFragment newsFragment = contentFragment.getNewsFragment();
        newsFragment.switchPager(position);
    }

    @Override
    public void initData() {
        super.initData();
    }

    //接受数据
    public void setData(ArrayList<NewsDataInfo.News> data){
        this.data = data;
        mAdapter = new LeftFragmentAdapter(context,data);
        listView.setAdapter(mAdapter);
        switchPager(prePosition);
    }

}
