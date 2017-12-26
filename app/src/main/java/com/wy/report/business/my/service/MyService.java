package com.wy.report.business.my.service;

import com.wy.report.base.model.ResponseModel;
import com.wy.report.business.my.model.UserModel;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by BHM on 17/12/17.
 */
public interface MyService {

    /**
     * 登录
     *
     * @param mobile
     * @return
     */
    @FormUrlEncoded
    @POST("/Member/dbg_login")
    Observable<ResponseModel<UserModel>> loginByPwd(@Field("mobile") String mobile, @Field("password") String pwd);

    /**
     * 获取验证码
     *
     * @param mobile
     * @return
     */
    @FormUrlEncoded
    @GET("/Member/dbg_get_verify_code")
    Observable<ResponseModel<UserModel>> getVerifyCode(@Query("mobile") String mobile);
}
