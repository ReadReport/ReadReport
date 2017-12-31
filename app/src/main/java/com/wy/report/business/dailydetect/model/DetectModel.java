package com.wy.report.business.dailydetect.model;

import com.alibaba.fastjson.annotation.JSONField;

/*
 *
 * @author cantalou
 * @date 2017-12-31 21:40
 */
public class DetectModel {

    private String id;

    @JSONField(name = "member_id")
    private String uid;

    @JSONField(name = "idp_fwjh_no")
    private String idNo;

    @JSONField(name = "test_type")
    private String testType;

    private String res;

    private String message;

    private String describe;

    @JSONField(name = "test_time")
    private String testTime;

    private String suggest;

    /**
     * 上传类型（2手动 1设备）
     */
    @JSONField(name = "collect_type")
    private String collectType;

    @JSONField(name = "device_type")
    private String deviceType;
}
