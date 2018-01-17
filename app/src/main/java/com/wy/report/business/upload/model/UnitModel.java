package com.wy.report.business.upload.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.wy.report.base.model.BaseModel;

/*
 *
 * @author cantalou
 * @date 2017-12-11 22:27
 */
public class UnitModel extends BaseModel {

    private String id;

    private String title;

    @JSONField(name = "ut_sheng_id")
    private String provinceId;

    private String province;

    private String city;

    private String szm;

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

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSzm() {
        return szm;
    }

    public void setSzm(String szm) {
        this.szm = szm;
    }
}
