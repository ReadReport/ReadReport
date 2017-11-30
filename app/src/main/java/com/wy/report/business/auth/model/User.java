package com.wy.report.business.auth.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author cantalou
 * @date 2017年11月30日 14:36
 */
public class User {

    /**
     * 用户ID
     */
    private long id;

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
}
