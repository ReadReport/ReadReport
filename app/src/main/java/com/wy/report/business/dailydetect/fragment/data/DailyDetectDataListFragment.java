package com.wy.report.business.dailydetect.fragment.data;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.daimajia.swipe.SwipeLayout;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.wy.report.R;
import com.wy.report.base.constant.BundleKey;
import com.wy.report.base.constant.RxKey;
import com.wy.report.base.fragment.PtrListFragment;
import com.wy.report.base.model.ResponseModel;
import com.wy.report.business.dailydetect.model.DailyDetectDataModel;
import com.wy.report.business.dailydetect.service.DailyDetectService;
import com.wy.report.business.home.model.DailyDetectTypeModel;
import com.wy.report.helper.retrofit.RetrofitHelper;
import com.wy.report.helper.retrofit.subscriber.NetworkSubscriber;
import com.wy.report.widget.view.recycleview.NestedLinearLayoutManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;

/*
 *
 * @author cantalou
 * @date 2017-12-31 14:50
 */
public abstract class DailyDetectDataListFragment extends PtrListFragment<DailyDetectDataModel, BaseViewHolder> {

    @BindView(R.id.fragment_daily_detect_data_list_title_type)
    TextView titleType;

    protected ArrayList<DailyDetectDataModel> data;

    protected DailyDetectService dailyDetectService;

    protected DailyDetectTypeModel typeModel;

    protected SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        Bundle arguments = getArguments();
        typeModel = arguments.getParcelable(BundleKey.BUNDLE_KEY_MODEL);

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
                String[] values = item.getValues();
                helper.setText(R.id.vh_daily_detect_data_list_item_value, createShowValue(values))
                      .setText(R.id.vh_daily_detect_data_list_item_state, item.getDescribe())
                      .setText(R.id.vh_daily_detect_data_list_item_time, formatDate(item.getTestTime()))
                      .addOnClickListener(R.id.bottom_wrapper);
                SwipeLayout swipeLayout = (SwipeLayout) helper.getConvertView();
                swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
            }
        };
    }

    @NonNull
    protected String createShowValue(String[] values) {
        return values[0] + "/" + values[1];
    }

    @Override
    protected void initView(View contentView) {
        super.initView(contentView);
        quickAdapter.bindToRecyclerView(recyclerView);
        quickAdapter.setEmptyView(R.layout.view_daily_detect_empty);
        quickAdapter.setNewData(data);
        quickAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
                final DailyDetectDataModel model = quickAdapter.getItem(position);
                dailyDetectService.deleteRecord(model.getId())
                                  .subscribe(new NetworkSubscriber<ResponseModel>(DailyDetectDataListFragment.this) {
                                      @Override
                                      public void handleSuccess(ResponseModel responseModel) {
                                          super.handleSuccess(responseModel);
                                          //处理并发删除时position不准确
                                          int position = data.indexOf(model);
                                          if (position > 0) {
                                              quickAdapter.remove(position);
                                              rxBus.post(RxKey.RX_DAILY_DETECT_DATA_DELETE, model);
                                          }
                                      }
                                  });
            }
        });
        ptrFrameLayout.setEnabled(false);
    }

    @Override
    protected void initRecycleView() {
        recyclerView.setLayoutManager(new NestedLinearLayoutManager(getActivity()));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        quickAdapter = createAdapter();
        recyclerView.setAdapter(quickAdapter);
    }

    @Subscribe(tags = {@Tag(RxKey.RX_DAILY_DETECT_DATA_LOADED)})
    public void dataLoaded(ArrayList<DailyDetectDataModel> data) {
        quickAdapter.setNewData(data);
        recyclerView.getParent().requestLayout();
    }

    @Subscribe(tags = {@Tag(RxKey.RX_DAILY_DETECT_DATA_ADD)})
    public void addData(DailyDetectDataModel model) {
        quickAdapter.addData(0, model);
    }

    @Override
    protected int toolbarFlag() {
        return 0;
    }

    protected String formatDate(long time) {
        return format.format(new Date(time * 1000));
    }
}
