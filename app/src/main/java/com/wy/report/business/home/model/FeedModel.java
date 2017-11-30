package com.wy.report.business.home.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author cantlou
 * @date 2017年11月29日 18:28
 */
public class FeedModel {


    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 解读时间
     */
    @JSONField(name = "read_date")
    private long time;

    public FeedModel() {
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
