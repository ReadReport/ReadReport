package com.wy.report.widget.view.dailydetect;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import java.util.List;

/*
 *
 * @author cantalou
 * @date 2017-12-26 23:13
 */
public class DailyDetectValueContainerView extends LinearLayout {

    public DailyDetectValueContainerView(Context context) {
        super(context);
        setOrientation(LinearLayout.HORIZONTAL);
    }

    public DailyDetectValueContainerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.HORIZONTAL);
    }

    public void setData(List<DailyDetectValueType> types) {
        for (DailyDetectValueType type : types) {
            DailyDetectValueView view = new DailyDetectValueView(getContext());
            LinearLayout.LayoutParams lp = new LayoutParams(0, LayoutParams.WRAP_CONTENT);
            lp.weight = 1;
            addView(view, lp);
            view.setData(type);
        }
    }
}
