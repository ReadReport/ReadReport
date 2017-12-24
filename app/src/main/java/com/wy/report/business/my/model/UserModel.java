package com.wy.report.business.my.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.wy.report.base.model.BaseModel;



/**
 * Created by BHM on 17/12/23.
 */
public class UserModel extends BaseModel {

    @JSONField(name = "id")
    private String id;

    @JSONField(name = "name")
    private String name;

    @JSONField(name = "sex")
    private String sex;

    @JSONField(name = "s_img")
    private String headIcon;

    @JSONField(name = "mobile")
    private String mobile;

    @JSONField(name = "relationship")
    private String relationship;

    @JSONField(name = "birthday")
    private String birthday;

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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getHeadIcon() {
        return headIcon;
    }

    public void setHeadIcon(String headIcon) {
        this.headIcon = headIcon;
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", headIcon='" + headIcon + '\'' +
                ", mobile='" + mobile + '\'' +
                ", relationship='" + relationship + '\'' +
                ", birthday='" + birthday + '\'' +
                '}';
    }
}
