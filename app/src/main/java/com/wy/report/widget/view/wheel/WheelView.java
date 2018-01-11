package com.wy.report.widget.view.wheel;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.TextView;

import com.cantalou.android.util.Log;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wy.report.R;
import com.wy.report.widget.view.recycleview.SmoothScrollLayoutManager;

import java.util.List;

/**
 * @author cantalou
 * @date 2018年01月10日 17:20
 * <p>
 */
public class WheelView extends RecyclerView {

    private BaseQuickAdapter<WheelViewItem, BaseViewHolder> adapter;

    private LinearLayoutManager linearLayoutManager;

    private boolean down = false;

    public WheelView(Context context) {
        super(context);
        initView();
    }

    public WheelView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        setLayoutManager(linearLayoutManager = new SmoothScrollLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false, 200));
        adapter = new BaseQuickAdapter<WheelViewItem, BaseViewHolder>(R.layout.vh_wheel_item) {
            @Override
            protected void convert(BaseViewHolder helper, WheelViewItem item) {
                ((TextView) helper.getConvertView()).setText(item.getTitle());
            }
        };
        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState != RecyclerView.SCROLL_STATE_IDLE) {
                    return;
                }

                if (linearLayoutManager.isSmoothScrolling()) {
                    return;
                }

                int position = linearLayoutManager.findFirstVisibleItemPosition();
                if (position == linearLayoutManager.findFirstCompletelyVisibleItemPosition()) {
                    return;
                }
                if (down) {
                    linearLayoutManager.scrollToPosition(position);
                } else {
                    linearLayoutManager.scrollToPositionWithOffset(position + 1,0);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (Math.abs(dy) < 2) {
                    return;
                }
                down = dy < 0;
                Log.d("onScrolled dy :{}, down :{}", dy, down);
            }
        });
        setAdapter(adapter);
    }

    public void setData(List<WheelViewItem> data) {
        adapter.setNewData(data);
    }

    public WheelViewItem getSelectedItem() {
        int first = linearLayoutManager.findFirstVisibleItemPosition();
        return adapter.getItem(first + 2);
    }

}
