package com.wy.report.business.my.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-1-4 下午8:52
 * @description: ReadReport
 */
public class MessageListMode {

    @JSONField(name = "viewed_message")
    private List<MessageItemMode> viewedMessage;

    @JSONField(name = "noview_message")
    private List<MessageItemMode> noViewMessage;

    private List<MessageItemMode> all;


    public List<MessageItemMode> getViewedMessage() {
        return viewedMessage;
    }

    public void setViewedMessage(List<MessageItemMode> viewedMessage) {
        this.viewedMessage = viewedMessage;
    }

    public List<MessageItemMode> getNoViewMessage() {
        return noViewMessage;
    }

    public void setNoViewMessage(List<MessageItemMode> noViewMessage) {
        this.noViewMessage = noViewMessage;
    }

    public List<MessageItemMode> getAll() {
        all = new ArrayList<>();
        all.addAll(noViewMessage);
        all.addAll(viewedMessage);
        return all;
    }

    public void setAll(List<MessageItemMode> all) {
        this.all = all;
    }
}
