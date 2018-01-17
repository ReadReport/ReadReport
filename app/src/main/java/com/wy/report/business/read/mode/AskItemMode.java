package com.wy.report.business.read.mode;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-1-17 下午7:57
 * @description: ReadReport
 */
public class AskItemMode {

    @JSONField(name = "id")
    private String id;

    @JSONField(name = "member_id")
    private String memberId;

    @JSONField(name = "doctor_id")
    private String doctorId;

    @JSONField(name = "rep_id")
    private String repId;

    /**
     * 回复内容
     */
    @JSONField(name = "conversation")
    private String conversation;

    /**
     * 读状态 0表示未阅读，1表示已阅读
     */
    @JSONField(name = "if_readed")
    private int ifReaded;

    /**
     * 创建时间戳
     */
    @JSONField(name = "at")
    private long time;
}
