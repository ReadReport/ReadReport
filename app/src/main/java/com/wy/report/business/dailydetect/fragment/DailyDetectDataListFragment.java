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
import com.wy.report.business.home.model.DailyDetectTypeModel;
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

    @BindView(R.id.fragment_daily_detect_data_list_title_type)
    TextView titleType;
    private ArrayList<DailyDetectDataModel> data;
    private boolean editMode = false;
    private DailyDetectService dailyDetectService;
    private DailyDetectTypeModel typeModel;

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        Bundle arguments = getArguments();
        data = arguments.getParcelableArrayList(BundleKey.BUNDLE_KEY_DAILY_DETECT_DATA);
        typeModel = arguments.getParcelable(BundleKey.BUNDLE_KEY_MODEL);

        dailyDetectService = RetrofitHelper.getInstance()
                                           .create(DailyDetectService.class);
        setTypeTitle(typeModel);
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
        //quickAdapter.addHeaderView(initHeaderView());
    }


    @Subscribe(tags = {@Tag(RxKey.RX_DAILY_DETECT_DATA_OPERATE)})
    public void editMode(Boolean editMode) {
        this.editMode = editMode;
        quickAdapter.notifyDataSetChanged();
    }

    @Subscribe(tags = {@Tag(RxKey.RX_DAILY_DETECT_DATA_ADD)})
    public void addData(DailyDetectDataModel model) {
        quickAdapter.addData(model);
    }

    protected View initHeaderView() {
        return null;
    }

    @Override
    protected int toolbarFlag() {
        return 0;
    }


    private void setTypeTitle(DailyDetectTypeModel typeModel) {
        switch (typeModel.getId()) {
            case DailyDetectTypeModel.DETECT_TYPE_BLOOD_PRESSURE: {
                titleType.setText(R.string.daily_detect_data_list_title_blood_pressure);
                break;
            }
            case DailyDetectTypeModel.DETECT_TYPE_BLOOD_SUGAR: {
                titleType.setText(R.string.daily_detect_data_list_title_blood_sugar);
                break;
            }

            case DailyDetectTypeModel.DETECT_TYPE_BMI: {
                titleType.setText(R.string.daily_detect_data_list_title_bmi);
                break;
            }

            case DailyDetectTypeModel.DETECT_TYPE_BODY_FAT: {
                titleType.setText(R.string.daily_detect_data_list_title_body_fat);
                break;
            }

            case DailyDetectTypeModel.DETECT_TYPE_BLOOD_FAT: {
                titleType.setText(R.string.daily_detect_data_list_title_blood_fat);
                break;
            }
        }
    }
}
