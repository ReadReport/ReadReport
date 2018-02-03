package com.wy.report.business.home.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wy.report.R;
import com.wy.report.base.constant.RxKey;
import com.wy.report.base.fragment.ToolbarFragment;
import com.wy.report.business.auth.model.User;
import com.wy.report.manager.auth.UserManger;
import com.wy.report.manager.router.AuthRouterManager;
import com.wy.report.util.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的
 *
 * @author cantalou
 * @date 2017-11-26 23:04
 */
public class MyFragment extends ToolbarFragment {


    @BindView(R.id.my_header)
    RoundedImageView header;

    @BindView(R.id.my_username)
    TextView userName;

    @BindView(R.id.my_phone)
    TextView phone;


    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
    }

    @Override
    protected void initView(View content) {
        super.initView(content);
        setTitle(getString(R.string.home_my_title));
        if (UserManger.getInstance().isLogin()) {
            onLoginSuccess(UserManger.getInstance().getLoginUser());
        }
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        Drawable toolbarBackground = toolbar.getBackground();
        toolbarBackground.setAlpha(0);
        toolbarBack.setVisibility(View.GONE);
        toolbarTitle.setTextColor(getColor(R.color.bai_ffffff));
    }

    @Override
    protected int toolbarFlag() {
        return TOOL_BAR_FLAG_SHOW | TOOL_BAR_FLAG_OVERLAY;
    }


    @Override
    protected int contentLayoutID() {
        return R.layout.fragment_my;
    }

    @OnClick(R.id.home_my_header_layout)
    public void onHeaderClick() {
        if (UserManger.getInstance().isLogin()) {
            AuthRouterManager.getInstance()
                             .getRouter()
                             .open(getActivity(), AuthRouterManager.ROUTER_USER_INFO);
        } else {
            AuthRouterManager.getInstance()
                             .getRouter()
                             .open(getActivity(), AuthRouterManager.ROUTER_LOGIN);
        }
    }


    @OnClick(R.id.home_my_message)
    public void onMessage() {
        AuthRouterManager.getInstance()
                         .getRouter()
                         .open(getActivity(), AuthRouterManager.ROUTER_MESSAGE);
    }

    @OnClick(R.id.home_my_family)
    public void onFamily() {
        AuthRouterManager.getInstance()
                         .getRouter()
                         .open(getActivity(), AuthRouterManager.ROUTER_FAMILY);
    }

    @OnClick(R.id.home_my_setting)
    public void onSetting() {
        AuthRouterManager.getInstance()
                         .getRouter()
                         .open(getActivity(), AuthRouterManager.ROUTER_SETTING);
    }


    @OnClick(R.id.home_my_bind)
    public void onBindPhone() {
        AuthRouterManager.getInstance()
                         .getRouter()
                         .open(getActivity(), AuthRouterManager.ROUTER_VERIFY_IDENTIFY);
    }

    @OnClick(R.id.home_my_report_manage)
    public void onReportManage() {
        AuthRouterManager.getInstance()
                         .getRouter()
                         .open(getActivity(), AuthRouterManager.ROUTER_REPORT_MANAGE);
    }


    @Subscribe(tags = {@Tag(RxKey.RX_LOGIN)})
    public void onLoginSuccess(User user) {
        if (user == null) {
            return;
        }
        if (!StringUtils.isBlank(user.getHead())) {
            Glide.with(getActivity())
                 .load(user.getHead())
                 .into(header);
        }
        String name     = StringUtils.isBlank(user.getName()) ? "null" : user.getName();
        String phoneNum = StringUtils.isBlank(user.getMobile()) ? "null" : user.getMobile();
        userName.setText(name);
        phone.setText(phoneNum);
    }

    @Subscribe(tags = {@Tag(RxKey.RX_LOGOUT)})
    public void onLogout(Object o) {
        userName.setText(R.string.home_my_login_register);
        phone.setText(R.string.home_my_logined_privilege);
        Glide.with(getActivity())
             .load(R.drawable.btn_login_no)
             .into(header);
    }

    @Subscribe(tags = {@Tag(RxKey.RX_MODIFY_USER_INFO)})
    public void onUserInfoModify(User user) {
        if (user == null) {
            return;
        }
        //头像
        if (StringUtils.isNotBlank(user.getHead())) {
            Glide.with(getActivity())
                 .load(user.getHead())
                 .into(header);
        }
        //名字
        if (StringUtils.isNotBlank(user.getName())) {
            userName.setText(user.getName());
        }
        //电话
        if (StringUtils.isNotBlank(user.getMobile())) {
            phone.setText(user.getMobile());
        }
    }

}
