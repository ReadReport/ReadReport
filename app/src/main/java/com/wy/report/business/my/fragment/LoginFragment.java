package com.wy.report.business.my.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.wy.report.R;
import com.wy.report.base.fragment.NetworkFragment;
import com.wy.report.base.model.ResponseModel;
import com.wy.report.business.my.model.UserModel;
import com.wy.report.business.my.service.MyService;
import com.wy.report.business.read.mode.ReportListMode;
import com.wy.report.business.read.service.ReadService;
import com.wy.report.helper.retrofit.RetrofitHelper;
import com.wy.report.helper.retrofit.subscriber.NetworkSubscriber;
import com.wy.report.helper.retrofit.subscriber.PtrSubscriber;

import java.util.List;

import butterknife.OnClick;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 17-12-20 下午8:48
 * @description: ReadReport
 */
public class LoginFragment extends NetworkFragment {

    private MyService myService;

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
        setTitle(getResources().getString(R.string.my_login));
    }

    @Override
    protected int contentLayoutID() {
        return R.layout.fragment_login;
    }

    @Override
    protected void loadData() {
        super.loadData();
    }

    @OnClick(R.id.login)
    public void login()
    {
        myService = RetrofitHelper.getInstance()
                .create(MyService.class);

        myService.loginByPwd("18046042250","111111").subscribe(new NetworkSubscriber<ResponseModel<UserModel>>(this) {
            @Override
            public void onNext(ResponseModel<UserModel> userModelResponseModel) {
                super.onNext(userModelResponseModel);
                UserModel userModel= userModelResponseModel.getData();
                Log.d("fds",userModel.toString());
            }
        });

    }
}
