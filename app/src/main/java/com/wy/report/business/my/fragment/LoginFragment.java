package com.wy.report.business.my.fragment;

import android.os.Bundle;
import android.view.View;

import com.wy.report.base.fragment.NetworkFragment;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 17-12-20 下午8:48
 * @description: ReadReport
 */
public class LoginFragment extends NetworkFragment {
    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initView(View contentView) {
        super.initView(contentView);
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        setTitle("登录");
    }

    @Override
    protected int contentLayoutID() {
        return 0;
    }
}
