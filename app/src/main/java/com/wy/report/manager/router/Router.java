package com.wy.report.manager.router;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.wy.report.R;
import com.wy.report.base.constant.BundleKey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Router {


    protected HashMap<String, Item> map = new HashMap<>();
    private List<Interceptor> interceptors = new ArrayList<>();

    public Router() {
    }

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

        Item item = map.get(url);
        if (item == null) {
            throw new IllegalStateException("Can not find class or fragment mapped with url " + url);
        }

        Intent intent = new Intent(context, item.activityClass);

        if (extras != null) {
            intent.putExtras(extras);
        }

        if (item.fragmentClass != null) {
            intent.putExtra(BundleKey.BUNDLE_KEY_FRAGMENT_CLASS_NAME, item.fragmentClass.getName());
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

    public void map(String url, Class<? extends Activity> activityClazz) {
        map.put(url, new Item(activityClazz));
    }

    public void map(String url, Class<? extends Activity> activityClazz, Class<?extends Fragment>fragmentClass) {
        map.put(url, new Item(activityClazz, fragmentClass));
    }

    public void addInterceptor(Interceptor interceptor) {
        interceptors.add(interceptor);
    }

    public interface Interceptor {
        boolean process(String url);
    }

    public static class Item {

        public Class activityClass;

        public Class fragmentClass;

        public Item(Class activityClass) {
            this.activityClass = activityClass;
        }

        public Item(Class activityClass, Class fragmentClass) {
            this.activityClass = activityClass;
            this.fragmentClass = fragmentClass;
        }
    }

}
