package com.wy.report.business.dailydetect.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.wy.report.base.model.BaseModel;

import java.util.List;

/*
 *
 * @author cantalou
 * @date 2018-01-13 21:30
 */
public class RecordResultModel extends BaseModel {

    @JSONField(name = "records")
    private List<DailyDetectDataModel> data;

    @JSONField(name = "lind_detail")
    private String linkDetail;

    public List<DailyDetectDataModel> getData() {
        return data;
    }

    public void setData(List<DailyDetectDataModel> data) {
        this.data = data;
    }

    public String getLinkDetail() {
        return linkDetail;
    }

    public void setLinkDetail(String linkDetail) {
        this.linkDetail = linkDetail;
    }
}
