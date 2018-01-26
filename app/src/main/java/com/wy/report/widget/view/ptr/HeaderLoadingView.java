package com.wy.report.widget.view.ptr;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.cantalou.android.util.Log;
import com.wy.report.R;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * @author cantalou
 * @date 2018年01月26日 14:57
 */
public class HeaderLoadingView extends FrameLayout implements PtrUIHandler {

    private ImageView loadingIv;

    private static final int[] loadingResId = new int[]{R.drawable.loading9, R.drawable.loading1, R.drawable.loading2, R.drawable.loading3,
            R.drawable.loading4, R.drawable.loading5, R.drawable.loading6, R.drawable.loading7, R.drawable.loading8};

    private int currentPosition;

    public HeaderLoadingView(Context context) {
        super(context);
        initViews(null);
    }

    public HeaderLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(attrs);
    }

    public HeaderLoadingView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews(attrs);
    }

    protected void initViews(AttributeSet attrs) {
        LayoutInflater.from(getContext())
                      .inflate(R.layout.view_ptr_loading, this);
        loadingIv = (ImageView) findViewById(R.id.prr_header_loading_iv);
        resetView();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        resetView();
    }

    private void showAnimation() {
        AnimationDrawable ad = (AnimationDrawable) getResources().getDrawable(R.drawable.ptr_header_loading);
        loadingIv.setImageDrawable(ad);
        ad.start();
    }

    private void resetView() {
        Drawable d = loadingIv.getDrawable();
        if (d instanceof AnimationDrawable) {
            ((AnimationDrawable) d).stop();
        }
        loadingIv.setImageResource(R.drawable.loading9);
    }


    @Override
    public void onUIReset(PtrFrameLayout frame) {
        resetView();
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        resetView();
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        showAnimation();
    }


    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        final int mOffsetToRefresh = frame.getOffsetToRefresh();
        final int currentPos = ptrIndicator.getCurrentPosY();
        int position = (int) ((currentPos * 1.0 / mOffsetToRefresh) * 8);
        if (position > 8) {
            position = 8;
        }
        Log.d("mOffsetToRefresh:{},currentPos:{},percent:{},position:{}", mOffsetToRefresh, currentPos, (currentPos * 1.0 / mOffsetToRefresh), position);

        if (isUnderTouch && status == 2) {
            if (currentPosition != position) {
                loadingIv.setImageResource(loadingResId[position]);
                currentPosition = position;
            }
        }
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout ptrFrameLayout, boolean b) {
        resetView();
    }
}
