package com.vargancys.vargancysnews.module.common;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;

import com.vargancys.vargancysnews.R;
import com.vargancys.vargancysnews.base.BaseActivity;
import com.vargancys.vargancysnews.module.common.fragment.ContentFragment;
import com.vargancys.vargancysnews.module.common.fragment.LeftMenuFragment;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    private static final String MAIN_CONTENT_TAG = "main_content_tag";
    private static final String LEFTMENU_TAG = "leftmenu_tag";
    @BindView(R.id.id_drawer_layout)
    DrawerLayout drawerLayout;
    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle save) {
        initFragment();
    }

    private void initFragment(){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fl_main_content,new ContentFragment(),MAIN_CONTENT_TAG);
        ft.replace(R.id.fl_left_content,new LeftMenuFragment(),LEFTMENU_TAG);
        ft.commit();
    }
}
