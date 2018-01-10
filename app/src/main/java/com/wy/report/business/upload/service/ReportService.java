package com.wy.report.business.upload.service;

import com.wy.report.base.model.ResponseModel;
import com.wy.report.business.upload.model.UploadModel;

import java.io.File;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 报告接口
 *
 * @author cantalou
 * @date 2017年12月22日 18:42
 * <p>
 */
public interface ReportService {

    @POST("/Report/do_upload_report")
    @Multipart
    Observable<ResponseModel<UploadModel>> submitReport(@Part("mid") String uid, @Part("upload_from") String uploadFrom, @Part("tj_hospital") String hospitalId,
                                                        @Part("tj_date") String date, @Part("remark") String remark, @Part MultipartBody.Part[] parts);

    @GET("/Report/do_upload_report")
    @Multipart
    Observable<ResponseModel<UploadModel>> queryReport(@Part("mid") String uid, @Part("tj_hospital") String hospitalId,
                                                        @Part("tj_date") String account, @Part("remark") String password);
}
