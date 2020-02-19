package com.vargancys.vargancysnews.module.menudetail;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vargancys.vargancysnews.R;
import com.vargancys.vargancysnews.base.MenuDetailBasePager;
import com.vargancys.vargancysnews.module.content.news.api.NewsDataInfo;
import com.vargancys.vargancysnews.module.menudetail.tabdetailpager.TabDetailPager;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2020/02/17
 * version:1.0
 */
public class NewsMenuDetailPager extends MenuDetailBasePager {
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tab_indicator)
    TabPageIndicator tabIndicator;
    @BindView(R.id.pager_next)
    ImageView pagerNext;
    private ArrayList<NewsDataInfo.News.Children> children;

    private ArrayList<TabDetailPager> tabDetailPagers;
    public NewsMenuDetailPager(Context context, NewsDataInfo.News news){
        super(context);
        this.children = news.getChildren();
    }

    @Override
    public int getLayoutId() {
        return R.layout.newsmenu_detail_pager;
    }

    @Override
    public void initView() {
        pagerNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
            }
        });
    }

    @Override
    public void initData() {
        super.initData();
        Log.e("news","新闻");
        tabDetailPagers = new ArrayList<>();
        for (int i = 0; i < children.size();i++){
            tabDetailPagers.add(new TabDetailPager(mContext,children.get(i)));
        }

        viewPager.setAdapter(new MyNewsMenuDetailPagerAdapter());
        tabIndicator.setViewPager(viewPager);

        tabIndicator.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener{
        @Override
        public void onPageSelected(int i) {
            if(i == 0){
                isEnableSlidingMenu(true);
            }else{
                isEnableSlidingMenu(false);
            }
        }

        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    }

    private void isEnableSlidingMenu(boolean value){

    }

    class MyNewsMenuDetailPagerAdapter extends PagerAdapter{
        @Override
        public int getCount() {
            return tabDetailPagers.size();
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            TabDetailPager tabDetailPager = tabDetailPagers.get(position);
            View view = tabDetailPager.rootView;
            tabDetailPager.initData();
            container.addView(view);
            return rootView;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return children.get(position).getTitle();
        }
    }
}
