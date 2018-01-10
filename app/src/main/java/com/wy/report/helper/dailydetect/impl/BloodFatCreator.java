package com.wy.report.helper.dailydetect.impl;

import com.wy.report.helper.dailydetect.DailyDetectValueCreator;
import com.wy.report.widget.view.dailydetect.ValueType;
import com.wy.report.widget.view.dailydetect.ValueType.Builder;

import java.util.ArrayList;
import java.util.List;

/**
 * 总胆固醇：2.8～5 .17mmol/L；
 * 甘油三酯：0 .56～1.7mmol/L ；
 * 高密度脂蛋白：男性：0 .96～1.15mmol/L;女性：0 .90～1 .55mmol/L；
 * 低密度脂蛋白：0～3.1mmol/L，胆固醇和甘油三酯是血脂的一部分
 *
 * @author cantalou
 * @date 2018年01月02日 14:24
 */
public class BloodFatCreator implements DailyDetectValueCreator {

    @Override
    public List<ValueType> create() {

        List<ValueType> result = new ArrayList<>();

        ValueType tc = new Builder().name("总胆固醇")
                                    .unit("mmol/L")
                                    .start(1.0)
                                    .end(9.9)
                                    .fraction("0.0")
                                    .delta(0.1)
                                    .create();
        result.add(tc);

        ValueType tg = new Builder().name("甘油三酯")
                                    .unit("mmol/L")
                                    .start(0.10)
                                    .end(3.00)
                                    .fraction("0.0")
                                    .delta(0.1)
                                    .create();
        result.add(tg);


        ValueType hdl = new Builder().name("高胆固醇")
                                     .unit("mmol/L")
                                     .start(0.10)
                                     .end(9.9)
                                     .fraction("0.00")
                                     .delta(0.01)
                                     .create();
        result.add(hdl);

        ValueType ldl = new Builder().name("低胆固醇")
                                     .unit("mmol/L")
                                     .start(0)
                                     .end(3.1)
                                     .fraction("0.0")
                                     .delta(0.1)
                                     .create();
        result.add(ldl);

        return result;
    }
}
