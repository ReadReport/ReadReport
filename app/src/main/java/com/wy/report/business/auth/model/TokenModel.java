package com.wy.report.business.auth.model;

/**
 * @author cantalou
 * @date 2017年11月23日 15:12
 */
public class TokenModel {

    private String token = "";

    private long timestamp;

    /**
     * 获取Token时的系统启动时间
     */
    private long systemClock;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getSystemClock() {
        return systemClock;
    }

    public void setSystemClock(long systemClock) {
        this.systemClock = systemClock;
    }
}
