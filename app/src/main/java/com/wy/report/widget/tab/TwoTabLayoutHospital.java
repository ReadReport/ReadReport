package com.wy.report.widget.tab;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.wy.report.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
 *
 * @author cantalou
 * @date 2017-12-30 22:59
 */
public class TwoTabLayoutHospital extends ConstraintLayout implements ViewPager.OnPageChangeListener {

    @BindView(R.id.toolbar_tab_0)
    TextView tab0;

    @BindView(R.id.toolbar_tab_line_0)
    View tabLine0;

    @BindView(R.id.toolbar_tab_1)
    TextView tab1;

    @BindView(R.id.toolbar_tab_line_1)
    View tabLine1;

    private ViewPager viewPager;

    public TwoTabLayoutHospital(Context context) {
        super(context);
        initView();
    }

    public TwoTabLayoutHospital(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public void initView() {
        LayoutInflater.from(getContext())
                      .inflate(R.layout.view_tab_layout_two_hospital, this);
        ButterKnife.bind(this, this);
    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Resources res = getResources();
        if (position == 0) {
            tab0.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.hospital_tab_selected));
            tab0.setTextColor(res.getColor(R.color.lan_30acff));
            tab1.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.hospital_tab));
            tab1.setTextColor(res.getColor(R.color.hei_333333));
            tabLine0.setVisibility(View.VISIBLE);
            tabLine0.setBackground(res.getDrawable(R.color.lan_30acff));
            tabLine1.setVisibility(View.GONE);
        } else {
            tab0.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.hospital_tab));
            tab0.setTextColor(res.getColor(R.color.hei_333333));
            tab1.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.hospital_tab_selected));
            tab1.setTextColor(res.getColor(R.color.lan_30acff));

            tabLine0.setVisibility(View.GONE);
            tabLine1.setVisibility(View.VISIBLE);
            tabLine1.setBackground(res.getDrawable(R.color.lan_30acff));
        }
    }

    @OnClick(R.id.toolbar_tab_0)
    public void clickTab0() {
        viewPager.setCurrentItem(0, true);
    }

    @OnClick(R.id.toolbar_tab_1)
    public void clickTab1() {
        viewPager.setCurrentItem(1, true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void setUpWithViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
        viewPager.addOnPageChangeListener(this);
    }
}
