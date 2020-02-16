package com.vargancys.vargancysnews.module.common.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.vargancys.vargancysnews.module.content.home.HomeFragment;
import com.vargancys.vargancysnews.module.content.news.NewsFragment;
import com.vargancys.vargancysnews.module.content.setting.SettingFragment;
import com.vargancys.vargancysnews.module.content.smart.SmartFragment;
import com.vargancys.vargancysnews.module.content.world.WorldFragment;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2020/02/16
 * version:1.0
 */
public class ContentAdapter extends FragmentPagerAdapter {
    private Fragment[] mFragment;
    private int Size;
    public ContentAdapter(FragmentManager fm,int size){
        super(fm);
        this.Size = size;
        mFragment = new Fragment[size];
    }
    @Override
    public Fragment getItem(int i) {
        if(mFragment[i] == null){
            switch (i){
                case 0:
                    mFragment[i] = HomeFragment.newInstance();
                    break;
                case 1:
                    mFragment[i] = NewsFragment.newInstance();
                    break;
                case 2:
                    mFragment[i] = SmartFragment.newInstance();
                    break;
                case 3:
                    mFragment[i] = WorldFragment.newInstance();
                    break;
                case 4:
                    mFragment[i] = SettingFragment.newInstance();
                    break;
            }
        }
        return mFragment[i];
    }

    @Override
    public int getCount() {
        return Size;
    }
}
