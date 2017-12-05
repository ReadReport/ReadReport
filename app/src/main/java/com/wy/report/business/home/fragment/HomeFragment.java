package com.wy.report.business.home.fragment;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.wy.report.R;
import com.wy.report.base.constant.RxKey;
import com.wy.report.base.fragment.PtrFragment;
import com.wy.report.base.model.ResponseModel;
import com.wy.report.business.home.model.FeedModel;
import com.wy.report.business.home.model.HomeReportModel;
import com.wy.report.business.home.service.HomeService;
import com.wy.report.helper.retrofit.RetrofitHelper;
import com.wy.report.helper.retrofit.subscriber.PtrSubscriber;
import com.wy.report.manager.massage.MessageManager;
import com.wy.report.manager.router.AuthRouterManager;
import com.wy.report.util.StringUtils;
import com.wy.report.util.ViewUtils;
import com.wy.report.widget.ObservableScrollView;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/*
 * 首页
 * @author cantalou
 * @date 2017-11-26 22:55
 */
public class HomeFragment extends PtrFragment {

    @BindView(R.id.home_top_bg)
    ImageView topBg;

    @BindView(R.id.home_interpretation_feed_container)
    LinearLayout feedContainer;

    @BindView(R.id.home_scroll_view)
    ObservableScrollView scrollView;

    @BindView(R.id.home_use_report_num)
    TextView useReportNum;

    @BindView(R.id.toolbar_msg_icon)
    ImageView toolbarMsgIcon;

    private Drawable toolbarBackground;

    private HomeService homeService;

    private MessageManager messageManager;

    private boolean toolbarOverHalf;

    @Override
    protected void initData() {
        homeService = RetrofitHelper.getInstance()
                                    .create(HomeService.class);
        messageManager = MessageManager.getInstance();
    }

    @Override
    protected void initView(View content) {
        super.initView(content);
        ViewUtils.convertToLeftTopCrop(topBg);
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        toolbarBackground = toolbar.getBackground();
        toolbarBackground.setAlpha(0);
        final int white = getResources().getColor(android.R.color.white);
        final int black = getResources().getColor(android.R.color.black);
        scrollView.setOnScrollChangeListener(new ObservableScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                float ratio = 1f * Math.abs(scrollY) / toolbar.getHeight();
                if (ratio > 1) {
                    ratio = 1;
                }
                toolbarBackground.setAlpha((int) (255 * ratio));
                final float half = 0.5f;
                if (ratio < half) {
                    ratio = (half - ratio) / half;
                    toolbarTitle.setTextColor(white);
                    toolbarTitle.setAlpha(ratio);
                    toolbarMsgIcon.setImageResource(messageManager.hasNewMessage() ? R.drawable.nav_notice_new : R.drawable.nav_notice);
                    toolbarMsgIcon.setAlpha(ratio);
                    toolbarOverHalf = false;
                } else {
                    ratio = (ratio - half) / half;
                    toolbarTitle.setTextColor(black);
                    toolbarTitle.setAlpha(ratio);
                    toolbarMsgIcon.setImageResource(messageManager.hasNewMessage() ? R.drawable.nav_noticeb_new : R.drawable.nav_noticeb);
                    toolbarMsgIcon.setAlpha(ratio);
                    toolbarOverHalf = true;
                }
            }
        });
        updateToolbarMessageState(null);
    }

    @Subscribe(tags = {@Tag(RxKey.RX_NEW_MESSAGE)})
    public void updateToolbarMessageState(Object obj) {
        if (toolbarOverHalf) {
            toolbarMsgIcon.setImageResource(messageManager.hasNewMessage() ? R.drawable.nav_noticeb_new : R.drawable.nav_noticeb);
        } else {
            toolbarMsgIcon.setImageResource(messageManager.hasNewMessage() ? R.drawable.nav_notice_new : R.drawable.nav_notice);
        }
    }

    @Override
    protected int toolbarLayoutID() {
        return R.layout.view_home_toolbar;
    }

    @Override
    protected int contentLayoutID() {
        return R.layout.fragment_home;
    }

    protected int toolbarFlag() {
        return TOOL_BAR_FLAG_SHOW | TOOL_BAR_FLAG_OVERLAY;
    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler.checkContentCanBePulledDown(frame, scrollView, header);
    }

    private void fillFeed(List<FeedModel> data) {
        feedContainer.removeAllViews();
        LayoutInflater inflater = getLayoutInflater(null);
        for (FeedModel model : data) {
            View itemView = inflater.inflate(R.layout.view_home_feed_item, feedContainer, false);
            feedContainer.addView(itemView);

            TextView feedContent = (TextView) itemView.findViewById(R.id.home_feed_content);
            feedContent.setText(String.format(getString(R.string.home_interpretation_feed_template), StringUtils.hidePhoneNumber(model.getMobile())));

            TextView time = (TextView) itemView.findViewById(R.id.home_feed_time);
            time.setText(StringUtils.formatTime(model.getTime() * 1000));
        }
    }

    @Override
    protected void loadData() {
        homeService.getHomeInfo()
                   .subscribe(new PtrSubscriber<ResponseModel<HomeReportModel>>(this) {
                       @Override
                       public void onNext(ResponseModel<HomeReportModel> totalNumberResponseModel) {
                           super.onNext(totalNumberResponseModel);
                           HomeReportModel homeReportModel = totalNumberResponseModel.getData();
                           useReportNum.setText(new DecimalFormat("#,###").format(homeReportModel.getTotalNumber()));
                           fillFeed(homeReportModel.getReportInfo());
                       }
                   });
    }

    @OnClick(R.id.home_upload_report)
    public void onReportUploadClick() {
        AuthRouterManager.getInstance()
                         .getRouter()
                         .open(getActivity(), AuthRouterManager.LOGIN_ROUTER_UPLOAD_REPORT);
    }
}
