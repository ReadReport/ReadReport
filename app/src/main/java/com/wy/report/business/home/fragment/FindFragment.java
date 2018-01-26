package com.wy.report.business.home.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cantalou.android.util.DeviceUtils;
import com.cantalou.android.util.ReflectUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.wy.report.R;
import com.wy.report.base.constant.BundleKey;
import com.wy.report.base.constant.RxKey;
import com.wy.report.base.fragment.PtrFragment;
import com.wy.report.base.model.ResponseModel;
import com.wy.report.business.home.model.DailyDetectTypeModel;
import com.wy.report.business.home.model.HealthKnowledgeModel;
import com.wy.report.business.home.model.HealthTestModel;
import com.wy.report.business.home.model.HomeFindHealthyKnowledgeModel;
import com.wy.report.business.home.model.HomeFindHealthyTestModel;
import com.wy.report.business.home.model.HomeFindModel;
import com.wy.report.business.home.service.HomeService;
import com.wy.report.helper.retrofit.RetrofitHelper;
import com.wy.report.helper.retrofit.subscriber.PtrSubscriber;
import com.wy.report.manager.massage.MessageManager;
import com.wy.report.manager.router.AuthRouterManager;
import com.wy.report.util.DensityUtils;
import com.wy.report.widget.view.recycleview.NestedLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

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

    @BindView(R.id.home_find_health_test_rv)
    RecyclerView healthTestRecycleView;

    @BindView(R.id.home_find_health_knowledge_rv)
    RecyclerView healthKnowledgeRecycleView;

    private MessageManager messageManager;

    private HomeService homeService;

    private BaseQuickAdapter<HealthTestModel, BaseViewHolder> healthTestAdapter;

    private BaseQuickAdapter<HealthKnowledgeModel, BaseViewHolder> healthKnowledgeAdapter;

    private HomeFindModel model;

    private AuthRouterManager authRouterManager;

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        messageManager = MessageManager.getInstance();
        homeService = RetrofitHelper.getInstance()
                                    .create(HomeService.class);
        ptrWithoutToolbar = true;
        authRouterManager = AuthRouterManager.getInstance();
    }

    @Override
    protected void initView(View contentView) {
        super.initView(contentView);
        ptrFrameLayout.disableWhenHorizontalMove(true);
        ViewConfiguration conf = ViewConfiguration.get(getActivity());
        ReflectUtil.set(ptrFrameLayout, "mPagingTouchSlop", conf.getScaledTouchSlop() / 2);

        healthTestRecycleView.setLayoutManager(new NestedLinearLayoutManager(getActivity()));
        healthTestAdapter = new BaseQuickAdapter<HealthTestModel, BaseViewHolder>(R.layout.vh_find_heanth_test_item) {
            @Override
            protected void convert(BaseViewHolder helper, HealthTestModel item) {
                Glide.with(getActivity())
                     .load(item.getImage())
                     .into((ImageView) helper.getView(R.id.vh_find_health_test_item_icon));
                helper.setText(R.id.vh_find_health_test_item_title, item.getTitle())
                      .setText(R.id.vh_find_health_test_item_test_num, item.getTestedNum())
                      .addOnClickListener(R.id.vh_find_health_test_item_do_test);
            }
        };
        healthTestAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                HealthTestModel model = (HealthTestModel) adapter.getItem(position);
                authRouterManager.openWebView(getActivity(), model.getId(), model.getTitle());
            }
        });
        healthTestAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        healthTestRecycleView.setAdapter(healthTestAdapter);
        healthTestRecycleView.setHasFixedSize(true);
        healthTestRecycleView.setNestedScrollingEnabled(false);


        healthKnowledgeRecycleView.setLayoutManager(new NestedLinearLayoutManager(getActivity()));
        healthKnowledgeAdapter = new BaseQuickAdapter<HealthKnowledgeModel, BaseViewHolder>(R.layout.vh_find_heanth_knowledge_item) {
            @Override
            protected void convert(BaseViewHolder helper, HealthKnowledgeModel item) {
                Glide.with(getActivity())
                     .load(item.getImage())
                     .into((ImageView) helper.getView(R.id.home_find_health_knowledge_item_icon));
                helper.setText(R.id.home_find_health_knowledge_item_title, item.getTitle())
                      .setText(R.id.home_find_health_knowledge_item_desc, item.getDesc());
            }
        };
        healthKnowledgeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                HealthKnowledgeModel model = (HealthKnowledgeModel) adapter.getItem(position);
                authRouterManager.openLoginWebView(getActivity(), model.getUrl(), model.getTitle());
            }
        });
        healthKnowledgeRecycleView.setAdapter(healthKnowledgeAdapter);
        healthKnowledgeRecycleView.setHasFixedSize(true);
        healthKnowledgeRecycleView.setNestedScrollingEnabled(false);

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
                           model = homeFindModelResponseModel.getData();
                           updateData();
                       }
                   });
    }

    private void updateData() {

        final int itemWidth = (DeviceUtils.getDeviceWidthPixels(getActivity()) - DensityUtils.dip2px(getActivity(), 20)) / DAILY_DETECT_LINE_NUM;

        final ArrayList<ArrayList<DailyDetectTypeModel>> dailyDetectLines = new ArrayList<>();
        ArrayList<DailyDetectTypeModel> line = null;
        List<DailyDetectTypeModel> dailyDetects = model.getDailyDetectTypeModels();
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
                ArrayList<DailyDetectTypeModel> line = dailyDetectLines.get(position);
                LinearLayout lineContainer = new LinearLayout(getActivity());
                LayoutInflater layoutInflater = getLayoutInflater(null);
                for (DailyDetectTypeModel model : line) {
                    View item = layoutInflater.inflate(R.layout.view_home_find_daily_detect_item, lineContainer, false);
                    ImageView icon = (ImageView) item.findViewById(R.id.home_find_daily_detect_item_icon);
                    icon.setImageResource(model.getIconID());
                    TextView title = (TextView) item.findViewById(R.id.home_find_daily_detect_item_title);
                    title.setText(model.getTitle());
                    lineContainer.addView(item, new LayoutParams(itemWidth, LayoutParams.WRAP_CONTENT));
                    item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle bundle = new Bundle();
                            bundle.putParcelable(BundleKey.BUNDLE_KEY_MODEL, (Parcelable) v.getTag());
                            router.open(getActivity(), AuthRouterManager.ROUTER_DAILY_DETECT, bundle);
                        }
                    });
                    item.setTag(model);
                }
                container.addView(lineContainer, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                lineContainer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        router.open(getActivity(), AuthRouterManager.ROUTER_DAILY_DETECT);
                    }
                });
                return lineContainer;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });

        HomeFindHealthyTestModel testModel = model.getHomeFindHealthyTestModel();
        healthTestAdapter.setNewData(testModel.getHealthTestModels());

        HomeFindHealthyKnowledgeModel knowledgeModel = model.getHomeFindHealthyKnowledgeModel();
        healthKnowledgeAdapter.setNewData(knowledgeModel.getHealthKnowledgeModels());
    }

    @OnClick(R.id.home_find_health_test_more)
    public void clickHealthTestMore() {
        if (model == null) {
            return;
        }
        HomeFindHealthyTestModel testModel = model.getHomeFindHealthyTestModel();
        authRouterManager.openWebView(getActivity(), testModel.getMore(), "");
    }

    @OnClick(R.id.home_find_health_knowledge_more)
    public void clickHealthKnowledgeMore() {
        if (model == null) {
            return;
        }
        HomeFindHealthyKnowledgeModel knowledgeModel = model.getHomeFindHealthyKnowledgeModel();
        authRouterManager.openWebView(getActivity(), knowledgeModel.getMore(), "");
    }
}
