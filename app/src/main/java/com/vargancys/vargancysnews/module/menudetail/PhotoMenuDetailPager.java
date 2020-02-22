package com.vargancys.vargancysnews.module.menudetail;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;
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
public class PhotoMenuDetailPager extends MenuDetailBasePager {
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.gridView)
    GridView gridView;

    public PhotoMenuDetailPager(Context context){
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.photo_menudetail_pager;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        super.initData();
        Log.e("news","图片");
    }
}
