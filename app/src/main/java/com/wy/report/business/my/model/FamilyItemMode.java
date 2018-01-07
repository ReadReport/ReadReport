package com.wy.report.business.my.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 17-12-29 下午7:46
 * @description: ReadReport
 */
public class FamilyItemMode implements Parcelable {

    @JSONField(name = "id")
    private String id;

    @JSONField(name = "name")
    private String name;

    @JSONField(name = "relationship")
    private String relationship;

    @JSONField(name = "birthday")
    private String birthday;

    @JSONField(name = "sex")
    private String sex;

    @JSONField(name = "mobile")
    private String mobile;

    @JSONField(name = "id_card")
    private String idCard;


    protected FamilyItemMode(Parcel in) {
        id = in.readString();
        name = in.readString();
        relationship = in.readString();
        birthday = in.readString();
        sex = in.readString();
        mobile = in.readString();
        idCard = in.readString();
    }


    public FamilyItemMode()
    {

    }

    public static final Creator<FamilyItemMode> CREATOR = new Creator<FamilyItemMode>() {
        @Override
        public FamilyItemMode createFromParcel(Parcel in) {
            return new FamilyItemMode(in);
        }

        @Override
        public FamilyItemMode[] newArray(int size) {
            return new FamilyItemMode[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(relationship);
        parcel.writeString(birthday);
        parcel.writeString(sex);
        parcel.writeString(mobile);
        parcel.writeString(idCard);
    }
}
