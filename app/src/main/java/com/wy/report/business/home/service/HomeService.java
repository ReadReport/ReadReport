package com.wy.report.business.home.service;

import com.wy.report.base.model.BaseModel;
import com.wy.report.business.home.model.FeedModel;
import com.wy.report.business.home.model.HomeReportModel;

import retrofit2.http.GET;
import rx.Observable;

/**
 * @author cantalou
 * @date 2017年11月29日 18:39
 */
public interface HomeService {

    @GET("/Doctor/get_total_report_unscramble_num")
    Observable<BaseModel<HomeReportModel>> getTotalNumber();

    @GET("/Doctor/get_total_report_unscramble_num")
    Observable<BaseModel<FeedModel>> getFeed();
}
