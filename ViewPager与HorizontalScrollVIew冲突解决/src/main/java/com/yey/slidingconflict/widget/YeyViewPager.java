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
            float x = ev.getX();
            if (x < mWidthSize * 0.04 || x > mWidthSize * 0.96) {
                return false;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

}
