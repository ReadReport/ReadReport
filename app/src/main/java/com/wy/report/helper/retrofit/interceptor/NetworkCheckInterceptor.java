package com.wy.report.helper.retrofit.interceptor;

import android.content.Context;

import com.cantalou.android.util.NetworkUtils;
import com.wy.report.ReportApplication;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * @author cantalou
 * @date 2017年11月24日 15:38
 */
public class NetworkCheckInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        Context context = ReportApplication.getGlobalContext();

        if (NetworkUtils.isNetworkAvailable(context)) {

        }

        return null;
    }
}
