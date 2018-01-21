package com.wy.report.business.dailydetect.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;

import com.wy.report.R;
import com.wy.report.base.constant.BundleKey;
import com.wy.report.base.constant.RxKey;
import com.wy.report.base.fragment.BaseFragment;
import com.wy.report.base.fragment.NetworkFragment;
import com.wy.report.base.model.ResponseModel;
import com.wy.report.business.auth.model.User;
import com.wy.report.business.dailydetect.model.DailyDetectDataModel;
import com.wy.report.business.dailydetect.service.DailyDetectService;
import com.wy.report.business.home.model.DailyDetectTypeModel;
import com.wy.report.helper.retrofit.RetrofitHelper;
import com.wy.report.helper.retrofit.subscriber.NetworkSubscriber;
import com.wy.report.manager.auth.UserManger;
import com.wy.report.widget.tab.TwoTabLayoutDetect;
import com.wy.report.widget.view.dailydetect.ValueView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/*
 *
 * 日常检测
 * @author cantalou
 * @date 2017-12-26 21:14
 */
public abstract class DailyDetectFragment extends NetworkFragment implements Toolbar.OnMenuItemClickListener {

    protected DailyDetectService dailyDetectService;

    protected ViewGroup detectValueContainerView;

    protected User user;

    protected DailyDetectTypeModel model;

    @BindView(R.id.daily_detect_framelayout_container)
    FrameLayout frameLayoutContainer;

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @BindView(R.id.daily_detect_tab_container)
    TwoTabLayoutDetect tabLayout;

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        model = getArguments().getParcelable(BundleKey.BUNDLE_KEY_MODEL);
        dailyDetectService = RetrofitHelper.getInstance()
                                           .create(DailyDetectService.class);
        user = UserManger.getInstance()
                         .getLoginUser();
    }

    @Override
    protected void loadData() {
        dailyDetectService.getDetectData(UserManger.getUid(), model.getId())
                          .subscribe(new NetworkSubscriber<ResponseModel<List<DailyDetectDataModel>>>(null) {
                              @Override
                              public void handleSuccess(ResponseModel<List<DailyDetectDataModel>> dailyDetectDataModelResponseModel) {
                                  super.handleSuccess(dailyDetectDataModelResponseModel);
                                  List<DailyDetectDataModel> data = dailyDetectDataModelResponseModel.getData();
                                  rxBus.post(RxKey.RX_DAILY_DETECT_DATA_LOADED, data);
                              }
                          });
    }

    @Override
    protected void initView(View contentView) {
        super.initView(contentView);
        detectValueContainerView = createDetectValueView();
        frameLayoutContainer.addView(detectValueContainerView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        final BaseFragment[] fragments = getFragments();
        for (BaseFragment fragment : fragments) {
            Bundle arg = new Bundle();
            arg.putParcelableArrayList(BundleKey.BUNDLE_KEY_DAILY_DETECT_DATA, null);
            arg.putParcelable(BundleKey.BUNDLE_KEY_MODEL, model);
            fragment.setArguments(arg);
        }
        viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments[position];
            }

            @Override
            public int getCount() {
                return fragments.length;
            }
        });
        tabLayout.setUpWithViewPager(viewPager);
        loadData();
    }

    protected abstract BaseFragment[] getFragments();

    protected abstract ViewGroup createDetectValueView();

    @Override
    protected int contentLayoutID() {
        return R.layout.fragment_daily_detect;
    }

    @Override
    protected int getMenuLayoutId() {
        return R.menu.menu_find_daily_detect;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        toolbar.setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.find_daily_detect_more: {
                break;
            }
        }
        return false;
    }

    @OnClick(R.id.daily_detect_save)
    public void saveRecord(View view) {
        loadData();
    }

    protected String getValue(int index) {
        return ((ValueView) detectValueContainerView.getChildAt(index)).getWheelView()
                                                                       .getSelectedItem()
                                                                       .getValue();
    }
}
