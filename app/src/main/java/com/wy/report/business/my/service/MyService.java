package com.wy.report.business.my.service;

import com.wy.report.base.model.ResponseModel;
import com.wy.report.business.my.model.AddMemberMode;
import com.wy.report.business.my.model.FamilyItemMode;
import com.wy.report.business.my.model.MessageItemMode;
import com.wy.report.business.my.model.MessageListMode;
import com.wy.report.business.my.model.RegisterMode;
import com.wy.report.business.my.model.UserModel;
import com.wy.report.business.my.model.VerifyPhoneNumMode;

import java.util.List;

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
     * 获取验证码 for 注册
     *
     * @param mobile
     * @return
     */
    @GET("/Member/dbg_register_send_code_before_action")
    Observable<ResponseModel<UserModel>> getVerifyCodeForRegister(@Query("mobile") String mobile);


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
    Observable<ResponseModel<MessageItemMode>> getMessageDetail(@Query("mid") String mid, @Query("mes_id") String msgId);

    /**
     * 删除消息
     *
     * @return
     */
    @GET("/Message/del_messges")
    Observable<ResponseModel> delMessage(@Query("mid") String mid, @Query("single_del_messges") String msgId);


    /**
     * 获取家庭成员列表
     *
     * @return
     */
    @GET("/Member/get_family_member")
    Observable<ResponseModel<List<FamilyItemMode>>> getFamily(@Query("mid") String mid);


    /**
     * 编辑家庭成员信息
     *
     * @return
     */
    @FormUrlEncoded
    @POST("/Member/edit_family_member")
    Observable<ResponseModel> editFamilyMember(@Field("id") String id, @Field("name") String name, @Field("sex") String sex, @Field("birthday") String birthday, @Field("relationship") String relationship, @Field("mobile") String mobile, @Field("id_card") String idCard);

    /**
     * 添加家庭成员信息
     *
     * @return
     */
    @FormUrlEncoded
    @POST("/Member/add_family_member")
    Observable<ResponseModel<AddMemberMode>> addFamilyMember(@Field("mid") String mid, @Field("name") String name, @Field("sex") String sex, @Field("birthday") String birthday, @Field("relationship") String relationship, @Field("mobile") String mobile, @Field("id_card") String idCard);

    /**
     * 意见反馈
     *
     * @return
     */
    @FormUrlEncoded
    @POST("/Consult/feedback")
    Observable<ResponseModel> feedback(@Field("mid") String mid, @Field("content") String content);

    /**
     * 验证手机
     *
     * @return
     */
    @GET("/Member/verify_member")
    Observable<ResponseModel<VerifyPhoneNumMode>> verifyPhone(@Query("username") String mobile, @Query("verify") String pwd);

    /**
     * 重置密码
     *
     * @return
     */
    @GET("/Member/reset_password")
    Observable<ResponseModel> modifyPwd(@Query("username") String mobile, @Query("password") String pwd);

    /**
     * 绑定手机
     *
     * @return
     */
    @GET("/Member/bind_member_mob")
    Observable<ResponseModel> bindPhone(@Query("mid") String mid,@Query("mobile") String mobile, @Query("verify") String verify);


}