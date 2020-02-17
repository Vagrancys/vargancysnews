package com.vargancys.vargancysnews.module.content.home;

import android.view.View;

import com.vargancys.vargancysnews.R;
import com.vargancys.vargancysnews.base.BaseFragment;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2020/02/16
 * version:1.0
 */
public class HomeFragment extends BaseFragment {
    public static HomeFragment newInstance(){
        return new HomeFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        super.initData();
    }
}
