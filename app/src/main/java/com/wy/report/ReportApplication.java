package com.wy.report;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.ViewTarget;
import com.umeng.analytics.MobclickAgent;
import com.wy.report.manager.auth.AuthManager;
import com.wy.report.util.Utils;

/**
 * @author cantalou
 * @date 2017年11月23日 11:28
 */
public class ReportApplication extends Application {

    private static ReportApplication globalContext;

    private static Activity currentActivity;

    public static ReportApplication getGlobalContext() {
        return globalContext;
    }

    public static Activity getCurrentActivity() {
        return currentActivity;
    }

    public static void setCurrentActivity(Activity currentActivity) {
        ReportApplication.currentActivity = currentActivity;
    }

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
        Utils.init(this);
        ViewTarget.setTagId(R.id.glide_tag_id);
    }
}
