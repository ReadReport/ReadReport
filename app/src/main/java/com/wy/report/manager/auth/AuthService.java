package com.wy.report.manager.auth;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 鉴权接口
 *
 * @author cantalou
 * @date 2017年11月23日 15:36
 */
public interface AuthService {

    @GET("/Index/refreshToken")
    Observable<TokenInfo> getToken(@Query("appid") String appId, @Query("secret") String secret);
}
