package com.wy.report.business.auth.service;

import com.wy.report.base.model.BaseModel;
import com.wy.report.business.auth.model.TokenModel;
import com.wy.report.business.auth.model.User;

import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 鉴权接口
 *
 * @author cantalou
 * @date 2017年11月23日 15:36
 */
public interface AuthService {

    /**
     * 获取token
     *
     * @param appId
     * @param secret
     * @return
     */
    @GET("/Index/getToken")
    Observable<BaseModel<TokenModel>> getToken(@Query("appid") String appId, @Query("secret") String secret);

    /**
     * 用户名密码登录
     * @return
     */
    @POST("/Member/dbg_login")
    Observable<BaseModel<User>> login(@Field("mobile")String mobile, @Field("password") String password);


    /**
     * 获取短信验证码
     */
    @GET("/Member/dbg_get_verify_code")
    Observable<BaseModel> getSmsVerifyCode(@Query("mobile")String mobile);

}
