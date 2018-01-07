package com.wy.report.business.dailydetect.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.alibaba.fastjson.annotation.JSONField;
import com.wy.report.base.model.BaseModel;

/*
 *
 * @author cantalou
 * @date 2017-12-31 21:40
 */
public class DailyDetectDataModel extends BaseModel implements Parcelable{

    public static final Creator<DailyDetectDataModel> CREATOR = new Creator<DailyDetectDataModel>() {
        @Override
        public DailyDetectDataModel createFromParcel(Parcel source) {
            return new DailyDetectDataModel(source);
        }

        @Override
        public DailyDetectDataModel[] newArray(int size) {
            return new DailyDetectDataModel[size];
        }
    };
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

    public DailyDetectDataModel() {
    }

    protected DailyDetectDataModel(Parcel in) {
        this.id = in.readString();
        this.uid = in.readString();
        this.idNo = in.readString();
        this.testType = in.readString();
        this.res = in.readString();
        this.message = in.readString();
        this.describe = in.readString();
        this.testTime = in.readString();
        this.suggest = in.readString();
        this.collectType = in.readString();
        this.deviceType = in.readString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getTestTime() {
        return testTime;
    }

    public void setTestTime(String testTime) {
        this.testTime = testTime;
    }

    public String getSuggest() {
        return suggest;
    }

    public void setSuggest(String suggest) {
        this.suggest = suggest;
    }

    public String getCollectType() {
        return collectType;
    }

    public void setCollectType(String collectType) {
        this.collectType = collectType;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.uid);
        dest.writeString(this.idNo);
        dest.writeString(this.testType);
        dest.writeString(this.res);
        dest.writeString(this.message);
        dest.writeString(this.describe);
        dest.writeString(this.testTime);
        dest.writeString(this.suggest);
        dest.writeString(this.collectType);
        dest.writeString(this.deviceType);
    }
}
