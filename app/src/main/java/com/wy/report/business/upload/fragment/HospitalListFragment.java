package com.wy.report.business.upload.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.wy.report.R;
import com.wy.report.base.fragment.ToolbarFragment;
import com.wy.report.widget.tab.TwoTabLayoutDetect;
import com.wy.report.widget.tab.TwoTabLayoutHospital;

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

    @BindView(R.id.two_tab_layout_hospital)
    TwoTabLayoutHospital twoTabLayoutHospital;

    FragmentPagerAdapter pagerAdapter;

    private String[] tabTitles;

    private Fragment[] fragments;

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        fragments = new Fragment[]{new NotChainUnitFragment(), new ChainUnitFragment()};
        tabTitles = getResources().getStringArray(R.array.report_hospital_tab);
        for (Fragment fragment : fragments) {
            fragment.setArguments(getArguments());
        }
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
        twoTabLayoutHospital.setUpWithViewPager(viewPager);
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        setTitle(R.string.report_hospital_title);
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

}