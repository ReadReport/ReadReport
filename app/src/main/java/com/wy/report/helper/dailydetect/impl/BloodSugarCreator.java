package com.wy.report.helper.dailydetect.impl;

import com.wy.report.helper.dailydetect.DailyDetectValueCreator;
import com.wy.report.widget.view.dailydetect.DailyDetectValueType;
import com.wy.report.widget.view.dailydetect.DailyDetectValueType.Builder;

import java.util.ArrayList;
import java.util.List;

/**
 * 时间段：空腹、早餐后、午餐前、午餐后、晚餐前、晚餐后、睡前、夜间
 * 血糖值：0~29
 * 小数值：0~9
 *
 * @author cantalou
 * @date 2018年01月02日 13:35
 */
public class BloodSugarCreator implements DailyDetectValueCreator {

    @Override
    public List<DailyDetectValueType> create() {

        List<DailyDetectValueType> result = new ArrayList<>();

        List<String> values = new ArrayList<String>() {{
            add("空腹");
            add("早餐后");
            add("午餐前");
            add("午餐后");
            add("晚餐前");
            add("晚餐后");
            add("睡前");
            add("夜间");
        }};
        DailyDetectValueType time = new Builder().name("时间段")
                                                 .unit("- -")
                                                 .values(values)
                                                 .create();
        result.add(time);

        DailyDetectValueType number = new Builder().unit("血糖值")
                                                   .start(0)
                                                   .end(30)
                                                   .fraction("0.0")
                                                   .create();
        result.add(number);

        return result;
    }
}
