package com.wy.report.business.home.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wy.report.R;
import com.wy.report.base.viewholder.BaseViewHolder;
import com.wy.report.business.home.model.DailyDetectTypeModel;

import butterknife.BindView;

/*
 *
 * @author cantalou
 * @date 2017-12-03 22:36
 */
public class DailyDetectViewHolder extends BaseViewHolder<DailyDetectTypeModel> {

    @BindView(R.id.home_find_daily_detect_item_icon)
    ImageView icon;

    @BindView(R.id.home_find_daily_detect_item_title)
    TextView title;

    public DailyDetectViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void onBindViewHolder(DailyDetectTypeModel model) {
        super.onBindViewHolder(model);
        icon.setImageResource(model.getIconID());
        title.setText(model.getTitle());
    }

}
