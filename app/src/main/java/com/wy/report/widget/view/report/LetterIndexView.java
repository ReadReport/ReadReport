package com.wy.report.widget.view.report;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wy.report.R;
import com.wy.report.util.DensityUtils;

/*
 *
 * @author cantalou
 * @date 2018-02-11 21:01
 */
public class LetterIndexView extends LinearLayout {

    private OnLetterChangedListener mLetterChangedListener;

    public LetterIndexView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public LetterIndexView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public LetterIndexView(Context context) {
        super(context);
        init(context, null);
    }

    private void init(Context context, AttributeSet attrs) {
        setOrientation(LinearLayout.VERTICAL);
        int widthHeight = DensityUtils.dip2px(getContext(), 20);
        for (int i = 'A'; i <= 'Z'; i++) {
            TextView charView = new TextView(getContext());
            charView.setGravity(Gravity.CENTER);
            charView.setBackgroundResource(R.drawable.selector_blue_circle_bg);
            charView.setText(Character.toString((char) i));
            addView(charView, new LayoutParams(widthHeight, widthHeight));
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    public void setOnLetterChangedListener(OnLetterChangedListener letterChangedListener) {
        mLetterChangedListener = letterChangedListener;
    }

    private boolean isTouchPointInView(View view, int x, int y) {
        if (view == null) {
            return false;
        }
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        if (y >= top && y <= bottom && x >= left && x <= right) {
            return true;
        }
        return false;
    }

    /**
     * 触摸选中的字母发生改变的接口
     */
    public interface OnLetterChangedListener {
        void onChanged(String s, int position);
    }
}
