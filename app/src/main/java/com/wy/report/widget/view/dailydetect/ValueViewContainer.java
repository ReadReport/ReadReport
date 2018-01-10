package com.wy.report.widget.view.dailydetect;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.wy.report.widget.view.wheel.WheelViewItem;

import java.util.ArrayList;
import java.util.List;

/*
 *
 * @author cantalou
 * @date 2017-12-26 23:13
 */
public class ValueViewContainer extends LinearLayout {

    public ValueViewContainer(Context context) {
        super(context);
        setOrientation(LinearLayout.HORIZONTAL);
    }

    public ValueViewContainer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.HORIZONTAL);
    }

    public void setData(List<ValueType> types) {
        for (ValueType type : types) {
            ValueView view = new ValueView(getContext());
            LinearLayout.LayoutParams lp = new LayoutParams(0, LayoutParams.WRAP_CONTENT);
            lp.weight = 1;
            addView(view, lp);
            view.setData(type);
        }
    }

    public List<String> getWheelValues() {
        ArrayList<String> values = new ArrayList<>();
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child instanceof ValueView) {
                WheelViewItem item = ((ValueView) child).getWheelView()
                                                        .getSelectedItem();
                values.add(item.getValue());
            }
        }
        return values;
    }
}
