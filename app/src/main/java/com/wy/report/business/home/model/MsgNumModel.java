package com.wy.report.business.home.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-2-5 下午4:17
 * @description: ReadReport
 */
public class MsgNumModel {
    @JSONField(name = "state")
    private int state;

    @JSONField(name = "num")
    private int num;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
