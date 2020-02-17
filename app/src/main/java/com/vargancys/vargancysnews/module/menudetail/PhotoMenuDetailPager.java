package com.vargancys.vargancysnews.module.menudetail;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.vargancys.vargancysnews.base.MenuDetailBasePager;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2020/02/17
 * version:1.0
 */
public class PhotoMenuDetailPager extends MenuDetailBasePager {
    private TextView textView;
    public PhotoMenuDetailPager(Context context){
        super(context);
    }

    @Override
    public View initView() {
        textView = new TextView(mContext);
        textView.setText("图片");
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.YELLOW);
        textView.setTextSize(25);
        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        Log.e("news","图片");
    }
}
