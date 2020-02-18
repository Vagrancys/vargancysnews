package com.vargancys.vargancysnews.base;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;

import butterknife.ButterKnife;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2020/02/17
 * version:1.0
 */
public abstract class MenuDetailBasePager {
    public Context mContext;
    public View rootView;

    @LayoutRes
    public abstract int getLayoutId();
    public MenuDetailBasePager(Context context){
        this.mContext = context;
        rootView = View.inflate(context,getLayoutId(),null);
        ButterKnife.bind(rootView);
        initView();
    }

    public abstract void initView();

    public void initData(){

    }
}
