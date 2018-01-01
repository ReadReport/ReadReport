package com.wy.report.helper.dailydetect;

import android.util.SparseArray;

import com.wy.report.business.home.model.DailyDetectModel;
import com.wy.report.helper.dailydetect.impl.BloodPressureCreator;
import com.wy.report.widget.view.dailydetect.DailyDetectValueType;

import java.util.List;

/*
 *
 * @author cantalou
 * @date 2018-01-01 22:01
 */
public class DailyDetectHelper {

    private static SparseArray<DailyDetectValueCreator> types = new SparseArray<>();

    static {
        types.put(DailyDetectModel.DETECT_TYPE_BLOOD_PRESSURE, new BloodPressureCreator());
    }

    public static List<DailyDetectValueType> getTypes(DailyDetectModel model) {
        return types.get(model.getId())
                    .create();
    }
}
