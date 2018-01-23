package com.wy.report.business.read.mode;

import com.alibaba.fastjson.annotation.JSONField;
import com.wy.report.util.TimeUtils;

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


    public boolean isDoctor() {
        if (memberId == null) {
            return true;
        }
        return memberId.equals("0");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getRepId() {
        return repId;
    }

    public void setRepId(String repId) {
        this.repId = repId;
    }

    public String getConversation() {
        return conversation;
    }

    public void setConversation(String conversation) {
        this.conversation = conversation;
    }

    public int getIfReaded() {
        return ifReaded;
    }

    public void setIfReaded(int ifReaded) {
        this.ifReaded = ifReaded;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getShowTime() {
        return TimeUtils.millis2String(time);
    }
}
