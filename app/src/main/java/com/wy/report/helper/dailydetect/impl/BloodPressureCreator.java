package com.wy.report.helper.dailydetect.impl;

import com.wy.report.helper.dailydetect.DailyDetectValueCreator;
import com.wy.report.widget.view.dailydetect.ValueType;
import com.wy.report.widget.view.dailydetect.ValueType.Builder;

import java.util.ArrayList;
import java.util.List;

/*
 *
 * @author cantalou
 * @date 2018-01-01 22:26
 */
public class BloodPressureCreator implements DailyDetectValueCreator {

    @Override
    public List<ValueType> create() {
        List<ValueType> result = new ArrayList<>();

        ValueType high = new Builder().name("高压")
                                      .unit("mmhg")
                                      .start(30)
                                      .end(240)
                                      .startIndex(50)
                                      .create();
        result.add(high);

        ValueType low = new Builder().name("低压")
                                     .unit("mmhg")
                                     .start(30)
                                     .startIndex(30)
                                     .end(240)
                                     .create();
        result.add(low);

        ValueType bmp = new Builder().name("心率")
                                     .unit("bmp")
                                     .start(30)
                                     .startIndex(50)
                                     .end(220)
                                     .create();
        result.add(bmp);

        high.setMinValue(low);
        low.setMaxValue(low);
        return result;
    }

}
