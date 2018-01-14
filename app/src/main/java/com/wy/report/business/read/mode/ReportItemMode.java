package com.wy.report.business.read.mode;

import com.alibaba.fastjson.annotation.JSONField;
import com.wy.report.util.TimeUtils;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 17-12-12 下午9:48
 * @description: ReadReport
 */
public class ReportItemMode {

    /**
     * 报告解读状态 0:未解读1:解读中2:已解读
     */
    public static final int READ_STATE_UNSUBMIT = 0;
    public static final int READ_STATE_UNREAD = 1;
    public static final int READ_STATE_UNGTE = 2;
    public static final int READ_STATE_GETFAILED = 3;
    public static final int READ_STATE_READED = 4;

    /**
     * 报告id
     */
    @JSONField(name = "id")
    private String id;
    /**
     * 用户id
     */
    @JSONField(name = "member_id")
    private String memberId;
    /**
     * 当前会员归属哪个用户 辅助查询
     */
    @JSONField(name = "parent_id")
    private String parentId;
    /**
     * 上传报告的时间戳
     */
    @JSONField(name = "upload_date")
    private long uploadTime;
    /**
     * 报告解读状态
     */
    @JSONField(name = "report_status")
    private int parseState;
    /**
     * 关系
     */
    @JSONField(name = "relationship")
    private String relationship;
//    /**
//     * 是否上传成功
//     */
//    @JSONField(name = "up_is_true")
//    private int upIsTrue;
//    /**
//     * 医生是否已经回复 1已答复
//     */
//    @JSONField(name = "is_has_rep")
//    private int hasReplay;

    /**
     * 用户姓名
     */
    @JSONField(name = "name")
    private String name;
    /**
     * 医院
     */
    @JSONField(name = "tj_hospital")
    private String hospital;
    /**
     * 做体检的时间
     */
    @JSONField(name = "tj_date")
    private long checkTime;

    /**
     * 体检分数
     */
    @JSONField(name = "score")
    private String score;

    /**
     * 未读消息数
     */
    @JSONField(name = "unreaded_msg_nums")
    private int unReadMsgNum;

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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getUploadTime() {
        return TimeUtils.millis2String(uploadTime).substring(0, 10);
    }

    public void setUploadTime(long uploadTime) {
        this.uploadTime = uploadTime;
    }

    public int getParseState() {
        return parseState;
    }

    public void setParseState(int parseState) {
        this.parseState = parseState;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

//    public boolean getUpIsTrue() {
//        return upIsTrue==1;
//    }
//
//    public void setUpIsTrue(int upIsTrue) {
//        this.upIsTrue = upIsTrue;
//    }
//
//    public boolean getHasReplay() {
//        return hasReplay==1;
//    }
//
//    public void setHasReplay(int hasReplay) {
//        this.hasReplay = hasReplay;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getCheckTime() {
        return TimeUtils.millis2String(checkTime).substring(0, 10);
    }

    public void setCheckTime(long checkTime) {
        this.checkTime = checkTime;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public int getUnReadMsgNum() {
        return unReadMsgNum;
    }

    public void setUnReadMsgNum(int unReadMsgNum) {
        this.unReadMsgNum = unReadMsgNum;
    }
}
