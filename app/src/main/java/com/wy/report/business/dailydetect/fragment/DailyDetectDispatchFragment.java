package com.wy.report.business.dailydetect.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.wy.report.base.constant.BundleKey;
import com.wy.report.business.dailydetect.fragment.subtype.BloodPressureFragment;
import com.wy.report.business.dailydetect.fragment.subtype.BloodSugarFragment;
import com.wy.report.business.home.model.DailyDetectTypeModel;

/*
 *
 * 日常检测分发
 * @author cantalou
 * @date 2017-12-26 21:14
 */
public class DailyDetectDispatchFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        DailyDetectTypeModel model = arguments.getParcelable(BundleKey.BUNDLE_KEY_MODEL);
        try {
            DailyDetectFragment fragment = getFragment(model);
            fragment.setArguments(arguments);
            getActivity().getSupportFragmentManager()
                         .beginTransaction()
                         .replace(android.R.id.content, fragment)
                         .commitAllowingStateLoss();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private DailyDetectFragment getFragment(DailyDetectTypeModel model) {
        switch (model.getId()) {
            case DailyDetectTypeModel.DETECT_TYPE_BLOOD_PRESSURE: {
                return new BloodPressureFragment();
            }
            case DailyDetectTypeModel.DETECT_TYPE_BLOOD_SUGAR: {
                return new BloodSugarFragment();
            }
            case DailyDetectTypeModel.DETECT_TYPE_BMI: {
                return new BloodSugarFragment();
            }
            case DailyDetectTypeModel.DETECT_TYPE_BODY_FAT: {
                return new BloodSugarFragment();
            }
            case DailyDetectTypeModel.DETECT_TYPE_BLOOD_FAT: {
                return new BloodSugarFragment();
            }
            default: {
                throw new IllegalArgumentException("Unknown type " + model.getId());
            }
        }
    }

}
