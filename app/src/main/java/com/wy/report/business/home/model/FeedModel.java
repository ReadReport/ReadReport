package com.wy.report.business.home.model;

/**
 * @author cantlou
 * @date 2017年11月29日 18:28
 */
public class FeedModel{

    /**
     * 动态id
     */
    private long id;

    /**
     * 手机号码
     */
    private String phoneNum;

    /**
     * 解读时间
     */
    private long time;

    public FeedModel() {
    }

    public FeedModel(long id, String phoneNum, long time) {
        this.id = id;
        this.phoneNum = phoneNum;
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
