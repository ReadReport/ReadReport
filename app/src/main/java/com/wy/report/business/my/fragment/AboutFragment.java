package com.wy.report.business.my.fragment;

import android.view.View;

import com.wy.report.R;
import com.wy.report.base.fragment.ToolbarFragment;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-1-8 下午6:21
 * @description: ReadReport
 */
public class AboutFragment extends ToolbarFragment {


    @Override
    protected int contentLayoutID() {
        return R.layout.fragment_about;
    }

    @Override
    protected void initView(View contentView) {
        super.initView(contentView);
        setTitle(R.string.about_title);
    }


}
