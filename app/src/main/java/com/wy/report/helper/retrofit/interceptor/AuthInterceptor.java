package com.wy.report.helper.retrofit.interceptor;

import com.wy.report.business.auth.model.TokenModel;
import com.wy.report.manager.auth.AuthManager;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 鉴权拦截器<br/><br/>
 * 所有的接口调用都要带上token和timestamp(token是调用凭证，timestamp是过期的时间戳, token是有2个小时的有效期的,过期后应该再次调用)<br/>
 * 默认情况下调用getToken的方法只需要appid和secret, 这个有系统分配的, 剩下其他所有的api接口调用都要带上appid以及token和timestamp三个参数，特别要注意token的时效性。<br/><br/>
 * 如果调用方法是get的, 那么appid、token、timestamp也是get方式发送给接口<br/>
 * 如果调用方法是post的, 那么appid、token、timestamp也是post方式发送给接口<br/>
 *
 * @author cantalou
 * @date 2017年11月23日 11:17
 */
public class AuthInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request original = chain.request();
        HttpUrl httpUrl = original.url();
        String url = httpUrl.toString();
        if (url.contains("/Index/getToken")) {
            return chain.proceed(original);
        }

        AuthManager authManager = AuthManager.getInstance();
        if (authManager.isTokenRefreshing()) {
            authManager.syncRefreshToken();
        }

        TokenModel tokenModel = authManager.getTokenModel();
        LinkedHashMap<String, String> nameValuePair = new LinkedHashMap<>(4);
        nameValuePair.put("appid", AuthManager.APP_ID);
        nameValuePair.put("secret", AuthManager.APP_SECRET);
        nameValuePair.put("token", tokenModel.getToken());
        nameValuePair.put("timestamp", String.valueOf(tokenModel.getTimestamp()));

        Request request = null;
        if ("GET".equalsIgnoreCase(original.method())) {
            HttpUrl.Builder httpBuilder = httpUrl.newBuilder();
            for (Map.Entry<String, String> entry : nameValuePair.entrySet()) {
                httpBuilder.addQueryParameter(entry.getKey(), entry.getValue());
            }
            request = original.newBuilder()
                              .url(httpBuilder.build())
                              .build();
        } else {

            RequestBody body = original.body();
            if (body instanceof FormBody) {
                FormBody formBody = (FormBody) request.body();
                FormBody.Builder bodyBuilder = new FormBody.Builder();
                for (int i = 0; i < formBody.size(); i++) {
                    bodyBuilder.addEncoded(formBody.encodedName(i), formBody.encodedValue(i));
                }
                for (Map.Entry<String, String> entry : nameValuePair.entrySet()) {
                    bodyBuilder.addEncoded(entry.getKey(), entry.getValue());
                }
                request = request.newBuilder()
                                 .post(bodyBuilder.build())
                                 .build();
            } else if (body instanceof MultipartBody) {
                MultipartBody formBody = (MultipartBody) request.body();
                MultipartBody.Builder bodyBuilder = new MultipartBody.Builder(formBody.boundary()).setType(formBody.type());
                for (MultipartBody.Part part : formBody.parts()) {
                    bodyBuilder.addPart(part);
                }
                for (Map.Entry<String, String> entry : nameValuePair.entrySet()) {
                    bodyBuilder.addFormDataPart(entry.getKey(), entry.getValue());
                }
                request = request.newBuilder()
                                 .post(bodyBuilder.build())
                                 .build();
            }
        }

        return chain.proceed(request);
    }
}
