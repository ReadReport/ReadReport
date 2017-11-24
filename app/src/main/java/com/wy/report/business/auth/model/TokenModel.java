package com.wy.report.business.auth.model;

/**
 * @author cantalou
 * @date 2017年11月23日 15:12
 */
public class TokenModel {

    private String token = "";

    private long timestamp;

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
}
