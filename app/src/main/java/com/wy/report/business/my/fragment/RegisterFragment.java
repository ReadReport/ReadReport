package com.wy.report.business.my.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.wy.report.R;
import com.wy.report.base.constant.RxKey;
import com.wy.report.base.fragment.NetworkFragment;
import com.wy.report.base.model.ResponseModel;
import com.wy.report.business.auth.model.User;
import com.wy.report.business.my.model.RegisterMode;
import com.wy.report.business.my.service.MyService;
import com.wy.report.helper.retrofit.RetrofitHelper;
import com.wy.report.helper.retrofit.subscriber.NetworkSubscriber;
import com.wy.report.manager.auth.UserManger;
import com.wy.report.util.RegexUtils;
import com.wy.report.util.StringUtils;
import com.wy.report.util.ToastUtils;
import com.wy.report.widget.view.CountDownTextView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 17-12-20 下午8:48
 * @description: ReadReport
 */
public class RegisterFragment extends NetworkFragment {


    private static String TAG = "LoginFragment";

    private MyService myService;

    @BindView(R.id.account)
    EditText userName;

    @BindView(R.id.pwd)
    EditText passWord;

    @BindView(R.id.verify_code)
    EditText verifyCode;

    @BindView(R.id.toolbar_menu)
    TextView menu;

    @BindView(R.id.register_check)
    CheckBox check;

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        myService = RetrofitHelper.getInstance()
                                  .create(MyService.class);
    }

    @Override
    protected void initView(View contentView) {
        super.initView(contentView);
        passWord.setHint(getString(R.string.register_password_hint));
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        setTitle(getResources().getString(R.string.my_register));
        menu.setText(R.string.my_login);
    }

    @Override
    protected int contentLayoutID() {
        return R.layout.fragment_register;
    }

    @Override
    protected void loadData() {
        super.loadData();
    }

    @OnClick(R.id.register)
    public void register() {
        String username = userName.getText().toString();
        String pwd = passWord.getText().toString();
        String verifycode = verifyCode.getText().toString();

        if (!RegexUtils.isMobileSimple(username))
        {
            ToastUtils.showLong(R.string.my_verify_mobile_null);
            return;
        }
        if (StringUtils.isBlank(verifycode))
        {
            ToastUtils.showLong(R.string.my_verify_verify_null);
            return;
        }
        if (StringUtils.isBlank(pwd))
        {
            ToastUtils.showLong(R.string.my_verify_pwd_null);
            return;
        }
        if (!check.isChecked())
        {
            ToastUtils.showLong(R.string.my_verify_check);
            return;
        }

        Log.d(TAG, "注册 用户名:" + username);
        Log.d(TAG, "注册 验证码:" + verifycode);
        Log.d(TAG, "注册 密码:" + pwd);
        myService.register(username, pwd, verifycode).subscribe(new NetworkSubscriber<ResponseModel<RegisterMode>>(this) {
            @Override
            public void onNext(ResponseModel<RegisterMode> registerModeResponseModel) {
                super.onNext(registerModeResponseModel);
                String newId = registerModeResponseModel.getData().getId();
                User user = new User();
                user.setId(newId);
                UserManger.getInstance().updateUser(user);
                rxBus.post(RxKey.RX_LOGIN,UserManger.getInstance().getLoginUser());
            }
        });
    }


    @OnClick(R.id.get_verify_code)
    public void getVerifyCode(final CountDownTextView textView) {
        String mobile = userName.getText().toString();

        if (!RegexUtils.isMobileSimple(mobile))
        {
            ToastUtils.showLong(R.string.my_verify_mobile_null);
            return;
        }

        myService.getVerifyCode(mobile).subscribe(new NetworkSubscriber<ResponseModel>(this) {
            @Override
            public void onNext(ResponseModel responseModel) {
                super.onNext(responseModel);
                ToastUtils.showLong(getResources().getString(R.string.my_verify_code_send));
                textView.startCountDown();
            }
        });
    }

    @OnClick(R.id.toolbar_menu)
    public void login() {
        getActivity().finish();
    }

    @Subscribe(tags = {@Tag(RxKey.RX_LOGIN)})
    public void onLoginSuccess(User user)
    {
        getActivity().finish();
    }
}
