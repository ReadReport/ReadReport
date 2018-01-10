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
import com.wy.report.manager.auth.UserManger;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的信息
 *
 * @author cantalou
 * @date 2017-11-26 23:04
 */
public class UserInfoFragment extends ToolbarFragment {


    private static String TAG = "UserInfoFragment";

    @BindView(R.id.user_info_header)
    RoundedImageView header;

    @BindView(R.id.user_info_name)
    TextView name;

    @BindView(R.id.user_info_birthday)
    TextView birthday;

    @BindView(R.id.user_info_sex)
    TextView sex;

    private User user;

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        user = UserManger.getInstance().getLoginUser();
    }

    @Override
    protected void initView(View contentView) {
        super.initView(contentView);
        updataInfo();
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        setTitle(getResources().getString(R.string.user_info_title));
    }

    @Override
    protected int contentLayoutID() {
        return R.layout.fragment_user_info;
    }



    @Subscribe(tags = {@Tag(RxKey.RX_LOGOUT)})
    public void onLogout() {
        getActivity().finish();
    }

    private void updataInfo() {
        if (user != null) {
            name.setText(user.getName());
            birthday.setText(String.valueOf(user.getBirthday()));
            sex.setText(user.getSex());
            Glide.with(getActivity()).load(user.getHead()).into(header);
        }
    }

    /**修改头像*/
    @OnClick(R.id.user_info_header_info)
    public void modifyHeader() {
        UserManger.getInstance().logout();
    }

    /**
     * 修改名字
     */
    @OnClick(R.id.user_info_user_name_info)
    public void modifyName() {
        UserManger.getInstance().logout();
    }

    /**
     * 修改生日
     */
    @OnClick(R.id.user_info_birthday_info)
    public void modifyBirthday() {
        UserManger.getInstance().logout();
    }

    /**
     * 修改性别
     */
    @OnClick(R.id.user_info_sex_info)
    public void modifySex() {
        UserManger.getInstance().logout();
    }

}
