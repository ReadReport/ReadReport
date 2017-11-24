package com.wy.report;

import android.app.Application;
import android.content.Context;

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

    public static ReportApplication getGlobalContext() {
        return globalContext;
    }
}
