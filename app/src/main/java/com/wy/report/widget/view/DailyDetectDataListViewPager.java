package com.wy.report.widget.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.wy.report.R;
import com.wy.report.util.DensityUtils;

/**
 * @author cantalou
 * @date 2018年01月18日 8:56
 */
public class DailyDetectDataListViewPager extends ViewPager {

    private float mX;

    private float mY;

    private double touchSlop;

    public DailyDetectDataListViewPager(Context context) {
        super(context);
        init();
    }

    public DailyDetectDataListViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        touchSlop = ViewConfiguration.get(getContext())
                                     .getScaledTouchSlop() * 0.8;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, 0);
            View titleView = findViewById(R.id.fragment_daily_detect_data_list_title);
            if (titleView != null && titleView.getVisibility() == View.VISIBLE) {
                View dataListView = findViewById(R.id.daily_detect_data_list_container);
                int totalHeight = dataListView.getMeasuredHeight() + titleView.getMeasuredHeight();
                if (totalHeight > height) height = totalHeight;
            } else {
                int h = child.getMeasuredHeight();
                if (h > height) height = h;
            }
        }
        int minHeight = DensityUtils.dip2px(getContext(), 250);
        if (height < minHeight) {
            height = minHeight;
        }
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

}
