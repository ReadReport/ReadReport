package com.wy.report.business.dailydetect.service;

import com.wy.report.base.model.ResponseModel;
import com.wy.report.business.dailydetect.model.DailyDetectDataModel;
import com.wy.report.business.dailydetect.model.RecordResultModel;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author cantalou
 * @date 2017-12-31 21:26
 */
public interface DailyDetectService {

    @GET("/DailyMonitor/get_recordes_by_type2")
    Observable<ResponseModel<RecordResultModel>> getDetectData(@Query("mid") String uid, @Query("test_type") int type);

    /**
     * 1
     *
     * @param mid       当前登录会员id
     * @param type      监测类型
     * @param lowPress  舒张压（低压）
     * @param highPress 收缩压（高压）
     * @param pulse     心率
     * @return
     */
    @POST("/DailyMonitor/do_idp_records")
    @FormUrlEncoded
    Observable<ResponseModel> recordBloodPressure(@Field("mid") String mid, @Field("test_type") int type, @Field("low_press") String lowPress,
                                                  @Field("high_press") String highPress, @Field("pulse") String pulse);

    /**
     * 2
     *
     * @param mid            当前登录会员id
     * @param type           监测类型
     * @param typeCode       血糖类别，如此为空则根据typeDetailCode判断。空腹血糖:1,餐后血糖:2
     * @param typeDetailCode #血糖类别明细。空腹1、早餐后2、午餐前3、午餐后4、晚餐前5、晚餐后6、睡前7、夜间8、随机0
     * @param sugar          血糖值，单位mmol/L
     * @return
     */
    @POST
    @FormUrlEncoded
    Observable<ResponseModel> recordBloodSugar(@Field("mid") String mid, @Field("test_type") int type, @Field("type_code") String typeCode,
                                               @Field("type_detail_code") String typeDetailCode, @Field("sugar_value") String sugar);


    /**
     * 3
     *
     * @param mid    当前登录会员id
     * @param type   监测类型
     * @param high   身高
     * @param weight 体重
     * @return
     */
    @POST
    @FormUrlEncoded
    Observable<ResponseModel> recordBMI(@Field("mid") String mid, @Field("test_type") int type, @Field("hight_value") String high,
                                        @Field("weight_value") String weight);

    /**
     * 6
     *
     * @param mid     当前登录会员id
     * @param type    监测类型
     * @param bodyFat 体脂率
     * @return
     */
    @POST
    @FormUrlEncoded
    Observable<ResponseModel> recordBodyFat(@Field("mid") String mid, @Field("test_type") int type, @Field("body_fat_value") String bodyFat);

    /**
     * 7
     *
     * @param mid  当前登录会员id
     * @param type 监测类型
     * @param chol 总胆固醇
     * @param trig 甘油三酯
     * @param hdl  高密度
     * @param ldl  低密度
     * @return
     */
    @POST
    @FormUrlEncoded
    Observable<ResponseModel> recordBloodFat(@Field("mid") String mid, @Field("test_type") int type, @Field("chol_value") String chol,
                                             @Field("trig_value") String trig, @Field("hdl_value") String hdl, @Field("ldl_value") String ldl);


    /**
     * 删除测量记录
     *
     * @return
     */
    @GET("/DailyMonitor/member_idp_record_delete")
    Observable<ResponseModel> deleteRecord(@Query("member_id") String uid, @Query("id") String mid);
}
