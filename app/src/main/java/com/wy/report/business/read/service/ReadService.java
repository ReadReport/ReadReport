package com.wy.report.business.read.service;

import com.wy.report.base.model.ResponseModel;
import com.wy.report.business.read.mode.ReportItemMode;
import com.wy.report.business.read.mode.ReportListMode;

import java.util.List;

import retrofit2.http.GET;
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
    @GET("/Report/do_submit_my_report")
    Observable<ResponseModel> sumbitReport2Doctor(@Query("rep_id") String reportId);

}
