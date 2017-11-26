package com.wy.report.manager.router;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.wy.report.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Router {


    public interface Interceptor {
        boolean process(String url);
    }

    private List<Interceptor> interceptors = new ArrayList<>();

    public Router() {
    }

    protected HashMap<String, Class> map = new HashMap<>();

    public void open(Context context, String url) {
        open(context, url, null, true);
    }

    public void open(Context context, String url, Bundle extras) {
        open(context, url, extras, true);
    }

    public void open(Context context, String url, boolean animated) {
        open(context, url, null, animated);
    }

    public void open(Context context, String url, Bundle extras, boolean animated) {
        open(context, url, extras, animated, -1);
    }

    public void open(Context context, String url, Bundle extras, boolean animated, int requestCode) {

        if (context == null) {
            throw new NullPointerException("Param context is null");
        }

        if (TextUtils.isEmpty(url)) {
            throw new IllegalArgumentException("Param url can not be null or empty");
        }

        for (Interceptor interceptor : interceptors) {
            if (interceptor.process(url)) {
                return;
            }
        }

        Class activityClass = map.get(url);
        if (activityClass == null) {
            throw new IllegalStateException("Can not find class mapped with url " + url);
        }

        Intent intent = new Intent(context, activityClass);

        if (extras != null) {
            intent.putExtras(extras);
        }

        if (context instanceof Activity) {
            Activity activity = (Activity) context;

            if (requestCode != 0) {
                activity.startActivityForResult(intent, requestCode);
            } else {
                activity.startActivity(intent);
            }

            if (animated) {
                activity.overridePendingTransition(R.anim.navigtor_push_left_in, R.anim.navigtor_push_left_out);
            }
        } else {
            context.startActivity(intent);
        }
    }

    public void map(String url, Class activityClass) {
        map.put(url, activityClass);
    }

    public void addInterceptor(Interceptor interceptor) {
        interceptors.add(interceptor);
    }

}
