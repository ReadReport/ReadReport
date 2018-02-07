package com.wy.report.business.home.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-2-7 上午11:37
 * @description: ReadReport
 */
public class AppUpdateMode {

    @JSONField(name = "android_version")
    private int versionCode;

    @JSONField(name = "introduce_info")
    private String updateInfo;

    @JSONField(name = "download_url")
    private String apkDownloadUrl;

    @JSONField(name = "force_update_status")
    private boolean forceUpload;


    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getUpdateInfo() {
        return updateInfo;
    }

    public void setUpdateInfo(String updateInfo) {
        this.updateInfo = updateInfo;
    }

    public String getApkDownloadUrl() {
        return apkDownloadUrl;
    }

    public void setApkDownloadUrl(String apkDownloadUrl) {
        this.apkDownloadUrl = apkDownloadUrl;
    }

    public boolean isForceUpload() {
        return forceUpload;
    }

    public void setForceUpload(boolean forceUpload) {
        this.forceUpload = forceUpload;
    }
}
