package com.wy.report.business.my.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wy.report.R;
import com.wy.report.base.fragment.NetworkFragment;
import com.wy.report.base.model.ResponseModel;
import com.wy.report.business.auth.model.User;
import com.wy.report.business.my.model.UserModel;
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

    @BindView(R.id.login_account)
    EditText userName;

    @BindView(R.id.login_pwd)
    EditText passWord;

    @BindView(R.id.toolbar_menu)
    TextView menu;

    @Override
    protected void initData(Bundle savedInstanceState) {
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
        setTitle(getResources().getString(R.string.my_verify_login));
        menu.setText(R.string.my_register);
    }

    @Override
    protected int contentLayoutID() {
        return R.layout.fragment_verify_code_login;
    }

    @Override
    protected void loadData() {
        super.loadData();
    }

    @OnClick(R.id.login)
    public void login() {

        String username = userName.getText().toString();
        String pwd      = passWord.getText().toString();
        Log.d(TAG, "登录 用户名:" + username);
        Log.d(TAG, "登录 验证码:" + pwd);
        myService.loginByPwd(username, pwd).subscribe(new NetworkSubscriber<ResponseModel<UserModel>>(this) {
            @Override
            public void onNext(ResponseModel<UserModel> userModelResponseModel) {
                super.onNext(userModelResponseModel);
                UserModel userModel = userModelResponseModel.getData();
                Log.d(TAG, "登录成功:" + userModel.toString());
                User user = new User();
                user.setName(userModel.getName());
                user.setId(Integer.valueOf(userModel.getId()));
                user.setHead(userModel.getHeadIcon());
                user.setBirthday(Integer.valueOf(userModel.getBirthday()));
                user.setMobile(userModel.getMobile());
                user.setSex(Integer.valueOf(userModel.getSex()));
                user.setRelationship(userModel.getRelationship());
                UserManger.getInstance().updateUser(user);
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
}
