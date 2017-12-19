package com.wy.report.business.read.service;

import com.wy.report.base.model.ResponseModel;
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
     * 获取报告列表
     *
     * @param mid
     * @return
     */
    @GET("/Report/get_my_reports")
    Observable<ResponseModel<List<ReportListMode.ReportItem>>> getReportList(@Query("mid") long mid);
}
