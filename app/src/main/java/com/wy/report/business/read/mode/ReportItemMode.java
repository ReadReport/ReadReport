package com.wy.report.business.read.mode;

import android.databinding.ObservableField;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 17-12-12 下午9:48
 * @description: ReadReport
 */
public class ReportItemMode {
    private ObservableField<String> time;
    private ObservableField<String> name;
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
