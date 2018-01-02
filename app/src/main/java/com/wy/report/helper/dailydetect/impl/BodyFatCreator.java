package com.wy.report.helper.dailydetect.impl;

import com.wy.report.helper.dailydetect.DailyDetectValueCreator;
import com.wy.report.widget.view.dailydetect.DailyDetectValueType;
import com.wy.report.widget.view.dailydetect.DailyDetectValueType.Builder;

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
    public List<DailyDetectValueType> create() {

        List<DailyDetectValueType> result = new ArrayList<>();

        DailyDetectValueType dailyDetectValueType = new Builder().name("体脂率")
                                                                 .unit("%")
                                                                 .start(1)
                                                                 .end(100)
                                                                 .create();
        result.add(dailyDetectValueType);
        return result;
    }
}
