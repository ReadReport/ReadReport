package com.wy.report.widget.view.recycleview;

import android.content.Context;
import android.graphics.PointF;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;

/*
 *
 * @author cantalou
 * @date 2018-01-10 21:45
 */
public class SmoothScrollLayoutManager extends LinearLayoutManager {

    private float speed = 150f;

    public SmoothScrollLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public SmoothScrollLayoutManager(Context context, int orientation, boolean reverseLayout, float speed) {
        super(context, orientation, reverseLayout);
        this.speed = speed;
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, final int position) {

        LinearSmoothScroller smoothScroller = new LinearSmoothScroller(recyclerView.getContext()) {
            @Override
            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                return speed / displayMetrics.densityDpi;
            }

            @Override
            public PointF computeScrollVectorForPosition(int targetPosition) {
                return SmoothScrollLayoutManager.this.computeScrollVectorForPosition(targetPosition);
            }

            @Override protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }
        };

        smoothScroller.setTargetPosition(position);
        startSmoothScroll(smoothScroller);
    }
}