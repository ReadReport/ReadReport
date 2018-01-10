package com.wy.report.business.my.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 17-12-29 下午7:46
 * @description: ReadReport
 */
public class VerifyPhoneNumMode {

    @JSONField(name = "mid")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
