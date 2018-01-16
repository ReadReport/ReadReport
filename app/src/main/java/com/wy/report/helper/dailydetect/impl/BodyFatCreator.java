package com.wy.report.helper.dailydetect.impl;

import com.wy.report.helper.dailydetect.DailyDetectValueCreator;
import com.wy.report.widget.view.dailydetect.ValueType;
import com.wy.report.widget.view.dailydetect.ValueType.Builder;

import java.util.ArrayList;
import java.util.List;

/**
 * 最低1%，最高100%
 *
 * @author cantalou
 * @date 2018年01月02日 14:09
 */
public class BodyFatCreator implements DailyDetectValueCreator {

    @Override
    public List<ValueType> create() {

        List<ValueType> result = new ArrayList<>();

        ValueType dailyDetectValueType = new Builder().name("体脂率")
                                                      .unit("%")
                                                      .start(1)
                                                      .end(100)
                                                      .startIndex(20)
                                                      .create();
        result.add(dailyDetectValueType);
        return result;
    }
}
