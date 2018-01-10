package com.wy.report.helper.dailydetect.impl;

import com.wy.report.helper.dailydetect.DailyDetectValueCreator;
import com.wy.report.widget.view.dailydetect.ValueType;
import com.wy.report.widget.view.dailydetect.ValueType.Builder;
import com.wy.report.widget.view.wheel.WheelViewItem;

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
    public List<ValueType> create() {

        List<ValueType> result = new ArrayList<>();

        List<WheelViewItem> values = new ArrayList<WheelViewItem>() {{
            add(new WheelViewItem("1","空腹"));
            add(new WheelViewItem("2","早餐后"));
            add(new WheelViewItem("3","午餐前"));
            add(new WheelViewItem("4","午餐后"));
            add(new WheelViewItem("5","晚餐前"));
            add(new WheelViewItem("6","晚餐后"));
            add(new WheelViewItem("7","睡前"));
            add(new WheelViewItem("8","夜间"));
            add(new WheelViewItem("0","随机"));
        }};
        ValueType time = new Builder().name("时间段")
                                      .unit("- -")
                                      .values(values)
                                      .create();
        result.add(time);

        ValueType number = new Builder().unit("血糖值")
                                        .start(0)
                                        .end(30)
                                        .fraction("0.0")
                                        .create();
        result.add(number);

        return result;
    }
}
