package com.wy.report.business.upload.model;

import com.wy.report.base.model.BaseModel;

import java.util.List;

/*
 *
 * @author cantalou
 * @date 2017-12-11 22:17
 */
public class HospitalProvinceModel extends BaseModel {

    private String province;

    private List<UnitModel> units;

    private boolean selected;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public List<UnitModel> getUnits() {
        return units;
    }

    public void setUnits(List<UnitModel> units) {
        this.units = units;
    }
}
