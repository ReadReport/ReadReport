package com.wy.report.widget.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.cantalou.android.util.Log;
import com.wy.report.R;

/**
 * @author cantalou
 * @date 2018年01月18日 8:56
 */
public class DailyDetectDataListViewPager extends ViewPager {

    private float mX;

    private float mY;

    private int touchSlop;

    public DailyDetectDataListViewPager(Context context) {
        super(context);
        init();
    }

    public DailyDetectDataListViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        touchSlop = ViewConfiguration.get(getContext())
                                     .getScaledTouchSlop();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            View titleView = findViewById(R.id.fragment_daily_detect_data_list_title);
            if (titleView != null) {
                View dataListView = findViewById(R.id.daily_detect_data_list_container);
                int totalHeight = dataListView.getMeasuredHeight() + titleView.getMeasuredHeight();
                if (totalHeight > height) height = totalHeight;
            } else {
                int h = child.getMeasuredHeight();
                if (h > height) height = h;
            }
        }
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN: {
//                mX = ev.getX();
//                mY = ev.getY();
//                break;
//            }
//            case MotionEvent.ACTION_MOVE: {
//                float deltaX = ev.getX() - mX;
//                float deltaY = Math.abs(ev.getY() - mY);
//
//                if (deltaY > Math.abs(deltaX) && deltaY > touchSlop) {
//                    getParent().requestDisallowInterceptTouchEvent(false);
//                    return false;
//                }
//
//                break;
//            }
//            case MotionEvent.ACTION_UP: {
//                getParent().requestDisallowInterceptTouchEvent(false);
//                break;
//            }
//        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

}
