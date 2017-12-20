package com.wy.report.helper.retrofit.subscriber;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.widget.Toast;

import com.cantalou.android.util.Log;
import com.cantalou.android.util.NetworkUtils;
import com.wy.report.ReportApplication;
import com.wy.report.helper.retrofit.ReportException;

import java.io.IOException;
import java.lang.ref.SoftReference;

import rx.Subscriber;

/**
 * @author cantalou
 * @date 2017年11月26日 16:05
 */
public abstract class NetworkSubscriber<T> extends Subscriber<T> {

    private Context context = ReportApplication.getGlobalContext();

    protected SoftReference<SubscriberCallback<T>> handler;

    public NetworkSubscriber(SubscriberCallback<T> networkFragment) {
        this.handler = new SoftReference<>(networkFragment);
    }

    @Override
    public void onStart() {
        if (NetworkUtils.isNetworkAvailable(context)) {
        }
        SubscriberCallback<T> callback = handler.get();
        if (callback != null) {
            callback.handleStart();
        }
    }

    @Override
    public void onCompleted() {
    }

    @Override
    @CallSuper
    public void onError(Throwable e) {
        Log.e(e);
        if (e instanceof ReportException) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT)
                 .show();
        } else if (e instanceof IOException) {
            Toast.makeText(context, "网络异常", Toast.LENGTH_SHORT)
                 .show();
        }
        SubscriberCallback<T> callback = handler.get();
        if (callback != null) {
            callback.handleError(e);
        }
    }


    @Override
    @CallSuper
    public void onNext(T t) {
        SubscriberCallback<T> callback = handler.get();
        if (callback != null) {
            callback.handleSuccess(t);
        }
    }
}
