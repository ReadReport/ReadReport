package com.wy.report.business.upload.service;

import com.wy.report.base.model.ResponseModel;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 报告接口
 *
 * @author cantalou
 * @date 2017年12月22日 18:42
 * <p>
 */
public interface ReportService {

    /**
     * mid:当前体检人员的id
     * upload_from:设备来源
     * tj_hospital：体检医院
     * tj_date：体检时间
     * 上传文件的二进制流
     * remark备注
     *
     * @param uid
     * @return
     */
    @POST("/Report/do_upload_report")
    @FormUrlEncoded
    Observable<ResponseModel> submitReport(@Field("mid") int uid, @Field("upload_from") String uploadFrom, @Field("tj_hospital") String hospitalId,
                                           @Field("tj_date") String date,@Field("remark") String remark);
}
