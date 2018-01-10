package com.wy.report.widget.view.wheel;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wy.report.R;
import com.wy.report.base.viewholder.BaseViewHolder;
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

    private int x;

    private int y;

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
        setLayoutManager(linearLayoutManager = new SmoothScrollLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new BaseQuickAdapter<WheelViewItem, BaseViewHolder>(R.layout.vh_wheel_item) {
            @Override
            protected void convert(BaseViewHolder helper, WheelViewItem item) {
                ((TextView) helper.getConvertView()).setText(item.getTitle());
            }
        };
        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                int position = linearLayoutManager.findFirstVisibleItemPosition();
                if (down) {
                    smoothScrollToPosition(position);
                } else {
                    smoothScrollToPosition(position + 1);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > y) {
                    down = true;
                }
                y = dy;
            }
        });
    }

    public void setData(List<WheelViewItem> data) {
        adapter.setNewData(data);
    }

    public WheelViewItem getSelectedItem() {
        int first = linearLayoutManager.findFirstVisibleItemPosition();
        return adapter.getItem(first + 2);
    }

}
