package com.wy.report.helper.retrofit.subscriber;

import android.content.Context;
import android.widget.Toast;

import com.cantalou.android.util.NetworkUtils;
import com.wy.report.ReportApplication;
import com.wy.report.helper.retrofit.ReportException;

import java.io.IOException;

import rx.Subscriber;

/**
 * @author cantalou
 * @date 2017年11月26日 16:05
 */
public abstract class NetworkSubscriber<T> extends Subscriber<T> {

    private Context context = ReportApplication.getGlobalContext();

    @Override
    public void onStart() {
        if (NetworkUtils.isNetworkAvailable(context)) {
        }
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof ReportException) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }else if(e instanceof IOException){
            Toast.makeText(context, "网络异常", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNext(T t) {

    }
}
