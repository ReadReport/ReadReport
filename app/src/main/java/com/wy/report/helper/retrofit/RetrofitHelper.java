package com.wy.report.helper.retrofit;

import com.wy.report.helper.retrofit.converter.json.FastJsonConverterFactory;
import com.wy.report.helper.retrofit.converter.part.PartConverterFactory;
import com.wy.report.helper.retrofit.interceptor.AuthInterceptor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author cantalou
 * @date 2017年11月23日 10:38
 */
@SuppressWarnings({"unused", "unchecked"})
public class RetrofitHelper {

    private OkHttpClient okHttpClient;
    private Retrofit retrofit;
    private HashMap<Class, Object> serviceProxyCache = new HashMap<>();

    private RetrofitHelper() {

        okHttpClient = new OkHttpClient().newBuilder()
                                         .connectTimeout(30, TimeUnit.SECONDS)
                                         .addNetworkInterceptor(new AuthInterceptor())
                                         .build();

        retrofit = new Retrofit.Builder().baseUrl("http://api.vip120.com")
                                         .client(okHttpClient)
                                         .addCallAdapterFactory(RxJavaCallAdapterFactory.createAsync())
                                         .addConverterFactory(FastJsonConverterFactory.create())
                                         .addConverterFactory(PartConverterFactory.create())
                                         .build();
    }

    public static RetrofitHelper getInstance() {
        return InstanceHolder.instance;
    }

    public <T> T create(Class<T> clazz) {
        T proxy = (T) serviceProxyCache.get(clazz);
        if (proxy != null) {
            return proxy;
        }
        proxy = (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, new ProxyHandler(retrofit.create(clazz)));
        serviceProxyCache.put(clazz, proxy);
        return proxy;
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    private static class InstanceHolder {
        static final RetrofitHelper instance = new RetrofitHelper();
    }

    private static class ProxyHandler implements InvocationHandler {

        private Object target;

        ProxyHandler(Object target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
            Object result = method.invoke(target, objects);
            if (result instanceof Observable) {
                return ((Observable) result).retryWhen(new RetryWhenException())
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread());
            }
            return result;
        }
    }
}
