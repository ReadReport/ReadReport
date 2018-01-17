package com.wy.report.business.read.mode;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-1-17 下午4:25
 * @description: ReadReport
 */
public class DoctorMode {

    @JSONField(name = "id")
    private String id;
    @JSONField(name = "name")
    private String name;
    @JSONField(name = "gender")
    private String sex;
    @JSONField(name = "appellation")
    private String appellation;
    @JSONField(name = "h_img")
    private String headIcon;
    @JSONField(name = "good_at")
    private String goodAt;
    @JSONField(name = "summary")
    private String summary;
    @JSONField(name = "zx_num")
    private String    serverNum;


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

    public String getAppellation() {
        return appellation;
    }

    public void setAppellation(String appellation) {
        this.appellation = appellation;
    }

    public String getHeadIcon() {
        return headIcon;
    }

    public void setHeadIcon(String headIcon) {
        this.headIcon = headIcon;
    }

    public String getGoodAt() {
        return goodAt;
    }

    public void setGoodAt(String goodAt) {
        this.goodAt = goodAt;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getServerNum() {
        return serverNum;
    }

    public void setServerNum(String serverNum) {
        this.serverNum = serverNum;
    }
}
