package com.vargancys.vargancysnews.module.common;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;

import com.vargancys.vargancysnews.R;
import com.vargancys.vargancysnews.base.BaseActivity;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    @BindView(R.id.id_drawer_layout)
    DrawerLayout drawerLayout;
    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle save) {

    }
}
