package com.wy.report.business.home.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.ArrayList;
import java.util.List;

/*
 *
 * @author cantalou
 * @date 2017-11-29 21:51
 */
public class HomeReportModel {

    @JSONField(name = "total_num")
    private long totalNumber;

    @JSONField(name = "report_info")
    private ArrayList<FeedModel> reportInfo = new ArrayList<>();

    public long getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(long totalNumber) {
        this.totalNumber = totalNumber;
    }

    public List<FeedModel> getReportInfo() {
        return reportInfo;
    }

    public void setReportInfo(ArrayList<FeedModel> reportInfo) {
        this.reportInfo = reportInfo;
    }
}
