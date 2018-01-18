package com.wy.report.business.dailydetect.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.alibaba.fastjson.annotation.JSONField;
import com.wy.report.base.model.BaseModel;

import java.util.ArrayList;

/**
 *
 * @author cantalou
 * @date 2017-12-31 21:40
 */
public class DailyDetectDataModel extends BaseModel implements Parcelable {

    /**
     * 项目id
     */
    private String id;

    /**
     * 用户id
     */
    @JSONField(name = "member_id")
    private String memberId;

    /**
     * 数动力用户no
     */
    @JSONField(name = "idp_fwjh_no")
    private String no;

    /**
     * 选项类型
     */
    @JSONField(name = "test_type")
    private String testType;

    /**
     * 结果
     */
    private DailyDetectValueModel res;

    /**
     * 消息（弹窗展示）
     */
    @JSONField(name = "message")
    private String message;

    /**
     * 描述
     */
    @JSONField(name = "describe")
    private String describe;

    /**
     * 测试时间
     */
    @JSONField(name = "test_time")
    private long testTime;

    /**
     * 建议
     */
    @JSONField(name = "suggest")
    private String suggest;


    /**
     * 上传类型（2手动 1设备）
     */
    @JSONField(name = "collect_type")
    private String collectType;


    /**
     * 设备型号
     */
    @JSONField(name = "device_type")
    private String deviceType;

    private String[] values;

    public DailyDetectDataModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    public DailyDetectValueModel getRes() {
        return res;
    }

    public void setRes(DailyDetectValueModel res) {
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

    public long getTestTime() {
        return testTime;
    }

    public void setTestTime(long testTime) {
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

    public String[] getValues() {
        return values;
    }

    public void setValues(String[] values) {
        this.values = values;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.memberId);
        dest.writeString(this.no);
        dest.writeString(this.testType);
        dest.writeParcelable(this.res, 0);
        dest.writeString(this.message);
        dest.writeString(this.describe);
        dest.writeLong(this.testTime);
        dest.writeString(this.suggest);
        dest.writeString(this.collectType);
        dest.writeString(this.deviceType);
        dest.writeStringArray(this.values);
    }

    protected DailyDetectDataModel(Parcel in) {
        this.id = in.readString();
        this.memberId = in.readString();
        this.no = in.readString();
        this.testType = in.readString();
        this.res = in.readParcelable(DailyDetectValueModel.class.getClassLoader());
        this.message = in.readString();
        this.describe = in.readString();
        this.testTime = in.readLong();
        this.suggest = in.readString();
        this.collectType = in.readString();
        this.deviceType = in.readString();
        this.values = in.createStringArray();
    }

    public static final Creator<DailyDetectDataModel> CREATOR = new Creator<DailyDetectDataModel>() {
        public DailyDetectDataModel createFromParcel(Parcel source) {
            return new DailyDetectDataModel(source);
        }

        public DailyDetectDataModel[] newArray(int size) {
            return new DailyDetectDataModel[size];
        }
    };
}
