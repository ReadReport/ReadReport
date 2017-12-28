package com.wy.report.business.home.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.wy.report.base.model.BaseModel;

import java.util.List;

/*
 *
 * @author cantalou
 * @date 2017-12-28 21:44
 */
public class HomeFindHealthyKnowledgeModel extends BaseModel {


    @JSONField(name = "data")
    private List<HealthKnowledgeModel> healthKnowledgeModels;

    private String more;

    public List<HealthKnowledgeModel> getHealthKnowledgeModels() {
        return healthKnowledgeModels;
    }

    public void setHealthKnowledgeModels(List<HealthKnowledgeModel> healthKnowledgeModels) {
        this.healthKnowledgeModels = healthKnowledgeModels;
    }

    public String getMore() {
        return more;
    }

    public void setMore(String more) {
        this.more = more;
    }
}
