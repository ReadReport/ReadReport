package com.wy.report.business.my.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.wy.report.R;
import com.wy.report.base.fragment.NetworkFragment;
import com.wy.report.base.model.ResponseModel;
import com.wy.report.business.my.service.MyService;
import com.wy.report.helper.retrofit.RetrofitHelper;
import com.wy.report.helper.retrofit.subscriber.NetworkSubscriber;
import com.wy.report.manager.auth.UserManger;
import com.wy.report.util.RegexUtils;
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
public class BindPhoneFragment extends NetworkFragment {

    private MyService myService;

    @BindView(R.id.account)
    EditText phone;

    @BindView(R.id.verify_code)
    EditText verifyCode;

    private String phoneNum;
    private String uid;



    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        myService = RetrofitHelper.getInstance()
                                  .create(MyService.class);

        phoneNum = UserManger.getInstance().getLoginUser().getMobile();
        uid = UserManger.getInstance().getLoginUser().getId();

    }

    @Override
    protected void initView(View contentView) {
        super.initView(contentView);
        phone.setText(phoneNum);
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        setTitle(getResources().getString(R.string.bind_phone_title));
    }

    @Override
    protected int contentLayoutID() {
        return R.layout.fragment_bind_phone;
    }


    @OnClick(R.id.bind_phone_submit)
    public void bind() {
        String mobile = phone.getText().toString();
        if (!RegexUtils.isMobileSimple(mobile)) {
            ToastUtils.showLong(getResources().getString(R.string.my_verify_mobile_null));
            return;
        }
        if (StringUtils.isBlank(verifyCode)) {
            ToastUtils.showLong(R.string.my_verify_verify_null);
            return;
        }
        myService.bindPhone(uid,mobile,verifyCode.getText().toString()).subscribe(new NetworkSubscriber<ResponseModel>(this) {
            @Override
            public void onNext(ResponseModel responseModel) {
                super.onNext(responseModel);
                ToastUtils.showLong(getResources().getString(R.string.bind_phone_success));
            }
        });
    }

    @OnClick(R.id.get_verify_code)
    public void getVerifyCode() {
        String mobile = phone.getText().toString();
        if (!RegexUtils.isMobileSimple(mobile)) {
            ToastUtils.showLong(getResources().getString(R.string.my_verify_mobile_null));
            return;
        }
        myService.getVerifyCode(mobile).subscribe(new NetworkSubscriber<ResponseModel>(this) {
            @Override
            public void onNext(ResponseModel responseModel) {
                super.onNext(responseModel);
                ToastUtils.showLong(getResources().getString(R.string.my_verify_code_send));
            }
        });
    }


}
