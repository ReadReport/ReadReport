package com.wy.report.business.read.mode;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 17-12-5 下午8:24
 * @description: ReadReport
 */
public class ReportListMode {

    private String name;
    private String uploadTime;
    private String hosptal;
    private String state;
    private String icon;




    public static class ReportItem
    {
        @JSONField(name="name")
        private String name;

        @JSONField(name="upload_date")
        private String uploadTime;

        @JSONField(name="ti_hospital")
        private String hosptal;

        private String state;

        private String icon;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUploadTime() {
            return uploadTime;
        }

        public void setUploadTime(String uploadTime) {
            this.uploadTime = uploadTime;
        }

        public String getHosptal() {
            return hosptal;
        }

        public void setHosptal(String hosptal) {
            this.hosptal = hosptal;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }
}
