package com.wy.report.business.home.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.wy.report.base.model.BaseModel;

import java.util.List;

/*
 *
 * @author cantalou
 * @date 2017-12-28 21:27
 */
public class HomeFindHealthyTestModel extends BaseModel {

    @JSONField(name = "data")
    private List<HealthTestModel> healthTestModels;

    private String more;

    public List<HealthTestModel> getHealthTestModels() {
        return healthTestModels;
    }

    public void setHealthTestModels(List<HealthTestModel> healthTestModels) {
        this.healthTestModels = healthTestModels;
    }

    public String getMore() {
        return more;
    }

    public void setMore(String more) {
        this.more = more;
    }
}
