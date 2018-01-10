package com.wy.report.business.home.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.wy.report.base.model.BaseModel;

/*
 * 日常检测
 * @author cantalou
 * @date 2017-12-03 16:04
 */
public class DailyDetectTypeModel extends BaseModel implements Parcelable{


    /**
     * 血压
     */
    public static final int DETECT_TYPE_BLOOD_PRESSURE = 1;

    /**
     * 血糖
     */
    public static final int DETECT_TYPE_BLOOD_SUGAR = 2;

    /**
     * BMI
     */
    public static final int DETECT_TYPE_BMI = 3;

    /**
     * 运动
     */
    public static final int DETECT_TYPE_SPORT = 4;

    /**
     * 睡眠
     */
    public static final int DETECT_TYPE_SLEEP = 5;

    /**
     * 体脂
     */
    public static final int DETECT_TYPE_BODY_FAT = 6;

    /**
     * 血脂
     */
    public static final int DETECT_TYPE_BLOOD_FAT = 7;

    /**
     * 血尿酸
     */
    public static final int DETECT_TYPE_BUA = 8;

    /**
     * 血氧
     */
    public static final int DETECT_TYPE_BLOOD_OXY = 9;

    private int id;

    private String title;

    private int iconID;

    public DailyDetectTypeModel(int id, String title, int iconID) {
        this.id = id;
        this.title = title;
        this.iconID = iconID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIconID() {
        return iconID;
    }

    public void setIconID(int iconID) {
        this.iconID = iconID;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeInt(this.iconID);
    }

    protected DailyDetectTypeModel(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.iconID = in.readInt();
    }

    public static final Creator<DailyDetectTypeModel> CREATOR = new Creator<DailyDetectTypeModel>() {
        public DailyDetectTypeModel createFromParcel(Parcel source) {
            return new DailyDetectTypeModel(source);
        }

        public DailyDetectTypeModel[] newArray(int size) {
            return new DailyDetectTypeModel[size];
        }
    };
}
