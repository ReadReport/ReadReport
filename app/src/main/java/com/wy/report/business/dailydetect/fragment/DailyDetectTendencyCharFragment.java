package com.wy.report.business.dailydetect.fragment;

import com.wy.report.R;
import com.wy.report.base.fragment.NetworkFragment;

/*
 *
 * @author cantalou
 * @date 2017-12-31 14:50
 */
public class DailyDetectTendencyCharFragment extends NetworkFragment {

    @Override
    protected int contentLayoutID() {
        return R.layout.fragment_daily_detect_tendency_chart;
    }

    @Override
    protected int toolbarFlag() {
        return 0;
    }
}
