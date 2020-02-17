package com.vargancys.vargancysnews.module.common.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.vargancys.vargancysnews.R;
import com.vargancys.vargancysnews.base.BaseFragment;
import com.vargancys.vargancysnews.module.common.adapter.ContentAdapter;
import com.vargancys.vargancysnews.module.content.news.NewsFragment;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2020/02/15
 * version:1.0
 */
public class ContentFragment extends BaseFragment {

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.common_tab)
    CommonTabLayout commonTab;

    private int[] iconSelector = {
            R.drawable.content_home_selector,R.drawable.content_news_selector,
            R.drawable.content_smart_selector,R.drawable.content_world_selector,
            R.drawable.content_setting_selector};
    private int[] iconUnSelector = {
            R.drawable.content_home,R.drawable.content_news,
            R.drawable.content_smart,R.drawable.content_world,
            R.drawable.content_setting
    };
    private String[] mTitle = {"主页","新闻","智慧","政要","设置"};
    private ArrayList<CustomTabEntity> contentEntities = new ArrayList<>();
    private ContentAdapter contentAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_content;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        super.initData();
        initEntity();
        initCommonTab();

    }

    private void initCommonTab(){
        contentAdapter = new ContentAdapter(getFragmentManager(),mTitle.length);
        viewPager.setAdapter(contentAdapter);
        viewPager.setOffscreenPageLimit(4);
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
        commonTab.setTabData(contentEntities);
        commonTab.setCurrentTab(0);
        commonTab.setOnTabSelectListener(new MyOnTabSelectListener());
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener{
        @Override
        public void onPageScrollStateChanged(int i) {

        }

        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            commonTab.setCurrentTab(i);
        }
    }

    public class MyOnTabSelectListener implements OnTabSelectListener{
        @Override
        public void onTabSelect(int position) {
            viewPager.setCurrentItem(position);
        }

        @Override
        public void onTabReselect(int position) {

        }
    }

    private void initEntity(){
        for (int i = 0; i < mTitle.length; i++){
            contentEntities.add(new ContentEntity(mTitle[i],iconSelector[i],iconUnSelector[i]));
        }
    }

    class ContentEntity implements CustomTabEntity{
        private String Title;
        private int Icon;
        private int UnIcon;
        private ContentEntity(String Title,int Icon,int UnIcon){
            this.Title = Title;
            this.Icon = Icon;
            this.UnIcon = UnIcon;
        }
        @Override
        public int getTabSelectedIcon() {
            return Icon;
        }

        @Override
        public int getTabUnselectedIcon() {
            return UnIcon;
        }

        @Override
        public String getTabTitle() {
            return Title;
        }
    }

    public NewsFragment getNewsFragment(){
        return (NewsFragment) contentAdapter.getItem(1);
    }
}
