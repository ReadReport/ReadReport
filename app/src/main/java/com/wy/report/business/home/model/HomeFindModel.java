package com.wy.report.business.home.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.wy.report.R;

import java.util.ArrayList;
import java.util.List;

/*
 *
 * @author cantalou
 * @date 2017-12-03 20:49
 */
public class HomeFindModel {

    private List<DailyDetectModel> dailyDetectModels;

    @JSONField(name = "test")
    private List<HealthTestModel> healthTestModels;

    @JSONField(name="knowledge")
    private List<HealthKnowledgeModel> healthKnowledgeModels;

    public List<DailyDetectModel> getDailyDetectModels() {
        if(dailyDetectModels == null){
            dailyDetectModels = new ArrayList<>();
            dailyDetectModels.add(new DailyDetectModel(1, "血压管理", R.drawable.btn_rcjc_bloodpressure));
            dailyDetectModels.add(new DailyDetectModel(2, "血糖管理", R.drawable.btn_rcjc_bloodsugar));
            dailyDetectModels.add(new DailyDetectModel(3, "体重管理", R.drawable.btn_rcjc_bodyweight));
            dailyDetectModels.add(new DailyDetectModel(4, "体脂管理", R.drawable.btn_rcjc_bodyfat));
            dailyDetectModels.add(new DailyDetectModel(5, "血脂管理", R.drawable.btn_rcjc_bloodpressure));
        }
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
