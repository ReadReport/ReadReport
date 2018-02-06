package com.wy.report.business.my.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-2-5 下午5:35
 * @description: ReadReport
 */
public class EditHeadMode {

    @JSONField(name = "status")
    private int    status;
    @JSONField(name = "photo")
    private String photo;
    @JSONField(name = "msg")
    private String msg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
