package com.wy.report.business.my.fragment;

import android.view.View;

import com.wy.report.R;
import com.wy.report.base.fragment.ToolbarFragment;
import com.wy.report.manager.router.AuthRouterManager;

import butterknife.OnClick;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-1-8 下午6:21
 * @description: ReadReport
 */
public class SettingFragment extends ToolbarFragment {


    @Override
    protected int contentLayoutID() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void initView(View contentView) {
        super.initView(contentView);
        setTitle(R.string.setting_title);
    }

    @OnClick(R.id.setting_modify_pwd_cl)
    public void modifyPwd() {

    }

    @OnClick(R.id.setting_about_cl)
    public void about() {
        AuthRouterManager.getInstance().getRouter().open(getActivity(), AuthRouterManager.ROUTER_ABOUT);
    }

    @OnClick(R.id.setting_suggestion_cl)
    public void suggestion() {
        AuthRouterManager.getInstance().getRouter().open(getActivity(), AuthRouterManager.ROUTER_FEEDBACK);
    }
}
