package com.wy.report.manager.router;

import android.content.Context;

import com.wy.report.business.home.activity.HomeActivity;

public class AuthRouterManager {

    public static final String PUBLIC_ACTIVITY_PREFIX = "public";

    public static final String LOGIN_ACTIVITY_PREFIX = "login";

    private Router router;

    /**
     * 主页面
     */
    public static final String PUBLIC_ROUTER_HOME = PUBLIC_ACTIVITY_PREFIX + "/home";

    static final class InstanceHolder {
        static final AuthRouterManager instance = new AuthRouterManager();
    }

    public static AuthRouterManager getInstance() {
        return InstanceHolder.instance;
    }

    /**
     * Instantiates a new router manager.
     */
    private AuthRouterManager() {
        router = new Router();
        router.addInterceptor(new Router.Interceptor() {
            @Override
            public boolean process(String url) {
                if (url.startsWith(LOGIN_ACTIVITY_PREFIX)) {
                    return true;
                }
                return false;
            }
        });
        configRouterMap();
    }

    /**
     * 配置公用路由映射.
     */
    private void configPublicRouterMap() {

        // 配置主界面
        router.map(PUBLIC_ROUTER_HOME, HomeActivity.class);


    }

    private void configLoginRouterMap() {

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
}
