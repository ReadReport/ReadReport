package com.wy.report.business.my.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wy.report.R;
import com.wy.report.base.fragment.NetworkFragment;
import com.wy.report.base.model.ResponseModel;
import com.wy.report.business.my.model.VerifyPhoneNumMode;
import com.wy.report.business.my.service.MyService;
import com.wy.report.helper.retrofit.RetrofitHelper;
import com.wy.report.helper.retrofit.subscriber.NetworkSubscriber;
import com.wy.report.manager.auth.UserManger;
import com.wy.report.manager.router.AuthRouterManager;
import com.wy.report.util.StringUtils;
import com.wy.report.util.ToastUtils;
import com.wy.report.widget.view.CountDownTextView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 17-12-20 下午8:48
 * @description: 验证身份
 */
public class VerifyIdentifyFragment extends NetworkFragment {


    private MyService myService;



    @BindView(R.id.verify_code)
    EditText verifyCode;

    @BindView(R.id.verify_identify_bind_num)
    TextView bindNum;

    String phoneNum;


    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        myService = RetrofitHelper.getInstance()
                                  .create(MyService.class);
        phoneNum = UserManger.getInstance().getLoginUser().getMobile();

    }

    @Override
    protected void initView(View contentView) {
        super.initView(contentView);
        bindNum.setText(String.format(getString(R.string.verify_identify_bind_num),phoneNum));
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        setTitle(getResources().getString(R.string.verify_identify_title));
    }

    @Override
    protected int contentLayoutID() {
        return R.layout.fragment_verify_identify;
    }


    @OnClick(R.id.verify_identify_submit)
    public void next() {
        String pwd      = verifyCode.getText().toString();

        myService.verifyPhone(phoneNum,pwd).subscribe(new NetworkSubscriber<ResponseModel<VerifyPhoneNumMode>>(this) {
            @Override
            public void onNext(ResponseModel<VerifyPhoneNumMode> verifyPhoneNumModeResponseModel) {
                super.onNext(verifyPhoneNumModeResponseModel);
                String uid = UserManger.getInstance().getLoginUser().getId();
                VerifyPhoneNumMode mode = verifyPhoneNumModeResponseModel.getData();
                if(StringUtils.equals(uid,mode.getId()))
                {
                    AuthRouterManager.getInstance().getRouter().open(getActivity(),AuthRouterManager.ROUTER_BIND_PHONE);
                }
            }
        });

    }


    @OnClick(R.id.get_verify_code)
    public void getVerifyCode(final CountDownTextView textView) {
        myService.getVerifyCode(phoneNum).subscribe(new NetworkSubscriber<ResponseModel>(this) {
            @Override
            public void onNext(ResponseModel responseModel) {
                super.onNext(responseModel);
                ToastUtils.showLong(getResources().getString(R.string.my_verify_code_send));
                textView.startCountDown();
            }
        });
    }

}
