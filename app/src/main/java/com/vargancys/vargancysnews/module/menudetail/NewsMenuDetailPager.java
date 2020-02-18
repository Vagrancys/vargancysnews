package com.vargancys.vargancysnews.module.menudetail;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.vargancys.vargancysnews.R;
import com.vargancys.vargancysnews.base.MenuDetailBasePager;

import butterknife.BindView;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2020/02/17
 * version:1.0
 */
public class NewsMenuDetailPager extends MenuDetailBasePager {
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    public NewsMenuDetailPager(Context context){
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.newsmenu_detail_pager;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        super.initData();
        Log.e("news","新闻");
    }
}
