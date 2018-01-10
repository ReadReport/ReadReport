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
import com.wy.report.manager.router.AuthRouterManager;
import com.wy.report.util.RegexUtils;
import com.wy.report.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 17-12-20 下午8:48
 * @description: ReadReport
 */
public class VerifyPhoneNumFragment extends NetworkFragment {


    private MyService myService;

    @BindView(R.id.account)
    EditText userName;

    @BindView(R.id.verify_code)
    EditText verifyCode;


    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        myService = RetrofitHelper.getInstance()
                                  .create(MyService.class);
    }

    @Override
    protected void initView(View contentView) {
        super.initView(contentView);
        contentView.findViewById(R.id.pwd).setVisibility(View.GONE);
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        setTitle(getResources().getString(R.string.verify_phone_num_title));
    }

    @Override
    protected int contentLayoutID() {
        return R.layout.fragment_verify_phone_num;
    }


    @OnClick(R.id.verify_phone_num_next)
    public void next() {
        final String username = userName.getText().toString();
        String pwd      = verifyCode.getText().toString();


        Bundle bundle = new Bundle();
        bundle.putString(BundleKey.BUNDLE_KEY_MODIFY_PWD_USERNAME,username);
        AuthRouterManager.getInstance().getRouter().open(getActivity(),AuthRouterManager.ROUTER_MODIFY_PWD,bundle);
        getActivity().finish();
//        if (StringUtils.isBlank(username)) {
//            ToastUtils.showLong(R.string.my_verify_mobile_null);
//            return;
//        }
//        if (StringUtils.isBlank(pwd)) {
//            ToastUtils.showLong(R.string.my_verify_verify_null);
//            return;
//        }
//        myService.verifyPhone(username, pwd).subscribe(new NetworkSubscriber<ResponseModel<VerifyPhoneNumMode>>(this) {
//            @Override
//            public void onNext(ResponseModel<VerifyPhoneNumMode> userModelResponseModel) {
//                super.onNext(userModelResponseModel);
//                String loginId = String.valueOf(UserManger.getInstance().getLoginUser().getId());
//                if (loginId.equals(userModelResponseModel.getData().getId())) {
//                    Bundle bundle = new Bundle();
//                    bundle.putString(BundleKey.BUNDLE_KEY_MODIFY_PWD_USERNAME,username);
//                    AuthRouterManager.getInstance().getRouter().open(getActivity(),AuthRouterManager.ROUTER_MODIFY_PWD,bundle);
//                    getActivity().finish();
//                }
//            }
//        });
    }


    @OnClick(R.id.get_verify_code)
    public void getVerifyCode() {
        String mobile = userName.getText().toString();
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
