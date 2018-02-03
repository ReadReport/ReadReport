package com.wy.report.helper.retrofit;

import com.wy.report.ReportApplication;
import com.wy.report.helper.retrofit.converter.json.FastJsonConverterFactory;
import com.wy.report.helper.retrofit.converter.part.PartConverterFactory;
import com.wy.report.helper.retrofit.interceptor.AuthInterceptor;

import org.apache.http.conn.ssl.AllowAllHostnameVerifier;

import java.io.File;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
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

    public static  final String BASE_URL = "http://api.vip120.com";

    private OkHttpClient okHttpClient;
    private Retrofit retrofit;
    private HashMap<Class, Object> serviceProxyCache = new HashMap<>();

    private RetrofitHelper() {

        try {

            ReportApplication context = ReportApplication.getGlobalContext();
            File httpCacheDirectory = new File(context.getCacheDir(), "responses");
            Cache cache = new Cache(httpCacheDirectory, 20 * 1024 * 1024);

            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] x509Certificates,
                        String s) throws java.security.cert.CertificateException {
                }

                @Override
                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] x509Certificates,
                        String s) throws java.security.cert.CertificateException {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[]{};
                }
            }};

            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());

            okHttpClient = new OkHttpClient().newBuilder()
                                             .connectTimeout(30, TimeUnit.SECONDS)
                                             .addNetworkInterceptor(new AuthInterceptor())
                                             .cache(cache)
                                             .hostnameVerifier(new AllowAllHostnameVerifier())
                                             .sslSocketFactory(sc.getSocketFactory())
                                             .build();

            retrofit = new Retrofit.Builder().baseUrl("http://api.vip120.com")
                                             .client(okHttpClient)
                                             .addCallAdapterFactory(RxJavaCallAdapterFactory.createAsync())
                                             .addConverterFactory(FastJsonConverterFactory.create())
                                             .addConverterFactory(PartConverterFactory.create())
                                             .build();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
