package com.wy.report.manager.router;

import android.content.Context;
import android.os.Bundle;

import com.wy.report.ReportApplication;
import com.wy.report.base.activity.StandardActivity;
import com.wy.report.base.constant.BundleKey;
import com.wy.report.business.dailydetect.fragment.DailyDetectDispatchFragment;
import com.wy.report.business.family.fragment.FamilyMemberSelectFragment;
import com.wy.report.business.home.activity.HomeActivity;
import com.wy.report.business.my.fragment.AboutFragment;
import com.wy.report.business.my.fragment.BindPhoneFragment;
import com.wy.report.business.my.fragment.EditFamilyFragment;
import com.wy.report.business.my.fragment.EditUserInfoFragment;
import com.wy.report.business.my.fragment.FamilyFragment;
import com.wy.report.business.my.fragment.FeedbackFragment;
import com.wy.report.business.my.fragment.LoginFragment;
import com.wy.report.business.my.fragment.MessageDetailFragment;
import com.wy.report.business.my.fragment.MessageFragment;
import com.wy.report.business.my.fragment.ModifyPwdFragment;
import com.wy.report.business.my.fragment.RegisterFragment;
import com.wy.report.business.my.fragment.SettingFragment;
import com.wy.report.business.my.fragment.UserInfoFragment;
import com.wy.report.business.my.fragment.VerifyIdentifyFragment;
import com.wy.report.business.my.fragment.VerifyLoginFragment;
import com.wy.report.business.my.fragment.VerifyPhoneNumFragment;
import com.wy.report.business.other.fragment.PictureFragment;
import com.wy.report.business.read.fragment.AskFragment;
import com.wy.report.business.read.fragment.DoctorDetailFragment;
import com.wy.report.business.read.fragment.ReportDetailFragment;
import com.wy.report.business.read.fragment.ReportManageFragment;
import com.wy.report.business.upload.fragment.HospitalListFragment;
import com.wy.report.business.upload.fragment.ReportQueryFragment;
import com.wy.report.business.upload.fragment.ReportUploadFragment;
import com.wy.report.business.upload.fragment.ReportUploadQueryFragment;
import com.wy.report.business.upload.fragment.ReportUploadSuccessFragment;
import com.wy.report.business.web.fragment.WebViewFragment;
import com.wy.report.manager.auth.UserManger;

public class AuthRouterManager {

    public static final String PUBLIC_ACTIVITY_PREFIX = "public/";

    public static final String LOGIN_ACTIVITY_PREFIX = "login/";

    /**
     * 主页面
     */
    public static final String ROUTER_HOME = PUBLIC_ACTIVITY_PREFIX + "ROUTER_HOME";

    /**
     * 上传查询报告界面
     */
    public static final String ROUTER_REPORT_UPLOAD_QUERY = LOGIN_ACTIVITY_PREFIX + "ROUTER_REPORT_UPLOAD_QUERY";

    /**
     * 上传报告界面
     */
    public static final String ROUTER_REPORT_UPLOAD = LOGIN_ACTIVITY_PREFIX + "ROUTER_REPORT_UPLOAD";

    /**
     * 上传报告界面
     */
    public static final String ROUTER_REPORT_QUERY = LOGIN_ACTIVITY_PREFIX + "ROUTER_REPORT_QUERY";

    /**
     * 图片预览界面
     */
    public static final String ROUTER_OTHER_PICTURE_PREVIEW = LOGIN_ACTIVITY_PREFIX + "ROUTER_OTHER_PICTURE_PREVIEW";

    /**
     * 家庭成员选择界面
     */
    public static final String ROUTER_FAMILY_MEMBER_SELECT = LOGIN_ACTIVITY_PREFIX + "ROUTER_FAMILY_MEMBER_SELECT";

    /**
     * 家庭成员选择界面
     */
    public static final String ROUTER_REPORT_HOSPITAL_LIST = LOGIN_ACTIVITY_PREFIX + "ROUTER_REPORT_HOSPITAL_LIST";

    /**
     * 报告管理界面
     */
    public static final String ROUTER_REPORT_MANAGE = LOGIN_ACTIVITY_PREFIX + "ROUTER_REPORT_MANAGE";

    /**
     * 登录界面
     */
    public static final String ROUTER_LOGIN = PUBLIC_ACTIVITY_PREFIX + "ROUTER_LOGIN";

    /**
     * 验证码登录界面
     */
    public static final String ROUTER_VERIFY_LOGIN = PUBLIC_ACTIVITY_PREFIX + "ROUTER_VERIFY_LOGIN";

