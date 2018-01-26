package com.wy.report.business.read.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wy.report.R;
import com.wy.report.widget.view.recycleview.NestedLinearLayoutManager;
import com.zyyoona7.lib.BaseCustomPopup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BHM on 17/12/17.
 */
public class ManagePopMenu extends BaseCustomPopup
        implements BaseQuickAdapter.OnItemClickListener {

    private List<PopItem> items;

    private static final String TAG = "ManagePopMenu";

    private RecyclerView recyclerView;

    protected BaseQuickAdapter<PopItem,BaseViewHolder> quickAdapter;

    public ManagePopMenu(Context context) {
        super(context);
    }

    @Override
    protected void initAttributes() {
        recyclerView = new RecyclerView(getContext());
        recyclerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        recyclerView.setLayoutManager(new NestedLinearLayoutManager(getContext()));
        createAdapter();
        recyclerView.setAdapter(quickAdapter);

        setContentView(recyclerView);
        setBackgroundDimEnable(false);
        setOutsideTouchable(true);
        setFocusAndOutsideEnable(true);
    }

    @Override
    protected void initViews(View view) {

    }


    @Override
    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        dismiss();
    }


    public void initData()
    {
        items = new ArrayList<>();
        PopItem item1 = new PopItem();
        item1.setId("1");
        item1.setContent("父亲");
        PopItem item2 = new PopItem();
        item2.setId("1");
        item2.setContent("儿子");
        items.add(item1);
        items.add(item2);
    }

    private void createAdapter()
    {
        initData();
        quickAdapter = new PopAdapter(R.layout.view_report_manage_pop_item);
        quickAdapter.setOnItemClickListener(this);
        quickAdapter.onAttachedToRecyclerView(recyclerView);
        quickAdapter.addData(items);
    }

    public class PopItem
    {
        private String id;
        private String content;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public class PopAdapter extends BaseQuickAdapter<PopItem, BaseViewHolder>
    {

        public PopAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, PopItem item) {
            helper.setText(R.id.pop_item_tv,item.getContent());
        }
    }
}
