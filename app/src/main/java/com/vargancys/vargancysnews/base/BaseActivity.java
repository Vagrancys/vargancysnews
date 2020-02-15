package com.vargancys.vargancysnews.base;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;

import com.trello.rxlifecycle.components.RxActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2020/02/14
 * version:1.0
 */
public abstract class BaseActivity extends RxActivity {
    private Unbinder bind;

    @LayoutRes
    public abstract
    int getLayoutId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        bind = ButterKnife.bind(this);
        initView(savedInstanceState);
    }

    public abstract void initView(Bundle save);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