    /**
     * 每日监测
     */
    public static final String ROUTER_DAILY_DETECT = LOGIN_ACTIVITY_PREFIX + "ROUTER_DAILY_DETECT";

    /**
     * webview
     */
    public static final String ROUTER_WEBVIEW = PUBLIC_ACTIVITY_PREFIX + "ROUTER_WEBVIEW";

    /**
     * 注册界面
     */
    public static final String ROUTER_REGISTER = PUBLIC_ACTIVITY_PREFIX + "ROUTER_REGISTER";

    /**
     * 消息界面
     */
    public static final String ROUTER_MESSAGE = LOGIN_ACTIVITY_PREFIX + "ROUTER_MESSAGE";

    /**
     * 消息详细界面
     */
    public static final String ROUTER_MESSAGE_DETAIL = LOGIN_ACTIVITY_PREFIX + "ROUTER_MESSAGE_DETAIL";

    /**
     * 家庭成员界面
     */
    public static final String ROUTER_FAMILY = LOGIN_ACTIVITY_PREFIX + "ROUTER_FAMILY";

    /**
     * 编辑家庭成员界面
     */
    public static final String ROUTER_EDIT_FAMILY = LOGIN_ACTIVITY_PREFIX + "ROUTER_EDIT_FAMILY";

    /**
     * 设置界面
     */
    public static final String ROUTER_SETTING = PUBLIC_ACTIVITY_PREFIX + "ROUTER_EDIT_SETTING";

    /**
     * 意见反馈
     */
    public static final String ROUTER_FEEDBACK = LOGIN_ACTIVITY_PREFIX + "ROUTER_FEEDBACK";

    /**
     * 关于我们
     */
    public static final String ROUTER_ABOUT = PUBLIC_ACTIVITY_PREFIX + "ROUTER_ABOUT";

    /**
     * 验证手机号
     */
    public static final String ROUTER_VERIFY_PHONE_NUM = LOGIN_ACTIVITY_PREFIX + "ROUTER_VERIFY_PHONE_NUM";

    /**
     * 修改密码
     */
    public static final String ROUTER_MODIFY_PWD = LOGIN_ACTIVITY_PREFIX + "ROUTER_MODIFY_PWD";

    /**
     * 验证身份
     */
    public static final String ROUTER_VERIFY_IDENTIFY = LOGIN_ACTIVITY_PREFIX + "ROUTER_VERIFY_IDENTIFY";

    /**
     * 修改绑定电话号码
     */
    public static final String ROUTER_BIND_PHONE = LOGIN_ACTIVITY_PREFIX + "ROUTER_BIND_PHONE";

    /**
     * 个人信息界面
     */
    public static final String ROUTER_USER_INFO = LOGIN_ACTIVITY_PREFIX + "ROUTER_USER_INFO";

    /**
     * 编辑昵称界面
     */
    public static final String ROUTER_EDIT_USERNAME = LOGIN_ACTIVITY_PREFIX + "ROUTER_EDIT_USERNAME";

    /**
     * 报告详细界面
     */
    public static final String ROUTER_REPORT_DETAIL = LOGIN_ACTIVITY_PREFIX + "ROUTER_REPORT_DETAIL";

    /**
     * 医生详细界面
     */
    public static final String ROUTER_DOCTOR_DETAIL = LOGIN_ACTIVITY_PREFIX + "ROUTER_DOCTOR_DETAIL";

    /**
     * 追问详细界面
     */
    public static final String ROUTER_ASK = LOGIN_ACTIVITY_PREFIX + "ROUTER_ASK";

    /**
     * 报告上传成功
     */
    public static final String ROUTER_REPORT_UPLOAD_SUCCESS = LOGIN_ACTIVITY_PREFIX + "ROUTER_REPORT_UPLOAD_SUCCESS";


    private Router router;

    private UserManger userManger;

    /**
     * Instantiates a new router manager.
     */
    private AuthRouterManager() {
        router = new Router();
        userManger = UserManger.getInstance();
        router.addInterceptor(new Router.Interceptor() {
            @Override
            public boolean process(String url) {
                if (url.startsWith(LOGIN_ACTIVITY_PREFIX) && !userManger.isLogin()) {
                    router.open(ReportApplication.getCurrentActivity(), ROUTER_LOGIN);
                    return true;
                }
                return false;
            }
        });
        configRouterMap();
    }

    public static AuthRouterManager getInstance() {
        return InstanceHolder.instance;
    }

