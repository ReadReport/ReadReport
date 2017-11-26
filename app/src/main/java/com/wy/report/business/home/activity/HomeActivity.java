package com.wy.report.business.home.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.wy.report.R;
import com.wy.report.base.activity.BaseActivity;
import com.wy.report.business.home.fragment.FoundFragment;
import com.wy.report.business.home.fragment.HomeFragment;
import com.wy.report.business.home.fragment.MyFragment;
import com.wy.report.manager.auth.AuthManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity {

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @BindView(R.id.sliding_tabs)
    TabLayout tabLayout;

    Fragment[] fragments = new Fragment[]{new HomeFragment(), new FoundFragment(), new MyFragment()};

    String[] titles;

    @Override
    protected void initData() {

        titles = getResources().getStringArray(R.array.home_title);

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments[position];
            }

            @Override
            public int getCount() {
                return fragments.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }
        });
        viewPager.setOffscreenPageLimit(fragments.length);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void initView() {
    }

    @Override
    protected int contentLayoutID() {
        return R.layout.activity_home;
    }


}
