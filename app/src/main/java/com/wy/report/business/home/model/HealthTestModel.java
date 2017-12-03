package com.wy.report.business.home.model;

import com.wy.report.base.model.BaseModel;

/*
 *
 * @author cantalou
 * @date 2017-12-03 16:05
 */
public class HealthTestModel extends BaseModel {

    private int id;

    private String title;

    private String image;

    private int testedNum;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getTestedNum() {
        return testedNum;
    }

    public void setTestedNum(int testedNum) {
        this.testedNum = testedNum;
    }
}
