package com.vargancys.vargancysnews.module.common.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vargancys.vargancysnews.R;
import com.vargancys.vargancysnews.base.BaseFragment;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2020/02/15
 * version:1.0
 */
public class LeftMenuFragment extends BaseFragment {
    private TextView textView;
    @Override
    public View initView() {
        textView = new TextView(context);
        textView.setTextSize(context.getResources().getDimension(R.dimen.text_size_18sp));
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.YELLOW);
        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        textView.setText("左侧滑栏");
    }
}
