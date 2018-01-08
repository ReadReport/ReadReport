package com.wy.report.business.dailydetect.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.wy.report.R;
import com.wy.report.base.constant.BundleKey;
import com.wy.report.base.constant.RxKey;
import com.wy.report.base.fragment.PtrListFragment;
import com.wy.report.business.dailydetect.model.DailyDetectDataModel;
import com.wy.report.business.home.model.DailyDetectTypeModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/*
 *
 * @author cantalou
 * @date 2017-12-31 14:50
 */
public class DailyDetectDataListFragment extends PtrListFragment<DailyDetectDataModel, BaseViewHolder> {

    private ArrayList<DailyDetectDataModel> datas;

    private DailyDetectTypeModel typeModel;

    private boolean editMode = false;

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        Bundle arguments = getArguments();
        if (arguments != null) {
            datas = arguments.getParcelableArrayList(BundleKey.BUNDLE_KEY_DAILY_DETECT_DATA);

            DailyDetectDataModel header = new DailyDetectDataModel();
            datas.add(0, header);
        }
    }

    @Override
    protected int contentLayoutID() {
        return R.layout.fragment_daily_detect_data_list;
    }

    @Override
    protected BaseQuickAdapter createAdapter() {
        return new BaseQuickAdapter<DailyDetectDataModel, BaseViewHolder>(R.layout.vh_daily_detect_data_list_item) {
            @Override
            protected void convert(BaseViewHolder helper, DailyDetectDataModel item) {
                helper.setText(R.id.vh_daily_detect_data_list_item_value, item.getOptions())
                      .setText(R.id.vh_daily_detect_data_list_item_state, item.getOptions())
                      .setText(R.id.vh_daily_detect_data_list_item_time, item.getOptions())
                      .setVisible(R.id.vh_daily_detect_data_list_item_delete, editMode)
                      .addOnClickListener(R.id.vh_daily_detect_data_list_item_delete);
            }
        };
    }

    @Override
    protected void initView(View contentView) {
        super.initView(contentView);
        quickAdapter.setEmptyView(R.layout.view_daily_detect_empty);
        quickAdapter.setNewData(datas);
        quickAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                adapter.remove(position);
                adapter.notifyItemRemoved(position);
            }
        });
    }


    @Subscribe(tags = {@Tag(RxKey.RX_DAILY_DETECT_DATA_EDIT_MODE)})
    public void editMode(Boolean editMode) {
        this.editMode = editMode;
        quickAdapter.notifyDataSetChanged();
    }
}
