package com.yey.slidingconflict.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.HorizontalScrollView;


public class YeyHorizontalScrollView extends HorizontalScrollView {
    private static final String TAG = YeyHorizontalScrollView.class.getName();
    private int childWidth;
    private int yeyHorizontalScrollViewWidth;

    public YeyHorizontalScrollView(Context context) {
        super(context);
    }

    public YeyHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public YeyHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        View mChildView = this.getChildAt(0);
        // YeyHorizontalScrollView中唯一子控件宽度
        childWidth = mChildView.getMeasuredWidth();
        // YeyHorizontalScrollView当前宽度
        yeyHorizontalScrollViewWidth = this.getMeasuredWidth();
    }


    /**
     * onInterceptTouchEvent(),该方法在ViewGroup中被调用得满足三个条件:
     * 1. 事件需要是ACTION_DOWN事件.
     * 2. mFirstTouchTarget != null
     * 3. disallowIntercept = false
     * 当手指触碰到屏幕后,假如YeyHorizontalScrollView中的子View消耗了ACTION_DOWN事件,那么mFirstTouchTarget就被赋值会不等于null.
     * 如果后续的ACTION_MOVE事件被YeyHorizontalScrollView拦截,那么YeyHorizontalScrollView中的子View将首先收到一个MotionEvent.ACTION_CANCLE事件.
     * 然后YeyHorizontalScrollView中会将mFirstTouchTarget设置为null,那么当下一次ACTION_MOVE事件来之时onInterceptTouchEvent()将不会被调用(因为mFirstTouchTarget=null),
     * 然后该ACTION_MOVE事件会被YeyHorizontalScrollView消耗.此后该事件组中的事件YeyHorizontalScrollView中的子View将再也接收不到了.
     *
     * @param
     * @return
     */
    private float interceptLastX, interceptLastY;

    // 是否拦截该事件分发到子View中.
    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        // 调用父类HorizontalScrollView.onInterceptTouchEvent(),该方法中不光做了判断事件是否拦截, 还对HorizontalScrollView其他的变量做了改变,
        // 在子类这里调用父类onInterceptTouchEvent()目的是减小对原有HorizontalScrollView影响,也是复用HorizontalScrollView中的事件拦截机制.
        super.onInterceptTouchEvent(e);
        boolean interceptChildeEvent = false;
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                interceptLastX = e.getX();
                interceptLastY = e.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                // 获取当前手指坐标
                float moveX = e.getX();
                float moveY = e.getY();
                // 移动后,计算出滑动后与上一个坐标点之间的距离.
                float slopX = moveX - interceptLastX;
                float slopY = moveY - interceptLastY;
                // 得到手指滑动距离绝对值
                float slopAbsX = Math.abs(slopX);
                float slopAbsY = Math.abs(slopY);
                if ((slopAbsX > 0 || slopAbsY > 0) && (slopAbsX - slopAbsY) >= 6) {
                    // 如果手指移动距离大于0,且横向移动距离减去纵向移动距离大于6像素
                    // 那么YeyHorizontalScrollView就将该事件拦截, 不分发给它的子View使用,留给自己使用了.
                    // 这样会导致mFirstTouchTarget=null,之后子View就再也接收不到事件组的其他事件了.
                    interceptChildeEvent = true;
                }
                interceptLastX = moveX;
                interceptLastY = moveY;
                break;
        }
        return interceptChildeEvent;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // 调用父类HorizontalScrollView.onTouchEvent(),该方法中不光做了判断事件是否拦截, 还做了其他的事情,比如移动HorizontalScrollView中的子控件.
        // 在子类这里调用父类onTouchEvent目的是减小对原有HorizontalScrollView影响.
        boolean disallowIntercepet = super.onTouchEvent(e);
        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
                // getScrollX() == 0:YeyHorizontalScrollView内容左边与YeyHorizontalScrollView控件左边距离为0,也就是YeyHorizontalScrollView状态无法再向右方滑动了.
                // 那么此时YeyHorizontalScrollView将不需要事件,请求父控件拦截事件,将事件交由父控件处理.
                if (getScrollX() <= 0) {
                    disallowIntercepet = false;// 拦截
                }
                // getScrollX() + scrollViewWidth:YeyHorizontalScrollView内容左边与YeyHorizontalScrollView控件左边距离大小加上YeyHorizontalScrollView控件本身宽度,
                // 这个值应该等于YeyHorizontalScrollView唯一子View的宽度.
                // childWidth <=(getScrollX() + yeyHorizontalScrollViewWidth):这个条件的意思就是YeyHorizontalScrollView状态无法再向左方滑动了.
                // 此时YeyHorizontalScrollView将不需要事件,请求父控件拦截事件,将事件交由父控件处理.
                if (((getScrollX() + yeyHorizontalScrollViewWidth)) >= childWidth) {
                    disallowIntercepet = false;// 拦截
                }
                // 这里是当YeyHorizontalScrollView既可以向左边滑动又可以向右侧滑动时，让父控件别拦截事件。
                if (getScrollX() > 0 && ((getScrollX() + yeyHorizontalScrollViewWidth) < childWidth)) {
                    disallowIntercepet = true;// 不拦截
                }
                break;
        }
        if (disallowIntercepet) {
            // 不拦截
            this.getParent().requestDisallowInterceptTouchEvent(true);
        } else {
            // 拦截
            this.getParent().requestDisallowInterceptTouchEvent(false);
        }
        return disallowIntercepet;
    }


//    dispatchTouchEvent() 该方法中也可以做请求父控件不要拦截的操作,但这里实现会导致两个页面切换的时候新出来的界面会跳跃.
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent e) {
//        boolean disallowIntercepet = super.dispatchTouchEvent(e);
//        switch (e.getAction()) {
//            case MotionEvent.ACTION_MOVE:
//                // getScrollX() == 0:YeyHorizontalScrollView内容左边与YeyHorizontalScrollView控件左边距离为0,也就是YeyHorizontalScrollView状态无法再向右方滑动了.
//                // 那么此时YeyHorizontalScrollView将不需要事件,请求父控件拦截事件,将事件交由父控件处理.
//                if (getScrollX() <= 0) {
//                    disallowIntercepet = false;// 拦截
//                }
//                // getScrollX() + scrollViewWidth:YeyHorizontalScrollView内容左边与YeyHorizontalScrollView控件左边距离大小加上YeyHorizontalScrollView控件本身宽度,
//                // 这个值应该等于YeyHorizontalScrollView唯一子View的宽度.
//                // childWidth <=(getScrollX() + yeyHorizontalScrollViewWidth):这个条件的意思就是YeyHorizontalScrollView状态无法再向左方滑动了.
//                // 此时YeyHorizontalScrollView将不需要事件,请求父控件拦截事件,将事件交由父控件处理.
//                if (((getScrollX() + yeyHorizontalScrollViewWidth)) >= childWidth) {
//                    disallowIntercepet = false;// 拦截
//                }
//
//                if (getScrollX() > 0 && ((getScrollX() + yeyHorizontalScrollViewWidth) < childWidth)) {
//                    disallowIntercepet = true;// 不拦截
//                }
//                break;
//            default:
//        }
//        if (disallowIntercepet) {
//            // 不拦截
//            this.getParent().requestDisallowInterceptTouchEvent(true);
//        } else {
//            // 拦截
//            this.getParent().requestDisallowInterceptTouchEvent(false);
//        }
//        return disallowIntercepet;
//    }
}
