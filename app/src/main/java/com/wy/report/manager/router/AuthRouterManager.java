package com.wy.report.manager.router;

import android.content.Context;

import com.wy.report.base.activity.StandardActivity;
import com.wy.report.business.family.fragment.FamilyMemberSelectFragment;
import com.wy.report.business.home.activity.HomeActivity;
import com.wy.report.business.other.fragment.PictureFragment;
import com.wy.report.business.upload.fragment.ReportQueryFragment;
import com.wy.report.business.upload.fragment.ReportUploadFragment;
import com.wy.report.business.upload.fragment.ReportUploadQueryFragment;
import com.wy.report.manager.auth.UserManger;

public class AuthRouterManager {

    public static final String PUBLIC_ACTIVITY_PREFIX = "public";

    public static final String LOGIN_ACTIVITY_PREFIX = "login";

    /**
     * 主页面
     */
    public static final String ROUTER_HOME = PUBLIC_ACTIVITY_PREFIX + "/home";

    /**
     * 上传查询报告界面
     */
    public static final String ROUTER_REPORT_UPLOAD_QUERY = LOGIN_ACTIVITY_PREFIX + "/report_upload_query";

    /**
     * 上传报告界面
     */
    public static final String ROUTER_REPORT_UPLOAD = LOGIN_ACTIVITY_PREFIX + "/report_upload";

    /**
     * 上传报告界面
     */
    public static final String ROUTER_REPORT_QUERY = LOGIN_ACTIVITY_PREFIX + "/report_query";

    /**
     * 图片预览界面
     */
    public static final String ROUTER_OTHER_PICTURE_PREVIEW = LOGIN_ACTIVITY_PREFIX + "/other_picture_preview";

    /**
     * 家庭成员选择界面
     */
    public static final String ROUTER_FAMILY_MEMBER_SELECT = LOGIN_ACTIVITY_PREFIX + "/family_member_select";

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
     * 配置系统路由映射.
     */
    private void configRouterMap() {
        router.map(ROUTER_HOME, HomeActivity.class);
        router.map(ROUTER_REPORT_UPLOAD_QUERY, StandardActivity.class, ReportUploadQueryFragment.class);
        router.map(ROUTER_REPORT_UPLOAD, StandardActivity.class, ReportUploadFragment.class);
        router.map(ROUTER_REPORT_QUERY, StandardActivity.class, ReportQueryFragment.class);
        router.map(ROUTER_OTHER_PICTURE_PREVIEW, StandardActivity.class, PictureFragment.class);
        router.map(ROUTER_FAMILY_MEMBER_SELECT, StandardActivity.class, FamilyMemberSelectFragment.class);
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

    static final class InstanceHolder {
        static final AuthRouterManager instance = new AuthRouterManager();
    }
}
