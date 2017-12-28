package com.wy.report.business.home.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.wy.report.base.model.BaseModel;

/*
 *
 * @author cantalou
 * @date 2017-12-03 16:05
 */
public class HealthTestModel extends BaseModel {

    private String id;

    @JSONField(name="theme")
    private String title;

    @JSONField(name="l_img")
    private String image;

    @JSONField(name="views")
    private String testedNum;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getTestedNum() {
        return testedNum;
    }

    public void setTestedNum(String testedNum) {
        this.testedNum = testedNum;
    }
}
