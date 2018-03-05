package com.wy.report.business.read.mode;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-1-20 下午3:40
 * @description: ReadReport
 */
public class ReportDetailMode {

    /**
     * 报告信息
     */
    @JSONField(name = "rep_info")
    private ReportInfo       reportInfo;
    /**
     * 用户信息
     */
    @JSONField(name = "rep_userinfo")
    private UserInfo         userInfo;
    /**
     * 图片信息
     */
    @JSONField(name = "rep_Imgs")
    private List<ImgInfo>    imgs;
    /**
     * 身体机能
     */
    @JSONField(name = "rep_system")
    private List<BodySystem> mBodySystems;

    public ReportInfo getReportInfo() {
        return reportInfo;
    }

    public void setReportInfo(ReportInfo reportInfo) {
        this.reportInfo = reportInfo;
    }

    public ReportDetailMode.UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(ReportDetailMode.UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public List<ImgInfo> getImgs() {
        return imgs;
    }

    public void setImgs(List<ImgInfo> imgs) {
        this.imgs = imgs;
    }

    public List<BodySystem> getBodySystems() {
        return mBodySystems;
    }

    public void setBodySystems(List<BodySystem> bodySystems) {
        mBodySystems = bodySystems;
    }

    /**
     * 用户信息
     */
    public class UserInfo {
        @JSONField(name = "name")
        private String name;
        @JSONField(name = "sex")
        private String sex;
        @JSONField(name = "tj_hospital")
        private String hospital;
        @JSONField(name = "bg_type")
        private String type;
        @JSONField(name = "tj_date")
        private long   date;
        @JSONField(name = "age")
        private int    age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getHospital() {
            return hospital;
        }

        public void setHospital(String hospital) {
            this.hospital = hospital;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public long getDate() {
            return date;
        }

        public void setDate(long date) {
            this.date = date;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }


    /**
     * 报告信息
     */
    public class ReportInfo {
        @JSONField(name = "id")
        private String id;
        @JSONField(name = "read_date")
        private long   readDate;
        @JSONField(name = "jl")
        private String jl;
        @JSONField(name = "doctor_id")
        private String doctorId;
        @JSONField(name = "doctor_name")
        private String doctorName;
        @JSONField(name = "upload_date")
        private long   uploadDate;
        @JSONField(name = "remark")
        private String remark;
        @JSONField(name = "remind_time")
        private long   remindTime;
        @JSONField(name = "rep_total_score")
        private int    score;
        @JSONField(name = "report_status")
        private int    reportStatus;

        @JSONField(name = "rep_consult")
        private DoctorSuggestion doctorSuggestion;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public long getReadDate() {
            return readDate;
        }

        public void setReadDate(long readDate) {
            this.readDate = readDate;
        }

        public String getJl() {
            return jl;
        }

        public void setJl(String jl) {
            this.jl = jl;
        }

        public String getDoctorId() {
            return doctorId;
        }

        public void setDoctorId(String doctorId) {
            this.doctorId = doctorId;
        }

        public String getDoctorName() {
            return doctorName;
        }

        public void setDoctorName(String doctorName) {
            this.doctorName = doctorName;
        }

        public long getUploadDate() {
            return uploadDate;
        }

        public void setUploadDate(long uploadDate) {
            this.uploadDate = uploadDate;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public long getRemindTime() {
            return remindTime;
        }

        public void setRemindTime(long remindTime) {
            this.remindTime = remindTime;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public int getReportStatus() {
            return reportStatus;
        }

        public void setReportStatus(int reportStatus) {
            this.reportStatus = reportStatus;
        }

        public DoctorSuggestion getDoctorSuggestion() {
            return doctorSuggestion;
        }

        public void setDoctorSuggestion(DoctorSuggestion doctorSuggestion) {
            this.doctorSuggestion = doctorSuggestion;
        }

        /**
         * 医生建议
         */
        public class DoctorSuggestion {
            @JSONField(name = "sportjy")
            private String sportSuggestion;
            @JSONField(name = "foodjy")
            private String foodSuggestion;
            @JSONField(name = "itemjy")
            private String itemSuggestion;
            @JSONField(name = "illzb")
            private String illQuota;
            @JSONField(name = "normalzb")
            private String normalQuota;


            public String getSportSuggestion() {
                return sportSuggestion;
            }

            public void setSportSuggestion(String sportSuggestion) {
                this.sportSuggestion = sportSuggestion;
            }

            public String getFoodSuggestion() {
                return foodSuggestion;
            }

            public void setFoodSuggestion(String foodSuggestion) {
                this.foodSuggestion = foodSuggestion;
            }

            public String getItemSuggestion() {
                return itemSuggestion;
            }

            public void setItemSuggestion(String itemSuggestion) {
                this.itemSuggestion = itemSuggestion;
            }

            public String getIllQuota() {
                return illQuota;
            }

            public void setIllQuota(String illQuota) {
                this.illQuota = illQuota;
            }

            public String getNormalQuota() {
                return normalQuota;
            }

            public void setNormalQuota(String normalQuota) {
                this.normalQuota = normalQuota;
            }
        }
    }


    /**
     * 体检报告图片
     */
    public class ImgInfo {
        @JSONField(name = "imgurl")
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    /**
     * 身体机能项目
     */
    public static class BodySystem {

        public final static int BODY_1 = 1;
        public final static int BODY_2 = 2;
        public final static int BODY_3 = 3;
        public final static int BODY_4 = 4;
        public final static int BODY_5 = 5;
        public final static int BODY_6 = 6;
        public final static int BODY_7 = 7;
        public final static int BODY_8 = 8;

        @JSONField(name = "id")
        private String        id;
        @JSONField(name = "esys_id")
        private int           systemId;
        @JSONField(name = "system_name")
        private String        systemName;
        @JSONField(name = "ill_items")
        private List<IllItem> mIllItems;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getSystemId() {
            return systemId;
        }

        public void setSystemId(int systemId) {
            this.systemId = systemId;
        }

        public String getSystemName() {
            return systemName;
        }

        public void setSystemName(String systemName) {
            this.systemName = systemName;
        }

        public List<IllItem> getIllItems() {
            return mIllItems;
        }

        public void setIllItems(List<IllItem> illItems) {
            mIllItems = illItems;
        }

        /**
         * 异常项目
         */
        public static class IllItem {
            @JSONField(name = "esys_id")
            private String systemId;
            @JSONField(name = "item_name")
            private String itemName;
            @JSONField(name = "res_zb")
            private String resZb;
            @JSONField(name = "res_detail")
            private String detail;
            @JSONField(name = "reference_range")
            private String referenceRange;

            public IllItem() {

            }


            public String getSystemId() {
                return systemId;
            }

            public void setSystemId(String systemId) {
                this.systemId = systemId;
            }

            public String getItemName() {
                return itemName;
            }

            public void setItemName(String itemName) {
                this.itemName = itemName;
            }

            public String getResZb() {
                return resZb;
            }

            public void setResZb(String resZb) {
                this.resZb = resZb;
            }

            public String getDetail() {
                return detail;
            }

            public void setDetail(String detail) {
                this.detail = detail;
            }

            public String getReferenceRange() {
                return referenceRange;
            }

            public void setReferenceRange(String referenceRange) {
                this.referenceRange = referenceRange;
            }
        }

    }

}


