package com.vargancys.vargancysnews.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.vargancys.vargancysnews.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2020/02/20
 * version:1.0
 */
public class RefreshListView extends ListView {
    //下拉刷新
    public static final int PULL_DOWN_REFRESH = 0;

    //手松刷新
    public static final int RELEASE_REFRESH = 1;

    //正在刷新
    public static final int REFRESHING = 2;

    private int currentStatus = PULL_DOWN_REFRESH;

    private LinearLayout headerView;

    //加载更多
    private View footerView;
    private View ll_pull_down_refresh;
    private ImageView iv_arrow;
    private ProgressBar pb_status;
    private TextView tv_status;
    private TextView tv_time;
    //下拉刷新控件的高
    private int pullDownRefreshHeight;

    private Animation upAnimation;
    private Animation downAnimation;

    //加载更多的高
    private int footerViewHeight;
    private boolean isLoadMore;
    private View topNewsView;

    private int listViewOnScreeY = -1;

    public RefreshListView(Context context){
        this(context,null);
    }

    public RefreshListView(Context context, AttributeSet attrs){
        this(context,attrs,0);
    }

    public RefreshListView(Context context,AttributeSet attrs,int defStyleAttr){
        super(context, attrs, defStyleAttr);
        initHeaderView(context);
        initAnimation();
        initFooterView(context);
    }

    private void initAnimation(){
        upAnimation = new RotateAnimation(0,-180,RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
        upAnimation.setDuration(500);
        upAnimation.setFillAfter(true);

        downAnimation = new RotateAnimation(-180,-360,RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
        downAnimation.setDuration(500);
        downAnimation.setFillAfter(true);
    }

    private void initHeaderView(Context context){
        headerView = (LinearLayout) View.inflate(context, R.layout.refresh_header,null);
        ll_pull_down_refresh = headerView.findViewById(R.id.ll_pull_down_refresh);
        iv_arrow = (ImageView) headerView.findViewById(R.id.iv_arrow);
        pb_status = (ProgressBar) headerView.findViewById(R.id.pb_status);
        tv_status = (TextView) headerView.findViewById(R.id.tv_status);
        tv_time = (TextView) headerView.findViewById(R.id.tv_time);

        //测量
        ll_pull_down_refresh.measure(0,0);
        pullDownRefreshHeight = ll_pull_down_refresh.getMeasuredHeight();
        ll_pull_down_refresh.setPadding(0,-pullDownRefreshHeight,0,0);
        addHeaderView(headerView);
    }

    private void initFooterView(Context context){
        footerView = View.inflate(context,R.layout.refresh_footer,null);
        footerView.measure(0,0);
        footerViewHeight = footerView.getMeasuredHeight();
        footerView.setPadding(0,-footerViewHeight,0,0);
        addFooterView(footerView);
        setOnScrollListener(new MyOnScrollListener());
    }

    /**
     * 添加顶部轮播图
     * @param topNewsView
     */
    public void addTopHeaderView(View topNewsView) {
        if(topNewsView !=null){
            this.topNewsView = topNewsView;
            headerView.addView(topNewsView);
        }
    }

    class MyOnScrollListener implements OnScrollListener{
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if(scrollState == OnScrollListener.SCROLL_STATE_IDLE ||
                    scrollState == OnScrollListener.SCROLL_STATE_FLING){
                if(getLastVisiblePosition() >= getCount()-1){
                    footerView.setPadding(10,10,10,10);
                    isLoadMore = true;
                    if(mOnRefreshListener !=null){
                        mOnRefreshListener.onLoadMore();
                    }
                }
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        }
    }

    private float startY;
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if(startY == -1){
                    startY = ev.getY();
                }

                boolean isDisplayTopNews = isDisplayTopNews();
                if(!isDisplayTopNews){
                    //加载更多
                    break;
                }

                if(currentStatus == REFRESHING){
                    break;
                }
                float endY = ev.getY();
                float distanceY = endY - startY;
                if(distanceY > 0){
                    //下拉
                    int paddingTop =(int) (-pullDownRefreshHeight + distanceY);
                    if(paddingTop < 0 && currentStatus != PULL_DOWN_REFRESH){
                        currentStatus = PULL_DOWN_REFRESH;
                        refreshViewState();
                    }else if(paddingTop > 0 && currentStatus != RELEASE_REFRESH){
                        currentStatus = RELEASE_REFRESH;
                    }
                    ll_pull_down_refresh.setPadding(0,paddingTop,0,0);
                }
                break;
            case MotionEvent.ACTION_UP:
                startY = -1;
                if(currentStatus == PULL_DOWN_REFRESH){
                    ll_pull_down_refresh.setPadding(0,-pullDownRefreshHeight,0,0);
                }else if(currentStatus == RELEASE_REFRESH){
                    currentStatus = REFRESHING;
                    refreshViewState();
                    ll_pull_down_refresh.setPadding(0,0,0,0);

                    if(mOnRefreshListener != null){
                        mOnRefreshListener.onPullDownRefresh();
                    }
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    private boolean isDisplayTopNews() {
        if(topNewsView != null){
            int[] location = new int[2];
            if(listViewOnScreeY == -1){
                getLocationOnScreen(location);
                listViewOnScreeY = location[1];
            }
            topNewsView.getLocationOnScreen(location);
            int topNewsViewOnScreenY = location[1];
        /*if(listViewOnScreeY <= topNewsViewOnScreenY){
            return true;
        }else{
            return false;
        }*/
            return listViewOnScreeY <= topNewsViewOnScreenY;
        }else{
            return true;
        }

    }

    /**
     * 当联网成功和失败的时候回调该方法
     * 用户刷新状态的还原
     * @param success
     */
    public void onRefreshFinish(boolean success){
        tv_status.setText("下拉刷新.....");
        currentStatus = PULL_DOWN_REFRESH;
        iv_arrow.clearAnimation();
        pb_status.setVisibility(GONE);
        iv_arrow.setVisibility(VISIBLE);
        ll_pull_down_refresh.setPadding(0,-pullDownRefreshHeight,0,0);
        if(success){
            tv_time.setText("上次更新时间："+getSystemTime());
        }
    }

    public void onMoreFinish(){
        isLoadMore = false;
        footerView.setPadding(0,-footerViewHeight,0,0);
    }

    /**
     * 得到当前android系统的时间
     * @return
     */
    private String getSystemTime(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date());
    }

    private void refreshViewState(){
        switch (currentStatus){
            case PULL_DOWN_REFRESH:
                iv_arrow.startAnimation(downAnimation);
                tv_status.setText("下拉刷新....");
                break;
            case RELEASE_REFRESH:
                iv_arrow.startAnimation(upAnimation);
                tv_status.setText("手松刷新.....");
                break;
            case REFRESHING:
                tv_status.setText("正在刷新....");
                pb_status.setVisibility(VISIBLE);
                iv_arrow.clearAnimation();
                iv_arrow.setVisibility(GONE);
                break;
        }
    }

    private OnRefreshListener mOnRefreshListener;

    public interface OnRefreshListener{
        void onPullDownRefresh();
        void onLoadMore();
    }

    public void setOnRefreshListener(OnRefreshListener l) {
        this.mOnRefreshListener = l;
    }
}

