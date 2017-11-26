package com.wy.report.business.auth.service;

import com.wy.report.base.model.BaseModel;
import com.wy.report.business.auth.model.TokenModel;

import okhttp3.Call;
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

    @GET("/Index/getToken")
    Observable<BaseModel<TokenModel>> getToken(@Query("appid") String appId, @Query("secret") String secret);

}
