package com.wy.report.business.read.mode;

import android.databinding.ObservableField;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 17-12-12 下午9:48
 * @description: ReadReport
 */
public class ReportItemMode {
    @JSONField(name="upload_date")
    private ObservableField<String> time;
    @JSONField(name="name")
    private ObservableField<String> name;
    @JSONField(name="ti_hospital")
    private ObservableField<String> hospital;

    public ObservableField<String> getTime() {
        return time;
    }

    public void setTime(ObservableField<String> time) {
        this.time = time;
    }

    public ObservableField<String> getName() {
        return name;
    }

    public void setName(ObservableField<String> name) {
        this.name = name;
    }

    public ObservableField<String> getHospital() {
        return hospital;
    }

    public void setHospital(ObservableField<String> hospital) {
        this.hospital = hospital;
    }
}
