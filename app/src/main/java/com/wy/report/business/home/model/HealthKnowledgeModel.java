package com.wy.report.business.home.model;

import com.alibaba.fastjson.annotation.JSONField;

/*
 * 健康知识
 * @author cantalou
 * @date 2017-12-03 16:08
 */
public class HealthKnowledgeModel {

    private String url;

    private String title;

    @JSONField(name = "img_m")
    private String image;

    @JSONField(name = "intro")
    private String desc;

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
