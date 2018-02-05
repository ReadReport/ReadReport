package com.wy.report.business.read.mode;

import com.alibaba.fastjson.annotation.JSONField;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.wy.report.util.TimeUtils;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-1-17 下午7:57
 * @description: ReadReport
 */
public class AskItemMode implements MultiItemEntity {

    public static final int DOCTOR_LAYOUT_TYPE = 0;
    public static final int USER_LAYOUT_TYPE   = 1;

    @JSONField(name = "id")
    private String id;

    @JSONField(name = "member_id")
    private String memberId;

    @JSONField(name = "doctor_id")
    private String doctorId;

    /**
     * 回复内容
     */
    @JSONField(name = "conversation")
    private String conversation;

    /**
     * 创建时间戳
     */
    @JSONField(name = "at")
    private long time;

    @JSONField(name = "mobile")
    private String mobile;

    @JSONField(name = "doctor_name")
    private String doctorName;

    @JSONField(name = "name")
    private String name;

    @JSONField(name = "doctor_img")
    private String doctorImage;
    @JSONField(name = "user_img")
    private String userImage;


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

    public String getConversation() {
        return conversation;
    }

    public void setConversation(String conversation) {
        this.conversation = conversation;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDoctorImage() {
        return doctorImage;
    }

    public void setDoctorImage(String doctorImage) {
        this.doctorImage = doctorImage;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getShowTime() {
        return TimeUtils.millis2String(time);
    }

    @Override
    public int getItemType() {
        if (isDoctor()) {
            return DOCTOR_LAYOUT_TYPE;
        }
        return USER_LAYOUT_TYPE;
    }
}
