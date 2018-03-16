package com.wy.report.business.read.service;

import com.wy.report.base.model.ResponseModel;
import com.wy.report.business.read.mode.AskMode;
import com.wy.report.business.read.mode.DoctorMode;
import com.wy.report.business.read.mode.ReportDetailMode;
import com.wy.report.business.read.mode.ReportListMode;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by BHM on 17/12/17.
 */
public interface ReadService {

    /**
     * 报告列表获取
     * @param mid 用户id
     * @param page 第几页
     * @param ifAll 默认0拉去单个成员的报告，1拉去所有成员的报告
     * @return
     */
    @GET("/Report/new_get_my_reports")
    Observable<ResponseModel<ReportListMode>> getReportList(@Query("mid") String mid, @Query("page") int page, @Query("if_all") int ifAll);


    /**
     * 提交报告给医生
     * @param reportId
     * @return
     */
    @GET("/Doctor/rep_submit_to_doctor")
    Observable<ResponseModel> sumbitReport2Doctor(@Query("rep_id") String reportId);

    /**
     * 获取医生信息
     * @return
     */
    @GET("/Doctor/get_doctor_info")
    Observable<ResponseModel<DoctorMode>> getDoctorInfo(@Query("doc_id") String docId);


    /**
     * 获取报告明细
     * @return
     */
    @GET("/Report/get_my_member_report_details")
    Observable<ResponseModel<ReportDetailMode>> getReportDetail(@Query("rep_id") String reportId, @Query("report_status") String reportStatus);


    /**
     * 获取聊天详细信息
     * @return
     */
    @GET("/Report/get_user_doctor_conversation")
    Observable<ResponseModel<AskMode>> getAskList(@Query("rep_id") String repId, @Query("page") int page);

    /**
     * 追问
     * @return
     */
    @FormUrlEncoded
    @POST("/Report/user_more_conversation")
    Observable<ResponseModel> ask(@Field("rep_id") String repId, @Field("member_id") String page, @Field("content") String content);


    /**
     * 追问
     * @return
     */
    @GET("/Report/member_reask")
    Observable<ResponseModel> reAsk(@Field("mid") String repId, @Field("qid") String page, @Field("content") String content);


    /**
     * 提醒医生解读报告
     * @param reportId
     * @return
     */
    @GET("/Doctor/reminding_doctor_readed")
    Observable<ResponseModel> remind2Doctor(@Query("rep_id") String reportId);
}
