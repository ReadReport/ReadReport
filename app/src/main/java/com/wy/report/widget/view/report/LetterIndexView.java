package com.wy.report.widget.view.report;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
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

    private OnLetterChangedListener listener;

    private String selectedChar = "";

    private LinearLayout charContainer;

    private TextView indicator;

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
        setOrientation(LinearLayout.HORIZONTAL);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.view_letter_index, this);
        charContainer = (LinearLayout) findViewById(R.id.view_letter_index_chars_container);
        indicator = (TextView) findViewById(R.id.view_letter_index_indicator);
        for (int i = 'A'; i <= 'Z'; i++) {
            String charStr = Character.toString((char) i);
            TextView charView = (TextView) inflater.inflate(R.layout.view_letter_index_char, charContainer, false);
            charView.setText(charStr);
            charView.setTag(charStr);
            charContainer.addView(charView);
        }
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent ev) {
        for (int i = 0; i < charContainer.getChildCount(); i++) {
            View child = charContainer.getChildAt(i);
            if (isTouchPointInView(child, ev)) {
                return true;
            }
        }
        return super.onInterceptHoverEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        for (int i = 0; i < charContainer.getChildCount(); i++) {
            View child = charContainer.getChildAt(i);
            if (isTouchPointInView(child, event)) {
                String tag = (String) child.getTag();
                if (listener != null && !selectedChar.equals(tag)) {
                    listener.onChanged(tag, i);
                }
                indicator.setText(tag);
            }
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                indicator.setVisibility(View.VISIBLE);
                break;
            }
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP: {
                indicator.setVisibility(View.GONE);
                break;
            }
        }
        return super.onTouchEvent(event) || true;
    }

    public void setOnLetterChangedListener(OnLetterChangedListener letterChangedListener) {
        listener = letterChangedListener;
    }

    private boolean isTouchPointInView(View view, MotionEvent event) {
        if (view == null) {
            return false;
        }
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        float x = event.getRawX();
        float y = event.getRawY();
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
