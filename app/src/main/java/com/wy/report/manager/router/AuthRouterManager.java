package com.wy.report.manager.router;

import android.content.Context;

import com.wy.report.base.activity.StandardActivity;
import com.wy.report.business.home.activity.HomeActivity;
import com.wy.report.business.upload.activity.UploadReportActivity;
import com.wy.report.business.upload.fragment.ReportUploadFragment;
import com.wy.report.business.upload.fragment.ReportUploadQueryFragment;
import com.wy.report.manager.auth.UserManger;

public class AuthRouterManager {

    public static final String PUBLIC_ACTIVITY_PREFIX = "public";

    public static final String LOGIN_ACTIVITY_PREFIX = "login";
    /**
     * 主页面
     */
    public static final String PUBLIC_ROUTER_HOME = PUBLIC_ACTIVITY_PREFIX + "/home";
    /**
     * 上传查询报告界面
     */
    public static final String LOGIN_ROUTER_UPLOAD_QUERY_REPORT = LOGIN_ACTIVITY_PREFIX + "/upload_query_report";
    /**
     * 上传报告界面
     */
    public static final String LOGIN_ROUTER_UPLOAD_REPORT = LOGIN_ACTIVITY_PREFIX + "/upload_report";
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
                    return false;
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
     * 配置公用路由映射.
     */
    private void configPublicRouterMap() {
        router.map(PUBLIC_ROUTER_HOME, HomeActivity.class);
    }

    private void configLoginRouterMap() {
        router.map(LOGIN_ROUTER_UPLOAD_QUERY_REPORT, StandardActivity.class, ReportUploadQueryFragment.class);
        router.map(LOGIN_ROUTER_UPLOAD_REPORT, StandardActivity.class, ReportUploadFragment.class);
    }

    /**
     * 配置系统路由映射.
     */
    private void configRouterMap() {
        configPublicRouterMap();
        configLoginRouterMap();
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
        router.open(context, PUBLIC_ROUTER_HOME);
    }

    static final class InstanceHolder {
        static final AuthRouterManager instance = new AuthRouterManager();
    }
}
