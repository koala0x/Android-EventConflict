package com.yey.slidingconflict.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class YeyViewPager extends ViewPager {

    private int mWidthSize;

    public YeyViewPager(@NonNull Context context) {
        super(context);
    }

    public YeyViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mWidthSize = this.getMeasuredWidth();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // 如果Down事件的坐标点太过靠近ViewPager侧边,则ViewPager不消耗该次事件组.
            // 这个用来解决侧边滑入ViewPager时,ViewPager直接拦截该事件组,不向下分发事件到子View.
            // 导致HorizontalScrollView无法随手指进行移动的bug.
            float x = ev.getX();
            if (x < mWidthSize * 0.04 || x > mWidthSize * 0.96) {
                // 该事件直接返回不消费,这次事件组就无法再被分发到ViewPager中来了.
                return false;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

}
