package com.wy.report.business.my.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.wy.report.R;
import com.wy.report.base.constant.BundleKey;
import com.wy.report.base.fragment.NetworkFragment;
import com.wy.report.base.model.ResponseModel;
import com.wy.report.business.my.service.MyService;
import com.wy.report.helper.retrofit.RetrofitHelper;
import com.wy.report.helper.retrofit.subscriber.NetworkSubscriber;
import com.wy.report.util.StringUtils;
import com.wy.report.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 17-12-20 下午8:48
 * @description: ReadReport
 */
public class ModifyPwdFragment extends NetworkFragment {

    private MyService myService;

    @BindView(R.id.pwd)
    EditText pwd1;

    @BindView(R.id.pwd2)
    EditText pwd2;

    private String phoneNum;



    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        myService = RetrofitHelper.getInstance()
                                  .create(MyService.class);

        phoneNum = getActivity().getIntent().getStringExtra(BundleKey.BUNDLE_KEY_MODIFY_PWD_USERNAME);
    }

    @Override
    protected void initView(View contentView) {
        super.initView(contentView);
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        setTitle(getResources().getString(R.string.modify_pwd_title));
    }

    @Override
    protected int contentLayoutID() {
        return R.layout.fragment_modify_pwd;
    }


    @OnClick(R.id.modify_pwd_submit)
    public void verify() {
        String p1 = pwd1.getText().toString();
        String p2      = pwd2.getText().toString();
        if (!p2.equals(p1) && StringUtils.isNotBlank(p2) && StringUtils.isNotBlank(p1)) {
            ToastUtils.showLong(R.string.modify_pwd_tip);
            return;
        }
        myService.modifyPwd(phoneNum, p1).subscribe(new NetworkSubscriber<ResponseModel>(this) {
            @Override
            public void onNext(ResponseModel userModelResponseModel) {
                super.onNext(userModelResponseModel);
                ToastUtils.showLong(R.string.modify_pwd_success_tip);
            }
        });
    }


}
