package com.wy.report.business.my.service;

import com.wy.report.base.model.ResponseModel;
import com.wy.report.business.my.model.MessageItemMode;
import com.wy.report.business.my.model.MessageListMode;
import com.wy.report.business.my.model.RegisterMode;
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
     * 密码登录
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
    @GET("/Member/dbg_get_verify_code")
    Observable<ResponseModel<UserModel>> getVerifyCode(@Query("mobile") String mobile);


    /**
     * 验证码登录
     *
     * @return
     */
    @FormUrlEncoded
    @POST("/Member/dbg_sms_login")
    Observable<ResponseModel<UserModel>> loginByVerifyCode(@Field("mobile") String mobile, @Field("verify") String pwd);

    /**
     * 注册
     *
     * @return
     */
    @FormUrlEncoded
    @POST("/Member/member_register")
    Observable<ResponseModel<RegisterMode>> register(@Field("mobile") String mobile, @Field("password") String pwd, @Field("verify") String verify);



    /**
     * 获取消息
     *
     * @return
     */
    @GET("/Message/get_messages")
    Observable<ResponseModel<MessageListMode>> getMessage(@Query("mid") String mid);

    /**
     * 消息详情
     *
     * @return
     */
    @GET("/Message/get_message_details")
    Observable<ResponseModel<MessageItemMode>> getMessageDetail(@Query("mid") String mid,@Query("mes_id") String msgId);
}
