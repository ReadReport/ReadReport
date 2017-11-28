package com.wy.report.business.home.fragment;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.wy.report.R;
import com.wy.report.base.fragment.BaseFragment;
import com.wy.report.base.fragment.PtrFragment;

import butterknife.BindView;

/*
 * 首页
 * @author cantalou
 * @date 2017-11-26 22:55
 */
public class HomeFragment extends PtrFragment {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View content) {
        super.initView(content);

        toolbar.getBackground()
               .setAlpha(0);
    }

    @Override
    protected int contentLayoutID() {
        return R.layout.fragment_home;
    }
}
