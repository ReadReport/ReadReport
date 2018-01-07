package com.wy.report.business.dailydetect.fragment;

import android.os.Bundle;
import android.view.View;

import com.wy.report.base.constant.BundleKey;
import com.wy.report.base.fragment.BaseFragment;
import com.wy.report.business.home.model.DailyDetectTypeModel;

/*
 *
 * 日常检测分发
 * @author cantalou
 * @date 2017-12-26 21:14
 */
public class DailyDetectDispatchFragment extends BaseFragment {

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
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

    @Override
    protected void initView(View contentView) {
    }

    private DailyDetectFragment getFragment(DailyDetectTypeModel model) {
        switch (model.getId()) {
            case DailyDetectTypeModel.DETECT_TYPE_BLOOD_SUGAR:{
                return new DailyDetectBloosSugarFragment();
            }
            default: {
                return new DailyDetectFragment();
            }
        }
    }

    @Override
    protected int contentLayoutID() {
        return 0;
    }
}
