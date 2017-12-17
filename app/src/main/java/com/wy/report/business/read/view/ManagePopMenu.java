package com.wy.report.business.read.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.zyyoona7.lib.BaseCustomPopup;

import java.util.List;

/**
 * Created by BHM on 17/12/17.
 */

public class ManagePopMenu extends BaseCustomPopup implements BaseQuickAdapter.OnItemClickListener {

    private List<String> items;

    private static final String TAG = "ManagePopMenu";

    private RecyclerView recyclerView;

    protected BaseQuickAdapter<T, K> quickAdapter;

    protected ManagePopMenu(Context context) {
        super(context);
    }

    @Override
    protected void initAttributes() {
        setContentView(recyclerView,
                ViewGroup.LayoutParams.MATCH_PARENT, SizeUtils.dp2px(300));

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        quickAdapter = createAdapter();
        quickAdapter.setOnItemClickListener(this);
        quickAdapter.onAttachedToRecyclerView(recyclerView);
        recyclerView.setAdapter(quickAdapter);
        setFocusAndOutsideEnable(false)
                .setBackgroundDimEnable(true)
                .setDimValue(0.5f);
    }

    @Override
    protected void initViews(View view) {

    }


    @Override
    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {

    }

    private void createAdapter()
    {

    }
}
