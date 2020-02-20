package com.vargancys.vargancysnews.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2020/02/20
 * version:1.0
 */
public class HorizontalScrollViewPager extends ViewPager {
    public HorizontalScrollViewPager(Context context){
        super(context);
    }

    public HorizontalScrollViewPager(Context context, AttributeSet attrs){
        super(context,attrs);

    }

    private float startX;
    private float startY;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(false);
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                startX = ev.getX();
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float endX = ev.getX();
                float endY = ev.getY();
                float distanceX = endX - startX;
                float distanceY = endY - startY;
                if(Math.abs(distanceX) >Math.abs(distanceY)){
                    if(getCurrentItem() == 0 &&distanceX >0){
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }else if(getCurrentItem() == (getAdapter().getCount() - 1) && distanceX < 0){
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }else{
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                }else{
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
                default:
                    break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
