package com.wy.report.business.home.model;

import com.wy.report.base.model.BaseModel;

/*
 * 日常检测
 * @author cantalou
 * @date 2017-12-03 16:04
 */
public class DailyDetectModel extends BaseModel {

    private int id;

    private String title;

    private int iconID;

    public DailyDetectModel(int id, String title, int iconID) {
        this.id = id;
        this.title = title;
        this.iconID = iconID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIconID() {
        return iconID;
    }

    public void setIconID(int iconID) {
        this.iconID = iconID;
    }
}
