package com.wy.report.business.home.model;

import java.util.List;

/*
 *
 * @author cantalou
 * @date 2017-12-03 20:49
 */
public class HomeFindModel {

    private List<DailyDetectModel> dailyDetectModels;

    private List<HealthTestModel> healthTestModels;

    private List<HealthKnowledgeModel> healthKnowledgeModels;

    public List<DailyDetectModel> getDailyDetectModels() {
        return dailyDetectModels;
    }

    public void setDailyDetectModels(List<DailyDetectModel> dailyDetectModels) {
        this.dailyDetectModels = dailyDetectModels;
    }

    public List<HealthTestModel> getHealthTestModels() {
        return healthTestModels;
    }

    public void setHealthTestModels(List<HealthTestModel> healthTestModels) {
        this.healthTestModels = healthTestModels;
    }

    public List<HealthKnowledgeModel> getHealthKnowledgeModels() {
        return healthKnowledgeModels;
    }

    public void setHealthKnowledgeModels(List<HealthKnowledgeModel> healthKnowledgeModels) {
        this.healthKnowledgeModels = healthKnowledgeModels;
    }
}
