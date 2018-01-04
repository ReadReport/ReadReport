package com.wy.report.business.my.fragment;

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
import com.wy.report.manager.router.AuthRouterManager;
import com.wy.report.util.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 消息
 *
 * @author cantalou
 * @date 2017-11-26 23:04
 */
public class MessageFragment extends ToolbarFragment {


    @BindView(R.id.my_header)
    RoundedImageView header;

    @BindView(R.id.my_username)
    TextView userName;

    @BindView(R.id.my_phone)
    TextView phone;

    private boolean isLogin;

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
    }

    @Override
    protected void initView(View content) {
        super.initView(content);
        setTitle(getString(R.string.message_title));
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
//        Drawable toolbarBackground = toolbar.getBackground();
//        toolbarBackground.setAlpha(0);
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
        if (isLogin) {

        } else {
            AuthRouterManager.getInstance()
                    .getRouter()
                    .open(getActivity(), AuthRouterManager.ROUTER_LOGIN);
        }
    }


    @Subscribe(tags = {@Tag(RxKey.RX_LOGIN)})
    public void onLoginSuccess(User user) {
        if (user == null) {
            return;
        }
        isLogin = true;
        if (!StringUtils.isBlank(user.getHead())) {
            Glide.with(getActivity())
                    .load(user.getHead())
                    .into(header);
        }
        String name = StringUtils.isBlank(user.getName()) ? "null" : user.getName();
        String phoneNum = StringUtils.isBlank(user.getMobile()) ? "null" : user.getMobile();
        userName.setText(name);
        phone.setText(phoneNum);
    }
}
