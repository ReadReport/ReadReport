package com.wy.report.business.read.mode;


import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 17-12-12 下午9:48
 * @description: ReadReport
 */
public class ReportListMode {

    @JSONField(name = "rep_lists")
    private List<ReportItemMode> data;

    @JSONField(name = "count")
    private int count;

    public List<ReportItemMode> getData() {
        return data;
    }

    public void setData(List<ReportItemMode> data) {
        this.data = data;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
