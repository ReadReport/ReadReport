package com.wy.report.business.my.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-1-4 下午8:52
 * @description: ReadReport
 */
public class MessageListMode {


    @JSONField(name = "messages")
    private List<MessageItemMode.MessageMode> all;

    @JSONField(name = "count")
    private int count;

    @JSONField(name = "state")
    private int state;

    public List<MessageItemMode.MessageMode> getAll() {
        return all;
    }

    public void setAll(List<MessageItemMode.MessageMode> all) {
        this.all = all;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
