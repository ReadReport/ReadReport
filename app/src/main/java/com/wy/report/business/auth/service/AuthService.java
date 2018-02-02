package com.wy.report.business.auth.service;

import com.wy.report.base.model.ResponseModel;
import com.wy.report.business.auth.model.TokenModel;
import com.wy.report.business.auth.model.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
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
    Observable<ResponseModel<TokenModel>> getToken(@Query("appid") String appId, @Query("secret") String secret);

    /**
     * 同步获取token
     *
     * @param appId
     * @param secret
     * @return
     */
    @GET("/Index/getToken")
    Call<ResponseModel<TokenModel>> syncGetToken(@Query("appid") String appId, @Query("secret") String secret);

    /**
     * 用户名密码登录
     * @return
     */
    @POST("/Member/dbg_login")
    @FormUrlEncoded
    Observable<ResponseModel<User>> login(@Field("mobile")String mobile, @Field("password") String password);


    /**
     * 获取短信验证码
     */
    @GET("/Member/dbg_get_verify_code")
    Observable<ResponseModel> getSmsVerifyCode(@Query("mobile")String mobile);

    /**
     * 验证APP是否允许运行
     * @return
     */
    @GET("https://raw.githubusercontent.com/cantalou/Report/master/checkService")
    Observable<ResponseModel> checkService();

}
