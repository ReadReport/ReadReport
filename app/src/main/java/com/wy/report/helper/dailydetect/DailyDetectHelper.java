package com.wy.report.helper.dailydetect;

import android.util.SparseArray;

import com.wy.report.business.home.model.DailyDetectTypeModel;
import com.wy.report.helper.dailydetect.impl.BMICreator;
import com.wy.report.helper.dailydetect.impl.BloodFatCreator;
import com.wy.report.helper.dailydetect.impl.BloodPressureCreator;
import com.wy.report.helper.dailydetect.impl.BloodSugarCreator;
import com.wy.report.helper.dailydetect.impl.BodyFatCreator;
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
        types.put(DailyDetectTypeModel.DETECT_TYPE_BLOOD_PRESSURE, new BloodPressureCreator());
        types.put(DailyDetectTypeModel.DETECT_TYPE_BLOOD_FAT, new BloodFatCreator());
        types.put(DailyDetectTypeModel.DETECT_TYPE_BLOOD_SUGAR, new BloodSugarCreator());
        types.put(DailyDetectTypeModel.DETECT_TYPE_BMI, new BMICreator());
        types.put(DailyDetectTypeModel.DETECT_TYPE_BODY_FAT, new BodyFatCreator());
    }

    public static List<DailyDetectValueType> getTypes(DailyDetectTypeModel model) {
        return types.get(model.getId())
                    .create();
    }
}
