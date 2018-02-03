package com.wy.report.business.auth.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.alibaba.fastjson.annotation.JSONField;
import com.wy.report.base.model.BaseModel;

/**
 * @author cantalou
 * @date 2017年11月30日 14:36
 */
public class User extends BaseModel implements Parcelable {

    public static final int GENDER_MALE = 1;

    public static final int GENDER_FEMALE = 2;

    /**
     * 用户ID
     */
    private String id;

    /**
     * 用户昵称
     */
    private String name;

    /**
     * 用户性别( 1男 2女 其他：未知)
     */
    private int sex;

    /**
     * 用户头像
     */
    @JSONField(name = "s_img")
    private String head;

    /**
     * 用户手机号
     */
    private String mobile;

    /**
     * 成员关系
     */
    private String relationship;

    /**
     * 用户生日的时间戳
     */
    private long birthday;

    public User() {
    }

    protected User(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.sex = in.readInt();
        this.head = in.readString();
        this.mobile = in.readString();
        this.relationship = in.readString();
        this.birthday = in.readLong();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
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

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeInt(this.sex);
        dest.writeString(this.head);
        dest.writeString(this.mobile);
        dest.writeString(this.relationship);
        dest.writeLong(this.birthday);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id.equals(user.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
