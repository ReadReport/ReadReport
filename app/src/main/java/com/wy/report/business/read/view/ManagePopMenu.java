package com.wy.report.business.read.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wy.report.R;
import com.wy.report.business.my.model.FamilyItemMode;

import java.util.List;

/**
 * Created by BHM on 17/12/17.
 */
public class ManagePopMenu extends PopupWindow
        implements BaseQuickAdapter.OnItemClickListener {


    private static final String TAG = "ManagePopMenu";

    private RecyclerView recyclerView;
    private final int MAX_ITEM_COUNT = 5;

    protected BaseQuickAdapter<FamilyItemMode, BaseViewHolder> quickAdapter;

    private Context getContext;

    private int itemWitdh;
    private int itemHeight;

    private OnPopItemClick mOnPopItemClick;

    public ManagePopMenu(Context context) {
        super(context);
        getContext = context;
        initAttributes();
    }

    protected void initAttributes() {
        recyclerView = new RecyclerView(getContext);
        recyclerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext));
        createAdapter();
        recyclerView.setAdapter(quickAdapter);
        setContentView(recyclerView);

        View item = LayoutInflater.from(getContext).inflate(R.layout.view_report_manage_pop_item_new, null);
        item.measure(0, 0);

        itemWitdh = item.getMeasuredWidth();
        itemHeight = item.getMeasuredHeight();
        setHeight(0);
        setWidth(0);

        setOutsideTouchable(true);
    }


    @Override
    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        dismiss();
        if(mOnPopItemClick != null)
        {
            mOnPopItemClick.onPopItemClick((FamilyItemMode) baseQuickAdapter.getData().get(i));
        }
    }


    private void createAdapter() {
        quickAdapter = new PopAdapter(R.layout.view_report_manage_pop_item_new);
        quickAdapter.setOnItemClickListener(this);
        quickAdapter.onAttachedToRecyclerView(recyclerView);
    }

    public void setNewData(List<FamilyItemMode> newData) {
        int newCol = newData.size() > MAX_ITEM_COUNT ? MAX_ITEM_COUNT : newData.size();
        setWidth(itemWitdh);
        setHeight(itemHeight * newCol);
        quickAdapter.setNewData(newData);
    }

    public class PopAdapter extends BaseQuickAdapter<FamilyItemMode, BaseViewHolder> {

        public PopAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, FamilyItemMode item) {
            helper.setText(R.id.pop_item_tv, item.getName());
        }
    }

    public void setOnPopItemClick(OnPopItemClick onPopItemClick) {
        mOnPopItemClick = onPopItemClick;
    }

    public interface OnPopItemClick
    {
        void onPopItemClick(FamilyItemMode item);
    }
}
