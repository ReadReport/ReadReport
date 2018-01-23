package com.wy.report.business.upload.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.alibaba.fastjson.annotation.JSONField;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.wy.report.base.model.BaseModel;

/*
 *
 * @author cantalou
 * @date 2017-12-11 22:27
 */
public class UnitModel extends BaseModel implements MultiItemEntity, Parcelable {

    public static final int TYPE_TITLE = 0;

    public static final int TYPE_HOSPITAL = 1;
    public static final Creator<UnitModel> CREATOR = new Creator<UnitModel>() {
        @Override
        public UnitModel createFromParcel(Parcel source) {
            return new UnitModel(source);
        }

        @Override
        public UnitModel[] newArray(int size) {
            return new UnitModel[size];
        }
    };
    private String id;
    private String title;
    @JSONField(name = "ut_sheng_id")
    private String provinceId;
    private String province;
    private String city;
    private int type;
    private String szm;

    public UnitModel() {
    }

    protected UnitModel(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.provinceId = in.readString();
        this.province = in.readString();
        this.city = in.readString();
        this.type = in.readInt();
        this.szm = in.readString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int getItemType() {
        return type;
    }

    public String getSzm() {
        return szm;
    }

    public void setSzm(String szm) {
        this.szm = szm;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.provinceId);
        dest.writeString(this.province);
        dest.writeString(this.city);
        dest.writeInt(this.type);
        dest.writeString(this.szm);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UnitModel unitModel = (UnitModel) o;

        return id.equals(unitModel.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
