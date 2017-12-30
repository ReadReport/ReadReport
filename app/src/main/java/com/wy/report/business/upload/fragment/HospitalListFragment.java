package com.wy.report.business.upload.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.wy.report.R;
import com.wy.report.base.fragment.ToolbarFragment;

import butterknife.BindView;
import butterknife.OnClick;

/*
 *
 * @author cantalou
 * @date 2017-12-10 21:47
 */
public class HospitalListFragment extends ToolbarFragment {

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    FragmentPagerAdapter pagerAdapter;

    @BindView(R.id.toolbar_tab_0)
    TextView tab0;
    @BindView(R.id.toolbar_tab_line_0)
    View tabLine0;
    @BindView(R.id.toolbar_tab_1)
    TextView tab1;
    @BindView(R.id.toolbar_tab_line_1)
    View tabLine1;
    private String[] tabTitles;
    private Fragment[] fragments;

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
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
            public Fragment getItem(int position) {
                return fragments[position];
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return tabTitles[position];
            }
        };
        viewPager.setOffscreenPageLimit(fragments.length);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    tab0.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.hospital_tab_selected));
                    tab1.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.hospital_tab));
                    tabLine0.setVisibility(View.VISIBLE);
                    tabLine1.setVisibility(View.GONE);
                } else {
                    tab0.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.hospital_tab));
                    tab1.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.hospital_tab_selected));
                    tabLine0.setVisibility(View.GONE);
                    tabLine1.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick(R.id.toolbar_tab_0)
    public void tab0Click() {
        viewPager.setCurrentItem(0, true);
    }

    @OnClick(R.id.toolbar_tab_1)
    public void tab1Click() {
        viewPager.setCurrentItem(1, true);
    }

    @Override
    protected int contentLayoutID() {
        return R.layout.fragment_hospital_list;
    }

    @Override
    protected int toolbarLayoutID() {
        return R.layout.view_hospital_list_toolbar;
    }
}