package com.wy.report.business.dailydetect.fragment.data;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseViewHolder;
import com.wy.report.R;
import com.wy.report.business.dailydetect.model.DailyDetectDataModel;
import com.wy.report.business.dailydetect.model.DailyDetectValueModel;

/**
 * @author cantalou
 * @date 2018年01月17日 11:05
 */
public class BloodFatDataFragment extends DailyDetectDataListFragment {
    @Override
    protected void initView(View contentView) {
        super.initView(contentView);
        titleType.setText(R.string.daily_detect_data_list_title_blood_fat);
    }

    @NonNull
    @Override
    protected String createShowValue(DailyDetectValueModel valueModel) {
        return "";
    }

    @NonNull
    @Override
    protected View createItemView(LayoutInflater inflater, ViewGroup parent, DailyDetectDataModel item) {
        View itemView = inflater.inflate(R.layout.vh_daily_detect_data_list_item_blood_fat, parent, false);
        itemView.setTag(item);

        BaseViewHolder helper = new BaseViewHolder(itemView);
        String[] describes = parseDescribe(item.getDescribe());

        DailyDetectValueModel value = item.getRes();

        helper.setText(R.id.vh_daily_detect_data_list_item_value0, "总胆固醇: " + value.getCholValue())
              .setText(R.id.vh_daily_detect_data_list_item_state0, describes[0])
              .setTextColor(R.id.vh_daily_detect_data_list_item_state0, !isException(describes[0]) ? getColor(R.color.hui_575757) : getColor(R.color.hong_f84d4d))
              .setText(R.id.vh_daily_detect_data_list_item_time0, formatDate(item.getTestTime()));

        helper.setText(R.id.vh_daily_detect_data_list_item_value1, "甘油三酯: " + value.getTrigValue())
              .setText(R.id.vh_daily_detect_data_list_item_state1, describes[1])
              .setTextColor(R.id.vh_daily_detect_data_list_item_state1, !isException(describes[1]) ? getColor(R.color.hui_575757) : getColor(R.color.hong_f84d4d))
              .setText(R.id.vh_daily_detect_data_list_item_time1, formatDate(item.getTestTime()));

        helper.setText(R.id.vh_daily_detect_data_list_item_value2, "高胆固醇: " + value.getHdlValue())
              .setText(R.id.vh_daily_detect_data_list_item_state2, describes[2])
              .setTextColor(R.id.vh_daily_detect_data_list_item_state2, !isException(describes[2]) ? getColor(R.color.hui_575757) : getColor(R.color.hong_f84d4d))
              .setText(R.id.vh_daily_detect_data_list_item_time2, formatDate(item.getTestTime()));

        helper.setText(R.id.vh_daily_detect_data_list_item_value3, "低胆固醇: " + value.getLdlValue())
              .setText(R.id.vh_daily_detect_data_list_item_state3, describes[3])
              .setTextColor(R.id.vh_daily_detect_data_list_item_state3, !isException(describes[3]) ? getColor(R.color.hui_575757) : getColor(R.color.hong_f84d4d))
              .setText(R.id.vh_daily_detect_data_list_item_time3, formatDate(item.getTestTime()));
        return itemView;
    }

    private String[] parseDescribe(String describe) {
        String[] result = new String[4];
        String[] split = describe.split("-");
        for (int i = 0; i < result.length; i++) {
            if (i < split.length) {
                result[i] = split[i];
            } else {
                result[i] = "";
            }
        }
        return result;
    }
}
