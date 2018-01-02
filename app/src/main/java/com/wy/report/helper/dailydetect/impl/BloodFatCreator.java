package com.wy.report.helper.dailydetect.impl;

import com.wy.report.business.auth.model.User;
import com.wy.report.helper.dailydetect.DailyDetectValueCreator;
import com.wy.report.manager.auth.UserManger;
import com.wy.report.widget.view.dailydetect.DailyDetectValueType;
import com.wy.report.widget.view.dailydetect.DailyDetectValueType.Builder;

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
    public List<DailyDetectValueType> create() {

        List<DailyDetectValueType> result = new ArrayList<>();

        DailyDetectValueType tc = new Builder().name("总胆固醇")
                                               .unit("mmol/L")
                                               .start(2.8)
                                               .end(5.7)
                                               .fraction("0.0")
                                               .delta(0.1)
                                               .create();
        result.add(tc);

        DailyDetectValueType tg = new Builder().name("甘油三酯")
                                               .unit("mmol/L")
                                               .start(0.56)
                                               .end(5.17)
                                               .fraction("0.0")
                                               .delta(0.1)
                                               .create();
        result.add(tg);

        User user = UserManger.getInstance()
                              .getLoginUser();
        Builder builder = new Builder().name("高胆固醇")
                                       .unit("mmol/L")
                                       .fraction("0.00")
                                       .delta(0.01);
        if (user.getSex() == User.GENDER_MALE) {
            builder.start(0.96)
                   .end(1.15);
        } else {
            builder.start(0.90)
                   .end(1.55);
        }
        result.add(builder.create());

        DailyDetectValueType ldl = new Builder().name("低胆固醇")
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
