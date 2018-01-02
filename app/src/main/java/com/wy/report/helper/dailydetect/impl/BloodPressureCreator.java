package com.wy.report.helper.dailydetect.impl;

import com.wy.report.helper.dailydetect.DailyDetectValueCreator;
import com.wy.report.widget.view.dailydetect.DailyDetectValueType;
import com.wy.report.widget.view.dailydetect.DailyDetectValueType.Builder;

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

        DailyDetectValueType high = new Builder().name("高压")
                                                 .unit("mmhg")
                                                 .start(30)
                                                 .end(240)
                                                 .create();
        result.add(high);

        DailyDetectValueType low = new Builder().name("低压")
                                                .unit("mmhg")
                                                .start(30)
                                                .end(240)
                                                .create();
        result.add(low);

        DailyDetectValueType bmp = new Builder().name("心率")
                                                .unit("bmp")
                                                .start(30)
                                                .end(220)
                                                .create();
        result.add(bmp);

        high.setMinValue(low);
        low.setMaxValue(low);
        return result;
    }

}
