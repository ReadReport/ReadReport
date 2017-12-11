package com.wy.report.business.upload.model;

import com.wy.report.base.model.BaseModel;

import java.util.List;

/*
 *
 * @author cantalou
 * @date 2017-12-11 22:17
 */
public class NotChainUnitModel extends BaseModel {

    private String name;

    private List<UnitModel> unitList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UnitModel> getUnitList() {
        return unitList;
    }

    public void setUnitList(List<UnitModel> unitList) {
        this.unitList = unitList;
    }
}
