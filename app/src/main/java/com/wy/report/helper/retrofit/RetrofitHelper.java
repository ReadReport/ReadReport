package com.wy.report.helper.retrofit;

import com.wy.report.helper.retrofit.converter.FastJsonConverterFactory;
import com.wy.report.helper.retrofit.interceptor.AuthInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * @author cantalou
 * @date 2017年11月23日 10:38
 */
public class RetrofitHelper {

    private static class InstanceHolder {
        static final RetrofitHelper instance = new RetrofitHelper();
    }

    private OkHttpClient okHttpClient;

    private Retrofit retrofit;


    private RetrofitHelper() {

        okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addNetworkInterceptor(new AuthInterceptor())
                .build();

        retrofit = new Retrofit.Builder().baseUrl("http://api.vip120.com")
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createAsync())
                .addConverterFactory(FastJsonConverterFactory.create())
                .build();
    }

    public static final RetrofitHelper getInstance() {
        return InstanceHolder.instance;
    }

    public static Retrofit getRetrofit() {
        return InstanceHolder.instance.retrofit;
    }

    public static OkHttpClient getOkHttpClient() {
        return InstanceHolder.instance.okHttpClient;
    }
}
