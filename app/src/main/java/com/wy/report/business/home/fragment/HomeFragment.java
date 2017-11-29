package com.wy.report.business.home.fragment;

import android.view.View;
import android.widget.ImageView;

import com.wy.report.R;
import com.wy.report.base.fragment.PtrFragment;
import com.wy.report.util.ViewUtils;

import butterknife.BindView;

/*
 * 首页
 * @author cantalou
 * @date 2017-11-26 22:55
 */
public class HomeFragment extends PtrFragment {

    @BindView(R.id.home_top_bg)
    ImageView topBg;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View content) {
        super.initView(content);
//        toolbar.getBackground()
//               .setAlpha(0);
//        ViewUtils.convertToLeftTopCrop(topBg);
    }


    @Override
    protected int toolbarLayoutID() {
        return R.layout.view_home_toolbar;
    }

    @Override
    protected int contentLayoutID() {
        return R.layout.fragment_home;
    }

    protected int toolbarFlag() {
        return TOOL_BAR_FLAG_SHOW | TOOL_BAR_FLAG_OVERLAY;
    }
}
