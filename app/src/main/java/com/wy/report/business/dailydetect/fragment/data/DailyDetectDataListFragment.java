package com.wy.report.business.dailydetect.fragment.data;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cantalou.android.util.DeviceUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.wy.report.R;
import com.wy.report.base.constant.BundleKey;
import com.wy.report.base.constant.RxKey;
import com.wy.report.base.fragment.NetworkFragment;
import com.wy.report.base.model.ResponseModel;
import com.wy.report.business.auth.model.User;
import com.wy.report.business.dailydetect.model.DailyDetectDataModel;
import com.wy.report.business.dailydetect.model.DailyDetectValueModel;
import com.wy.report.business.dailydetect.service.DailyDetectService;
import com.wy.report.business.home.model.DailyDetectTypeModel;
import com.wy.report.helper.retrofit.RetrofitHelper;
import com.wy.report.helper.retrofit.subscriber.NetworkSubscriber;
import com.wy.report.manager.auth.UserManger;
import com.wy.report.util.DensityUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;

/*
 *
 * @author cantalou
 * @date 2017-12-31 14:50
 */
public abstract class DailyDetectDataListFragment extends NetworkFragment {

    private static final String[] exceptionValue = new String[]{"偏", "异", "高", "低", "不", "胖", "瘦"};

    protected ArrayList<DailyDetectDataModel> data;

    protected DailyDetectService dailyDetectService;

    protected DailyDetectTypeModel typeModel;

    protected SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @BindView(R.id.fragment_daily_detect_data_list_title_type)
    TextView titleType;

    @BindView(R.id.daily_detect_data_list_container)
    LinearLayout dataListContainer;

    private User user;

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        Bundle arguments = getArguments();
        typeModel = arguments.getParcelable(BundleKey.BUNDLE_KEY_MODEL);

        dailyDetectService = RetrofitHelper.getInstance()
                                           .create(DailyDetectService.class);
        user = UserManger.getInstance()
                         .getLoginUser();
    }

    @Override
    protected int contentLayoutID() {
        return R.layout.fragment_daily_detect_data_list;
    }

    @NonNull
    protected abstract String createShowValue(DailyDetectValueModel valueModel);

    @Override
    protected void initView(View contentView) {
        super.initView(contentView);
    }


    @Subscribe(tags = {@Tag(RxKey.RX_DAILY_DETECT_DATA_LOADED)})
    public void dataLoaded(ArrayList<DailyDetectDataModel> data) {
        dataListContainer.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        for (DailyDetectDataModel item : data) {
            View itemView = inflater.inflate(R.layout.vh_daily_detect_data_list_item, dataListContainer, false);
            itemView.setTag(item);
            BaseViewHolder helper = new BaseViewHolder(itemView);
            String describe = item.getDescribe();
            helper.setText(R.id.vh_daily_detect_data_list_item_value, createShowValue(item.getRes()))
                  .setText(R.id.vh_daily_detect_data_list_item_state, describe)
                  .setTextColor(R.id.vh_daily_detect_data_list_item_state, !isException(describe) ? getColor(R.color.hui_575757) : getColor(R.color.hong_f84d4d))
                  .setText(R.id.vh_daily_detect_data_list_item_time, formatDate(item.getTestTime()));

            helper.getView(R.id.vh_daily_detect_data_list_content)
                  .getLayoutParams().width = DeviceUtils.getDeviceWidthPixels(getActivity()) - DensityUtils.dip2px(getActivity(), 20);

            View deleteView = helper.getView(R.id.vh_daily_detect_data_list_delete);
            deleteView.setTag(item);
            deleteView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final DailyDetectDataModel item = (DailyDetectDataModel) v.getTag();
                    dailyDetectService.deleteRecord(user.getId(), item.getId())
                                      .subscribe(new NetworkSubscriber<ResponseModel>(DailyDetectDataListFragment.this) {
                                          @Override
                                          public void handleSuccess(ResponseModel responseModel) {
                                              super.handleSuccess(responseModel);
                                              for (int i = 0; i < dataListContainer.getChildCount(); i++) {
                                                  View view = dataListContainer.getChildAt(i);
                                                  if (item.equals(view.getTag())) {
                                                      dataListContainer.removeViewAt(i);
                                                  }
                                              }
                                              rxBus.post(RxKey.RX_DAILY_DETECT_DATA_DELETE, item);
                                          }
                                      });
                }
            });
            dataListContainer.addView(itemView);
        }
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


}
