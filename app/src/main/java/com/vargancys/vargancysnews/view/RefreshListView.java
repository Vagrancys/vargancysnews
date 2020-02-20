package com.vargancys.vargancysnews.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.vargancys.vargancysnews.R;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2020/02/20
 * version:1.0
 */
public class RefreshListView extends ListView {
    private LinearLayout headerView;
    private View ll_pull_down_refresh;
    private ImageView iv_arrow;
    private ProgressBar pb_status;
    private TextView tv_status;
    private TextView tv_time;

    public RefreshListView(Context context){
        this(context,null);
    }

    public RefreshListView(Context context, AttributeSet attrs){
        this(context,attrs,0);
    }

    public RefreshListView(Context context,AttributeSet attrs,int defStyleAttr){
        super(context, attrs, defStyleAttr);
        initHeaderView(context);
    }

    private void initHeaderView(Context context){
        headerView = (LinearLayout) View.inflate(context, R.layout.refresh_header,null);
        ll_pull_down_refresh = headerView.findViewById(R.id.ll_pull_down_refresh);
        iv_arrow = (ImageView) headerView.findViewById(R.id.iv_arrow);
        pb_status = (ProgressBar) headerView.findViewById(R.id.pb_status);
        tv_status = (TextView) headerView.findViewById(R.id.tv_status);
        tv_time = (TextView) headerView.findViewById(R.id.tv_time);

        addHeaderView(headerView);
    }


}
