package com.wy.report.business.upload.service;

import com.wy.report.base.model.ResponseModel;
import com.wy.report.business.upload.model.UnitModel;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/*
 *
 * @author cantalou
 * @date 2017-12-11 22:35
 */
public interface HospitalService {

    @GET("/Doctor/get_chain_units")
    Observable<ResponseModel<List<UnitModel>>> getChainUnits();

    @GET("/Doctor/get_not_chain_units")
    Observable<ResponseModel<List<UnitModel>>> getNotChainUnits();
}
