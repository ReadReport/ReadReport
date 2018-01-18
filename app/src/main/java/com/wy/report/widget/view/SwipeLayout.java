package com.wy.report.widget.view;


import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.cantalou.android.util.Log;

/**
 * TODO.
 *
 * @author cantalou
 * @date 2018年01月18日 13:04
 * <p>
 */
public class SwipeLayout extends com.daimajia.swipe.SwipeLayout {

    public SwipeLayout(Context context) {
        super(context);
    }

    public SwipeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SwipeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean res = super.onInterceptTouchEvent(ev);
        Log.d("onInterceptTouchEvent :{} , :{}",ev, res);
        return res;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("onTouchEvent :{} ",event);
        if(event.getAction() == MotionEvent.ACTION_UP){
            return true;
        }
        return super.onTouchEvent(event);
    }
}
