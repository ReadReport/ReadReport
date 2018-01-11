package com.wy.report.business.dailydetect.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.wy.report.R;
import com.wy.report.base.constant.BundleKey;
import com.wy.report.base.constant.RxKey;
import com.wy.report.base.fragment.PtrListFragment;
import com.wy.report.base.model.ResponseModel;
import com.wy.report.business.dailydetect.model.DailyDetectDataModel;
import com.wy.report.business.dailydetect.service.DailyDetectService;
import com.wy.report.helper.retrofit.RetrofitHelper;
import com.wy.report.helper.retrofit.subscriber.NetworkSubscriber;

import java.util.ArrayList;

import butterknife.BindView;

/*
 *
 * @author cantalou
 * @date 2017-12-31 14:50
 */
public class DailyDetectDataListFragment extends PtrListFragment<DailyDetectDataModel, BaseViewHolder> {

    private ArrayList<DailyDetectDataModel> datas;

    private boolean editMode = false;

    @BindView(R.id.fragment_daily_detect_data_list_title_type)
    TextView titleType;

    private DailyDetectService dailyDetectService;

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        Bundle arguments = getArguments();
        if (arguments != null) {
            datas = arguments.getParcelableArrayList(BundleKey.BUNDLE_KEY_DAILY_DETECT_DATA);
        }

        dailyDetectService = RetrofitHelper.getInstance()
                                           .create(DailyDetectService.class);
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
                helper.setText(R.id.vh_daily_detect_data_list_item_value, item.getRes())
                      .setText(R.id.vh_daily_detect_data_list_item_state, item.getSuggest())
                      .setText(R.id.vh_daily_detect_data_list_item_time, item.getTestTime())
                      .setVisible(R.id.vh_daily_detect_data_list_item_delete, editMode)
                      .addOnClickListener(R.id.vh_daily_detect_data_list_item_delete);
            }
        };
    }

    @Override
    protected void initView(View contentView) {
        super.initView(contentView);
        quickAdapter.bindToRecyclerView(recyclerView);
        quickAdapter.setEmptyView(R.layout.view_daily_detect_empty);
        quickAdapter.setNewData(datas);
        quickAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
                DailyDetectDataModel model = quickAdapter.getItem(position);
                dailyDetectService.deleteRecord(model.getId())
                                  .subscribe(new NetworkSubscriber<ResponseModel>(DailyDetectDataListFragment.this) {
                                      @Override
                                      public void handleSuccess(ResponseModel responseModel) {
                                          super.handleSuccess(responseModel);
                                          quickAdapter.remove(position);
                                          quickAdapter.notifyItemRemoved(position);
                                      }
                                  });
            }
        });
        //quickAdapter.addHeaderView(initHeaderView());
    }


    @Subscribe(tags = {@Tag(RxKey.RX_DAILY_DETECT_DATA_EDIT_MODE)})
    public void editMode(Boolean editMode) {
        this.editMode = editMode;
        quickAdapter.notifyDataSetChanged();
    }

    protected View initHeaderView() {
        return null;
    }

    @Override
    protected int toolbarFlag() {
        return 0;
    }
}
