package com.wy.report.business.dailydetect.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.wy.report.base.model.BaseModel;

/**
 * @author cantalou
 * @date 2017-12-31 21:40
 */
public class DailyDetectValueModel extends BaseModel implements Parcelable {

    /**
     * 低
     */
    private String lowValue;

    /**
     * 高
     */
    private String highValue;

    /**
     * 高
     */
    private String pulseValue;

    private String sugarValue;

    private String typeCode;

    private String typeDetailCode;

    private String bmi;

    private String hightValue;

    private String weightValue;

    private String cholValue;

    private String hdlValue;

    private String ldlValue;

    private String trigValue;

    public String getLowValue() {
        return lowValue;
    }

    public void setLowValue(String lowValue) {
        this.lowValue = lowValue;
    }

    public String getHighValue() {
        return highValue;
    }

    public void setHighValue(String highValue) {
        this.highValue = highValue;
    }

    public String getPulseValue() {
        return pulseValue;
    }

    public void setPulseValue(String pulseValue) {
        this.pulseValue = pulseValue;
    }

    public String getSugarValue() {
        return sugarValue;
    }

    public void setSugarValue(String sugarValue) {
        this.sugarValue = sugarValue;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypeDetailCode() {
        return typeDetailCode;
    }

    public void setTypeDetailCode(String typeDetailCode) {
        this.typeDetailCode = typeDetailCode;
    }

    public String getBmi() {
        return bmi;
    }

    public void setBmi(String bmi) {
        this.bmi = bmi;
    }

    public String getHightValue() {
        return hightValue;
    }

    public void setHightValue(String hightValue) {
        this.hightValue = hightValue;
    }

    public String getWeightValue() {
        return weightValue;
    }

    public void setWeightValue(String weightValue) {
        this.weightValue = weightValue;
    }

    public String getCholValue() {
        return cholValue;
    }

    public void setCholValue(String cholValue) {
        this.cholValue = cholValue;
    }

    public String getHdlValue() {
        return hdlValue;
    }

    public void setHdlValue(String hdlValue) {
        this.hdlValue = hdlValue;
    }

    public String getLdlValue() {
        return ldlValue;
    }

    public void setLdlValue(String ldlValue) {
        this.ldlValue = ldlValue;
    }

    public String getTrigValue() {
        return trigValue;
    }

    public void setTrigValue(String trigValue) {
        this.trigValue = trigValue;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.lowValue);
        dest.writeString(this.highValue);
        dest.writeString(this.pulseValue);
        dest.writeString(this.sugarValue);
        dest.writeString(this.typeCode);
        dest.writeString(this.typeDetailCode);
        dest.writeString(this.bmi);
        dest.writeString(this.hightValue);
        dest.writeString(this.weightValue);
        dest.writeString(this.cholValue);
        dest.writeString(this.hdlValue);
        dest.writeString(this.ldlValue);
        dest.writeString(this.trigValue);
    }

    public DailyDetectValueModel() {
    }

    protected DailyDetectValueModel(Parcel in) {
        this.lowValue = in.readString();
        this.highValue = in.readString();
        this.pulseValue = in.readString();
        this.sugarValue = in.readString();
        this.typeCode = in.readString();
        this.typeDetailCode = in.readString();
        this.bmi = in.readString();
        this.hightValue = in.readString();
        this.weightValue = in.readString();
        this.cholValue = in.readString();
        this.hdlValue = in.readString();
        this.ldlValue = in.readString();
        this.trigValue = in.readString();
    }

    public static final Creator<DailyDetectValueModel> CREATOR = new Creator<DailyDetectValueModel>() {
        public DailyDetectValueModel createFromParcel(Parcel source) {
            return new DailyDetectValueModel(source);
        }

        public DailyDetectValueModel[] newArray(int size) {
            return new DailyDetectValueModel[size];
        }
    };
}
