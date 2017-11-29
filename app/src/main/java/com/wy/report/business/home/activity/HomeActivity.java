package com.wy.report.business.home.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.LinearLayout;

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
    }

    @Override
    protected void initView() {
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
        initTabLayout();
        viewPager.setCurrentItem(0);
    }

    private void initTabLayout() {
        int[] iconDrawable = new int[]{R.drawable.home_tab_home_selector, R.drawable.home_tab_find_selector, R.drawable.home_tab_me_selector};
        for (int i = 0; i < titles.length; i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(R.layout.view_home_tab_item_layout);
            tab.setIcon(iconDrawable[i]);
            tab.setText(titles[i]);
            if(i == 0){
                tab.select();
            }
        }
    }

    @Override
    protected int contentLayoutID() {
        return R.layout.activity_home;
    }

    @Override
    public boolean isWindowTranslucentStatus() {
        return true;
    }
}
