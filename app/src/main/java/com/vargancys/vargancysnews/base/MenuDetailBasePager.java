package com.vargancys.vargancysnews.base;

import android.content.Context;
import android.view.View;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2020/02/17
 * version:1.0
 */
public abstract class MenuDetailBasePager {
    public Context mContext;
    public View rootView;

    public MenuDetailBasePager(Context context){
        this.mContext = context;
        rootView = initView();
    }

    public abstract View initView();

    public void initData(){

    }
}
