package com.wy.report.business.my.service;

import com.wy.report.base.model.ResponseModel;
import com.wy.report.business.my.model.UserModel;
import com.wy.report.business.read.mode.ReportListMode;

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
     * 获取报告列表
     *
     * @param mobile
     * @return
     */
    @FormUrlEncoded
    @POST("/Member/dbg_login")
    Observable<ResponseModel<UserModel>> loginByPwd(@Field("mobile") String mobile, @Field("password") String pwd);
}
