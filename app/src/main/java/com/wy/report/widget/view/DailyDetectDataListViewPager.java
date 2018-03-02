package com.wy.report.widget.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;

import com.wy.report.R;
import com.wy.report.util.DensityUtils;

/**
 * @author cantalou
 * @date 2018年01月18日 8:56
 */
public class DailyDetectDataListViewPager extends ViewPager {

    private int current;

    public DailyDetectDataListViewPager(Context context) {
        super(context);
        init();
    }

    public DailyDetectDataListViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        this.addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                current = position;
                getChildAt(position).requestLayout();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        if (current > getChildCount() - 1) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        int height = 0;
        View child = getChildAt(current);
        child.measure(widthMeasureSpec, 0);

        View titleView = child.findViewById(R.id.fragment_daily_detect_data_list_title);
        if (titleView != null && titleView.getVisibility() == View.VISIBLE) {
            View dataListView = findViewById(R.id.daily_detect_data_list_container);
            int totalHeight = dataListView.getMeasuredHeight() + titleView.getMeasuredHeight();
            if (totalHeight > height) height = totalHeight;
        } else {
            int h = child.getMeasuredHeight();
            if (h > height) height = h;
        }
        int minHeight = DensityUtils.dip2px(getContext(), 270);
        if (height < minHeight) {
            height = minHeight;
        }
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, false);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item, false);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        return false;
    }
}
