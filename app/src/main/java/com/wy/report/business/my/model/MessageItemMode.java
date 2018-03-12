package com.wy.report.business.my.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 17-12-29 下午7:46
 * @description: ReadReport
 */
public class MessageItemMode {

    @JSONField(name = "message")
    private MessageMode message;

    public MessageMode getMessage() {
        return message;
    }

    public void setMessage(MessageMode message) {
        this.message = message;
    }

    public static class MessageMode {
        @JSONField(name = "id")
        private String id;

        @JSONField(name = "member_id")
        private String memberId;

        @JSONField(name = "message_title")
        private String title;

        @JSONField(name = "message")
        private String message;

        @JSONField(name = "create_date")
        private String createDate;

        @JSONField(name = "update_date")
        private String updateDate;

        /**
         * 发布状态（0未发布 1已发布【这里只显示已发布的消息】
         */
        @JSONField(name = "publish_status")
        private String status;

        /**
         * 发布状态（0未读 1已读
         */
        @JSONField(name = "view_status")
        private int viewStatus;

        private boolean viewed;


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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public boolean isViewed() {
            return viewStatus == 1;
        }

        public void setViewed(boolean viewed) {
            viewStatus = viewed ? 1 : 0;
        }

    }
}
