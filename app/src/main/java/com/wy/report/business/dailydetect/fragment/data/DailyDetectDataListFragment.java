package com.wy.report.business.dailydetect.fragment.data;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.cantalou.android.util.Log;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.wy.report.R;
import com.wy.report.base.constant.BundleKey;
import com.wy.report.base.constant.RxKey;
import com.wy.report.base.fragment.PtrListFragment;
import com.wy.report.base.model.ResponseModel;
import com.wy.report.business.dailydetect.model.DailyDetectDataModel;
import com.wy.report.business.dailydetect.model.DailyDetectValueModel;
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

    private static final String[] exceptionValue = new String[]{"偏", "异", "高", "低", "不", "胖", "瘦"};
    protected ArrayList<DailyDetectDataModel> data;

    protected DailyDetectService dailyDetectService;

    protected DailyDetectTypeModel typeModel;

    protected SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    @BindView(R.id.fragment_daily_detect_data_list_title_type)
    TextView titleType;

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
        return new SwipeAdapter(R.layout.vh_daily_detect_data_list_item);
    }

    @NonNull
    protected abstract String createShowValue(DailyDetectValueModel valueModel);

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
    }

    @Override
    protected int toolbarFlag() {
        return 0;
    }

    protected String formatDate(long time) {
        return format.format(new Date(time * 1000));
    }

    protected boolean isException(String describe) {
        for (String s : exceptionValue) {
            if (describe.contains(s)) {
                return true;
            }
        }
        return false;
    }

    private class SwipeAdapter extends BaseQuickAdapter<DailyDetectDataModel, BaseViewHolder> {

        public SwipeAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, DailyDetectDataModel item) {
            String describe = item.getDescribe();
            helper.setText(R.id.vh_daily_detect_data_list_item_value, createShowValue(item.getRes()))
                  .setText(R.id.vh_daily_detect_data_list_item_state, describe)
                  .setTextColor(R.id.vh_daily_detect_data_list_item_state, !isException(describe) ? getColor(R.color.hui_575757) : getColor(R.color.hong_f84d4d))
                  .setText(R.id.vh_daily_detect_data_list_item_time, formatDate(item.getTestTime()))
                  .addOnClickListener(R.id.bottom_wrapper);
            SwipeLayout swipeLayout = helper.getView(R.id.swipe);
            swipeLayout.addDrag(SwipeLayout.DragEdge.Left, helper.getView(R.id.bottom_wrapper));
            swipeLayout.addSwipeListener(new SimpleSwipeListener() {
                @Override
                public void onOpen(SwipeLayout layout) {
                    super.onOpen(layout);
                    Log.d("onOpen");
                }

                @Override
                public void onClose(SwipeLayout layout) {
                    super.onClose(layout);
                    Log.d("onClose");
                }
            });
        }
    }
}
