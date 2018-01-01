package com.wy.report.helper.dailydetect.impl;

import com.wy.report.helper.dailydetect.DailyDetectValueCreator;
import com.wy.report.widget.view.dailydetect.DailyDetectValueType;

import java.util.ArrayList;
import java.util.List;

/*
 *
 * @author cantalou
 * @date 2018-01-01 22:26
 */
public class BloodPressureCreator implements DailyDetectValueCreator {

    @Override
    public List<DailyDetectValueType> create() {
        List<DailyDetectValueType> result = new ArrayList<>();
        result.add(new DailyDetectValueType("高压", "mmhg", 5, 30, 240, 1));
        result.add(new DailyDetectValueType("低压", "mmhg", 5, 30, 240, 1));
        result.add(new DailyDetectValueType("心率", "bmp", 5, 30, 220, 1));
        return result;
    }

}
