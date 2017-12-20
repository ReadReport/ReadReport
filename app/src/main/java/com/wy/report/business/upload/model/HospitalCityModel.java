package com.wy.report.business.upload.model;

import com.wy.report.base.model.BaseModel;

import java.util.List;

/**
 * @author cantalou
 * @date 2017年12月20日 12:54
 */
public class HospitalCityModel extends BaseModel {

    private String name;

    private List<UnitModel> units;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UnitModel> getUnits() {
        return units;
    }

    public void setUnits(List<UnitModel> units) {
        this.units = units;
    }
}
