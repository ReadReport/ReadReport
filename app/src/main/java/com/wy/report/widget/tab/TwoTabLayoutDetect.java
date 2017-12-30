package com.wy.report.widget.tab;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wy.report.R;

import butterknife.BindView;

/*
 *
 * @author cantalou
 * @date 2017-12-30 22:59
 */
public class TwoTabLayoutDetect extends LinearLayout implements ViewPager.OnPageChangeListener{

    @BindView(R.id.toolbar_tab_0)
    TextView tab0;

    @BindView(R.id.toolbar_tab_line_0)
    View tabLine0;

    @BindView(R.id.toolbar_tab_1)
    TextView tab1;

    @BindView(R.id.toolbar_tab_line_1)
    View tabLine1;

    public TwoTabLayoutDetect(Context context) {
        super(context);
        initView();
    }

    public TwoTabLayoutDetect(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public void initView(){

    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Resources res = getResources();
        if (position == 0) {
            tab0.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.daily_detect_tab_selected));
            tab0.setTextColor(res.getColor(R.color.hei_333333));
            tabLine0.setVisibility(View.VISIBLE);

            tab1.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.daily_detect_tab));
            tab1.setTextColor(res.getColor(R.color.hui_575757));
            tabLine1.setVisibility(View.GONE);
        } else {
            tab0.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.daily_detect_tab));
            tab0.setTextColor(res.getColor(R.color.hui_575757));
            tabLine0.setVisibility(View.GONE);

            tab1.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.daily_detect_tab_selected));
            tab1.setTextColor(res.getColor(R.color.hei_333333));
            tabLine1.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
