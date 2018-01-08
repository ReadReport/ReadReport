package com.wy.report.business.dailydetect.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.alibaba.fastjson.annotation.JSONField;
import com.wy.report.base.model.BaseModel;

/*
 * @author cantalou
 * @date 2017-12-31 21:40
 */
public class DailyDetectDataModel extends BaseModel implements Parcelable {

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
    /**
     * 项目id
     */
    private String id;
    /**
     * 类别id
     */
    @JSONField(name = "catalog_id")
    private String catalogId;
    /**
     * 项目名称
     */
    private String name;
    /**
     * 选项类型
     */
    private String optionType;
    /**
     * "偏高|正常",  选项
     */
    private String options;
    /**
     * "NSE是监测小细胞肺癌的首选标志物。。。",：偏高或不正常结论
     */
    @JSONField(name = "high_conclusion")
    private String highConclusion;
    /**
     * 偏低结论
     */
    @JSONField(name = "low_conclusion")
    private String lowConclusion;
    /**
     * 异常建议（为空时取类别异常建议）
     */
    private String suggest;

    public DailyDetectDataModel() {
    }

    protected DailyDetectDataModel(Parcel in) {
        this.id = in.readString();
        this.catalogId = in.readString();
        this.name = in.readString();
        this.optionType = in.readString();
        this.options = in.readString();
        this.highConclusion = in.readString();
        this.lowConclusion = in.readString();
        this.suggest = in.readString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOptionType() {
        return optionType;
    }

    public void setOptionType(String optionType) {
        this.optionType = optionType;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getHighConclusion() {
        return highConclusion;
    }

    public void setHighConclusion(String highConclusion) {
        this.highConclusion = highConclusion;
    }

    public String getLowConclusion() {
        return lowConclusion;
    }

    public void setLowConclusion(String lowConclusion) {
        this.lowConclusion = lowConclusion;
    }

    public String getSuggest() {
        return suggest;
    }

    public void setSuggest(String suggest) {
        this.suggest = suggest;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.catalogId);
        dest.writeString(this.name);
        dest.writeString(this.optionType);
        dest.writeString(this.options);
        dest.writeString(this.highConclusion);
        dest.writeString(this.lowConclusion);
        dest.writeString(this.suggest);
    }
}
