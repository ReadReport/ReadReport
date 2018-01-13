package com.wy.report.business.dailydetect.fragment;

import android.os.Bundle;

import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.wy.report.R;
import com.wy.report.base.constant.BundleKey;
import com.wy.report.base.constant.RxKey;
import com.wy.report.base.fragment.NetworkFragment;
import com.wy.report.business.dailydetect.model.DailyDetectDataModel;

import java.util.List;

/*
 *
 * @author cantalou
 * @date 2017-12-31 14:50
 */
public class DailyDetectTendencyCharFragment extends NetworkFragment {

    private List<DailyDetectDataModel> data;

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            data = arguments.getParcelableArrayList(BundleKey.BUNDLE_KEY_DAILY_DETECT_DATA);
        }
    }

    @Override
    protected int contentLayoutID() {
        return R.layout.fragment_daily_detect_tendency_chart;
    }

    @Override
    protected int toolbarFlag() {
        return 0;
    }

    @Subscribe(tags = {@Tag(RxKey.RX_DAILY_DETECT_DATA_ADD)})
    public void addData(DailyDetectDataModel model) {
        data.remove(model);
        updateTendencyChar();
    }

    @Subscribe(tags = {@Tag(RxKey.RX_DAILY_DETECT_DATA_DELETE)})
    public void deleteData(DailyDetectDataModel model) {
        data.remove(model);
        updateTendencyChar();
    }

    private void updateTendencyChar(){

    }
}
