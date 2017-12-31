package com.wy.report.business.dailydetect.service;

import com.wy.report.base.model.ResponseModel;
import com.wy.report.business.dailydetect.model.DetectModel;

import retrofit2.http.GET;
import rx.Observable;

/*
 *
 * @author cantalou
 * @date 2017-12-31 21:26
 */
public interface DailyDetectService {

    @GET("/DailyMonitor/get_recordes_by_typ")
    Observable<ResponseModel<DetectModel>> getDetectData();
}
