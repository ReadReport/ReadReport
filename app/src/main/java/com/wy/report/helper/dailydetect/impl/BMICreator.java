package com.wy.report.helper.dailydetect.impl;

import com.wy.report.helper.dailydetect.DailyDetectValueCreator;
import com.wy.report.widget.view.dailydetect.ValueType;
import com.wy.report.widget.view.dailydetect.ValueType.Builder;

import java.util.ArrayList;
import java.util.List;

/**
 * 体重：最低30kg，最高300kg
 * 身高：最低80cm，最高250cm
 *
 * @author cantalou
 * @date 2018年01月02日 13:57
 */
public class BMICreator implements DailyDetectValueCreator {

    @Override
    public List<ValueType> create() {

        List<ValueType> result = new ArrayList<>();

        ValueType weight = new Builder().name("体重")
                                        .unit("kg")
                                        .start(30)
                                        .end(300)
                                        .create();
        result.add(weight);

        ValueType height = new Builder().name("身高")
                                        .unit("cm")
                                        .start(80)
                                        .end(250)
                                        .create();
        result.add(height);

        return result;
    }
}