    /**
     * 配置系统路由映射.
     */
    private void configRouterMap() {
        router.map(ROUTER_HOME, HomeActivity.class);
        router.map(ROUTER_REPORT_UPLOAD_QUERY, StandardActivity.class, ReportUploadQueryFragment.class);
        router.map(ROUTER_REPORT_UPLOAD, StandardActivity.class, ReportUploadFragment.class);
        router.map(ROUTER_REPORT_QUERY, StandardActivity.class, ReportQueryFragment.class);
        router.map(ROUTER_OTHER_PICTURE_PREVIEW, StandardActivity.class, PictureFragment.class);
        router.map(ROUTER_FAMILY_MEMBER_SELECT, StandardActivity.class, FamilyMemberSelectFragment.class);
        router.map(ROUTER_REPORT_HOSPITAL_LIST, StandardActivity.class, HospitalListFragment.class);
        router.map(ROUTER_REPORT_MANAGE, StandardActivity.class, ReportManageFragment.class);
        router.map(ROUTER_LOGIN, StandardActivity.class, LoginFragment.class);
        router.map(ROUTER_VERIFY_LOGIN, StandardActivity.class, VerifyLoginFragment.class);
        router.map(ROUTER_DAILY_DETECT, StandardActivity.class, DailyDetectDispatchFragment.class);
        router.map(ROUTER_WEBVIEW, StandardActivity.class, WebViewFragment.class);
        router.map(ROUTER_REGISTER, StandardActivity.class, RegisterFragment.class);
        router.map(ROUTER_MESSAGE, StandardActivity.class, MessageFragment.class);
        router.map(ROUTER_MESSAGE_DETAIL, StandardActivity.class, MessageDetailFragment.class);
        router.map(ROUTER_FAMILY, StandardActivity.class, FamilyFragment.class);
        router.map(ROUTER_EDIT_FAMILY, StandardActivity.class, EditFamilyFragment.class);
        router.map(ROUTER_SETTING, StandardActivity.class, SettingFragment.class);
        router.map(ROUTER_FEEDBACK, StandardActivity.class, FeedbackFragment.class);
        router.map(ROUTER_ABOUT, StandardActivity.class, AboutFragment.class);
        router.map(ROUTER_VERIFY_PHONE_NUM, StandardActivity.class, VerifyPhoneNumFragment.class);
        router.map(ROUTER_MODIFY_PWD, StandardActivity.class, ModifyPwdFragment.class);
        router.map(ROUTER_BIND_PHONE, StandardActivity.class, BindPhoneFragment.class);
        router.map(ROUTER_VERIFY_IDENTIFY, StandardActivity.class, VerifyIdentifyFragment.class);
        router.map(ROUTER_USER_INFO, StandardActivity.class, UserInfoFragment.class);
        router.map(ROUTER_EDIT_USERNAME, StandardActivity.class, EditUserInfoFragment.class);
        router.map(ROUTER_REPORT_DETAIL, StandardActivity.class, ReportDetailFragment.class);
        router.map(ROUTER_DOCTOR_DETAIL, StandardActivity.class, DoctorDetailFragment.class);
        router.map(ROUTER_ASK, StandardActivity.class, AskFragment.class);
        router.map(ROUTER_REPORT_UPLOAD_SUCCESS, StandardActivity.class, ReportUploadSuccessFragment.class);

    }

    /**
     * 获取一个接口，表示通用路由接口，打开普通功能页面.
     *
     * @return 通用路由
     */
    public Router getRouter() {
        return router;
    }

    public void openHome(Context context) {
        router.open(context, ROUTER_HOME);
    }

    public void openWebView(Context context, String url, String title) {
        Bundle bundle = new Bundle();
        bundle.putString(BundleKey.BUNDLE_KEY_WEB_VIEW_URL, url);
        bundle.putString(BundleKey.BUNDLE_KEY_WEB_VIEW_TITLE, title);
        router.open(context, ROUTER_WEBVIEW, bundle);
    }

    public void openLoginWebView(Context context, String url, String title) {
        if (!userManger.isLogin()) {
            router.open(ReportApplication.getCurrentActivity(), ROUTER_LOGIN);
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString(BundleKey.BUNDLE_KEY_WEB_VIEW_URL, url);
        bundle.putString(BundleKey.BUNDLE_KEY_WEB_VIEW_TITLE, title);
        router.open(context, ROUTER_WEBVIEW, bundle);
    }

    static final class InstanceHolder {
        static final AuthRouterManager instance = new AuthRouterManager();
    }
}
