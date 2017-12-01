package com.wy.report.business.family.model;

import android.os.Parcel;

import com.alibaba.fastjson.annotation.JSONField;
import com.wy.report.business.auth.model.User;

/**
 * @author cantalou
 * @date 2017年12月01日 14:20
 */
public class FamilyMemberModel extends User {

    /**
     * 证件号码（身份证等等）
     */
    @JSONField(name = "id_card")
    private String idCard;

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
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.idCard);
    }

    public FamilyMemberModel() {
    }

    protected FamilyMemberModel(Parcel in) {
        super(in);
        this.idCard = in.readString();
    }

    public static final Creator<FamilyMemberModel> CREATOR = new Creator<FamilyMemberModel>() {
        @Override
        public FamilyMemberModel createFromParcel(Parcel source) {
            return new FamilyMemberModel(source);
        }

        @Override
        public FamilyMemberModel[] newArray(int size) {
            return new FamilyMemberModel[size];
        }
    };
}
