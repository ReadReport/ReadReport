package com.wy.report.business.home.service;

import com.wy.report.base.model.BaseModel;
import com.wy.report.business.home.model.FeedModel;

import retrofit2.http.GET;
import rx.Observable;

/**
 * @author cantalou
 * @date 2017年11月29日 18:39
 */
public interface HomeService {

    @GET("")
    public Observable<BaseModel<FeedModel>> getFeed();
}
