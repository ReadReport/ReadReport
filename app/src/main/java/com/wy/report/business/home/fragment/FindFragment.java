package com.wy.report.business.home.fragment;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cantalou.android.util.DeviceUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.wy.report.R;
import com.wy.report.base.constant.RxKey;
import com.wy.report.base.fragment.PtrFragment;
import com.wy.report.base.model.ResponseModel;
import com.wy.report.business.home.model.DailyDetectModel;
import com.wy.report.business.home.model.HealthKnowledgeModel;
import com.wy.report.business.home.model.HealthTestModel;
import com.wy.report.business.home.model.HomeFindModel;
import com.wy.report.business.home.service.HomeService;
import com.wy.report.helper.retrofit.RetrofitHelper;
import com.wy.report.helper.retrofit.subscriber.PtrSubscriber;
import com.wy.report.manager.massage.MessageManager;
import com.wy.report.manager.router.AuthRouterManager;
import com.wy.report.util.DensityUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/*
 * @author cantalou
 * @date 2017-11-26 23:03
 */
public class FindFragment extends PtrFragment {

    private static final int DAILY_DETECT_LINE_NUM = 4;

    @BindView(R.id.toolbar_msg_icon)
    ImageView toolbarMsgIcon;

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @BindView(R.id.view_pager)
    ViewPager dailyDetectViewPager;

    private MessageManager messageManager;

    private HomeService homeService;

    private BaseQuickAdapter<HealthTestModel,BaseViewHolder> healthTestAdapter;

    private BaseQuickAdapter<HealthKnowledgeModel,BaseViewHolder> healthKnowledageAdapter;

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        messageManager = MessageManager.getInstance();
        homeService = RetrofitHelper.getInstance()
                                    .create(HomeService.class);
        ptrWithoutToolbar = true;
    }

    @Override
    protected void initView(View contentView) {
        super.initView(contentView);
        ptrFrameLayout.disableWhenHorizontalMove(true);

        healthTestAdapter = new BaseQuickAdapter<HealthTestModel, BaseViewHolder>(R.id.home_find_health_knowledge_icon) {
            @Override
            protected void convert(BaseViewHolder helper, HealthTestModel item) {

            }
        };
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        toolbarTitle.setText(R.string.home_find_title);
        toolbarTitle.setTextColor(getResources().getColor(android.R.color.black));
        updateToolbarMessageState(null);
    }

    @Subscribe(tags = {@Tag(RxKey.RX_NEW_MESSAGE)})
    public void updateToolbarMessageState(Object obj) {
        toolbarMsgIcon.setImageResource(messageManager.hasNewMessage() ? R.drawable.nav_noticeb_new : R.drawable.nav_noticeb);
    }

    @Override
    protected int contentLayoutID() {
        return R.layout.fragment_find;
    }

    @Override
    protected int toolbarLayoutID() {
        return R.layout.view_home_toolbar;
    }

    @Override
    protected void loadData() {
        homeService.getFindInfo()
                   .subscribe(new PtrSubscriber<ResponseModel<HomeFindModel>>(this) {
                       @Override
                       public void onNext(ResponseModel<HomeFindModel> homeFindModelResponseModel) {
                           super.onNext(homeFindModelResponseModel);

                           HomeFindModel model = homeFindModelResponseModel.getData();
                           initDailyDetect(model.getDailyDetectModels());
                       }
                   });
    }

    private void initDailyDetect(List<DailyDetectModel> dailyDetects) {

        final int itemWidth = (DeviceUtils.getDeviceWidthPixels(getActivity()) - DensityUtils.dip2px(getActivity(), 20)) / DAILY_DETECT_LINE_NUM;

        final ArrayList<ArrayList<DailyDetectModel>> dailyDetectLines = new ArrayList<>();
        ArrayList<DailyDetectModel> line = null;
        for (int i = 0; i < dailyDetects.size(); i++) {
            if (i % DAILY_DETECT_LINE_NUM == 0) {
                line = new ArrayList<>();
                dailyDetectLines.add(line);
            }
            line.add(dailyDetects.get(i));
        }

        dailyDetectViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return dailyDetectLines.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ArrayList<DailyDetectModel> line = dailyDetectLines.get(position);
                LinearLayout lineContainer = new LinearLayout(getActivity());
                LayoutInflater layoutInflater = getLayoutInflater(null);
                for (DailyDetectModel model : line) {
                    View item = layoutInflater.inflate(R.layout.view_home_find_daily_detect_item, lineContainer, false);
                    ImageView icon = (ImageView) item.findViewById(R.id.home_find_daily_detect_item_icon);
                    icon.setImageResource(model.getIconID());
                    TextView title = (TextView) item.findViewById(R.id.home_find_daily_detect_item_title);
                    title.setText(model.getTitle());
                    lineContainer.addView(item, new LayoutParams(itemWidth, LayoutParams.WRAP_CONTENT));
                    item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            router.open(getActivity(), AuthRouterManager.ROUTER_DAILY_DETECT);
                        }
                    });
                }
                container.addView(lineContainer, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                return lineContainer;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });

    }
}
