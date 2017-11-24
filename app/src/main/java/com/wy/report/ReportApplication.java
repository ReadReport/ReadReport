package com.wy.report;

import android.app.Application;
import android.content.Context;

import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.MobclickAgent;
import com.wy.report.manager.auth.AuthManager;

/**
 * @author cantalou
 * @date 2017年11月23日 11:28
 */
public class ReportApplication extends Application {

    public static ReportApplication globalContext;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        globalContext = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MobclickAgent.setCatchUncaughtExceptions(true);
        AuthManager.getInstance();
    }

    public static ReportApplication getGlobalContext() {
        return globalContext;
    }
}
