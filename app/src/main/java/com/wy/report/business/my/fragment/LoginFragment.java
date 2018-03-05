package com.wy.report.business.my.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.wy.report.R;
import com.wy.report.base.constant.RxKey;
import com.wy.report.base.fragment.NetworkFragment;
import com.wy.report.base.model.ResponseModel;
import com.wy.report.business.auth.model.User;
import com.wy.report.business.my.model.UserModel;
import com.wy.report.business.my.service.MyService;
import com.wy.report.helper.retrofit.RetrofitHelper;
import com.wy.report.helper.retrofit.subscriber.NetworkSubscriber;
import com.wy.report.manager.auth.UserManger;
import com.wy.report.manager.massage.MessageManager;
import com.wy.report.manager.router.AuthRouterManager;
import com.wy.report.util.LogUtils;
import com.wy.report.util.StringUtils;
import com.wy.report.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

import static com.wy.report.manager.router.AuthRouterManager.ROUTER_REGISTER;
import static com.wy.report.manager.router.AuthRouterManager.ROUTER_VERIFY_LOGIN;
import static com.wy.report.manager.router.AuthRouterManager.ROUTER_VERIFY_PHONE_NUM;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 17-12-20 下午8:48
 * @description: ReadReport
 */
public class LoginFragment extends NetworkFragment {

    private MyService myService;

    @BindView(R.id.account)
    EditText userName;

    @BindView(R.id.pwd)
    EditText passWord;

    @BindView(R.id.toolbar_menu)
    TextView menu;

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
    }

    @Override
    protected void initView(View contentView) {
        super.initView(contentView);
        contentView.findViewById(R.id.verify_code).setVisibility(View.GONE);
        contentView.findViewById(R.id.get_verify_code).setVisibility(View.GONE);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.showSoftInput(userName, 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 500);

        //        userName.setText("18046042250");
        //        passWord.setText("111111");
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        setTitle(getResources().getString(R.string.my_login));
        menu.setText(R.string.my_register);
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
    public void login() {
        myService = RetrofitHelper.getInstance()
                                  .create(MyService.class);
        String username = userName.getText().toString();
        String pwd      = passWord.getText().toString();
        if (StringUtils.isBlank(username)) {
            ToastUtils.showLong(R.string.my_verify_mobile_null);
            return;
        }
        if (StringUtils.isBlank(pwd)) {
            ToastUtils.showLong(R.string.my_verify_pwd_null);
            return;
        }
        LogUtils.d("登录 用户名", username);
        LogUtils.d("登录 密码", pwd);
        myService.loginByPwd(username, pwd).subscribe(new NetworkSubscriber<ResponseModel<UserModel>>(this) {
            @Override
            public void onNext(ResponseModel<UserModel> userModelResponseModel) {
                super.onNext(userModelResponseModel);
                UserModel userModel = userModelResponseModel.getData();
                LogUtils.d("登录成功", userModel.toString());
                User user = new User();
                user.setName(userModel.getName());
                user.setId(userModel.getId());
                user.setHead(userModel.getHeadIcon());
                user.setBirthday(Integer.valueOf(userModel.getBirthday()));
                user.setMobile(userModel.getMobile());
                user.setSex(Integer.valueOf(userModel.getSex()));
                user.setRelationship(userModel.getRelationship());
                UserManger.getInstance().updateUser(user);
                rxBus.post(RxKey.RX_LOGIN, UserManger.getInstance().getLoginUser());
                MessageManager.getInstance().getMessage();
            }
        });

    }

    @OnClick(R.id.verify_code_login)
    public void verifyCodeLogin() {
        AuthRouterManager.getInstance().getRouter().open(getActivity(), ROUTER_VERIFY_LOGIN);
    }

    @OnClick(R.id.verify_code_forget_psw)
    public void forgetPsw() {
        AuthRouterManager.getInstance().getRouter().open(getActivity(), ROUTER_VERIFY_PHONE_NUM);
    }


    @OnClick(R.id.toolbar_menu)
    public void gotoRegister() {
        AuthRouterManager.getInstance().getRouter().open(getActivity(), ROUTER_REGISTER);
    }

    @Subscribe(tags = {@Tag(RxKey.RX_LOGIN)})
    public void onLoginSuccess(User user) {
        getActivity().finish();
    }
}
