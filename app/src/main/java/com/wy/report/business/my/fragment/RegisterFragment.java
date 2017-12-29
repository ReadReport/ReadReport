package com.wy.report.business.my.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.wy.report.util.ToastUtils;

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

    @BindView(R.id.register_account)
    EditText userName;

    @BindView(R.id.register_pwd)
    EditText passWord;

    @BindView(R.id.register_verify)
    EditText verifyCode;

    @BindView(R.id.toolbar_menu)
    TextView menu;

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        myService = RetrofitHelper.getInstance()
                                  .create(MyService.class);
    }

    @Override
    protected void initView(View contentView) {
        super.initView(contentView);
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
        String username   = userName.getText().toString();
        String pwd        = passWord.getText().toString();
        String verifycode = verifyCode.getText().toString();
        Log.d(TAG, "注册 用户名:" + username);
        Log.d(TAG, "注册 验证码:" + verifycode);
        Log.d(TAG, "注册 密码:" + pwd);
        myService.register(username, pwd, verifycode).subscribe(new NetworkSubscriber<ResponseModel<RegisterMode>>(this) {
            @Override
            public void onNext(ResponseModel<RegisterMode> registerModeResponseModel) {
                super.onNext(registerModeResponseModel);
                String newId = registerModeResponseModel.getData().getId();
                User user = new User();
                user.setId(Long.valueOf(newId));
                UserManger.getInstance().updateUser(user);
                rxBus.post(RxKey.RX_LOGIN,UserManger.getInstance().getLoginUser());
            }
        });
    }


    @OnClick(R.id.get_verify_code)
    public void getVerifyCode() {
        String mobile = userName.getText().toString();
        myService.getVerifyCode(mobile).subscribe(new NetworkSubscriber<ResponseModel>(this) {
            @Override
            public void onNext(ResponseModel responseModel) {
                super.onNext(responseModel);
                ToastUtils.showLong(getResources().getString(R.string.my_verify_code_send));
            }
        });
    }

    @Subscribe(tags = {@Tag(RxKey.RX_LOGIN)})
    public void onLoginSuccess(User user)
    {
        getActivity().finish();
    }
}
