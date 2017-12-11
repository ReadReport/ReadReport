package com.wy.report.business.upload.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.cantalou.android.util.Log;
import com.wy.report.R;
import com.wy.report.base.fragment.ToolbarFragment;
import com.wy.report.util.DensityUtils;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.Observable;
import rx.functions.Action1;

/*
 *
 * @author cantalou
 * @date 2017-12-10 21:47
 */
public class HospitalListFragment extends ToolbarFragment {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    FragmentPagerAdapter pagerAdapter;
    private String[] tabTitles;
    private Fragment[] fragments;

    @Override
    protected void initData(Bundle savedInstanceState) {
        fragments = new Fragment[]{new NotChainUnitFragment(), new ChainUnitFragment()};
        tabTitles = getResources().getStringArray(R.array.report_hospital_tab);
    }

    @Override
    protected void initView(View contentView) {
        super.initView(contentView);
        pagerAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public int getCount() {
                return fragments.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Fragment getItem(int position) {
                return fragments[position];
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return tabTitles[position];
            }
        };
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        Observable.timer(10, TimeUnit.MILLISECONDS)
                  .subscribe(new Action1<Long>() {
                      @Override
                      public void call(Long aLong) {
                          setIndicatorWidth();
                      }
                  });
    }

    @Override
    protected int contentLayoutID() {
        return R.layout.fragment_hospital_list;
    }

    public void setIndicatorWidth() {
        Field tabStrip = null;
        try {
            tabStrip = TabLayout.class.getDeclaredField("mTabStrip");
            tabStrip.setAccessible(true);
            int width = DensityUtils.dip2px(getActivity(), 20);
            LinearLayout llTab = (LinearLayout) tabStrip.get(tabLayout);
            for (int i = 0; i < llTab.getChildCount(); i++) {
                View tabView = tabLayout.getChildAt(i);
                View child = llTab.getChildAt(i);
                child.setPadding(0, 0, 0, 0);
                LinearLayout.LayoutParams childLayoutParams = (LinearLayout.LayoutParams) child.getLayoutParams();
                childLayoutParams.width = width;
                childLayoutParams.leftMargin = (tabView.getMeasuredWidth() - width) / 2;
                child.requestLayout();
            }
        } catch (Exception e) {
            Log.e(e);
        }
    }

    @Override
    protected int toolbarLayoutID() {
        return R.layout.view_hospital_list_toolbar;
    }
}