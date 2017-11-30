package com.wy.report.business.home.fragment;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wy.report.R;
import com.wy.report.base.fragment.NetworkFragment;
import com.wy.report.base.model.BaseModel;
import com.wy.report.business.home.model.FeedModel;
import com.wy.report.business.home.model.HomeReportModel;
import com.wy.report.business.home.service.HomeService;
import com.wy.report.helper.retrofit.RetrofitHelper;
import com.wy.report.helper.retrofit.subscriber.PtrSubscriber;
import com.wy.report.util.StringUtils;
import com.wy.report.util.ViewUtils;
import com.wy.report.widget.ObservableScrollView;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/*
 * 首页
 * @author cantalou
 * @date 2017-11-26 22:55
 */
public class HomeFragment extends NetworkFragment {

    @BindView(R.id.home_top_bg)
    ImageView topBg;

    @BindView(R.id.home_interpretation_feed_container)
    LinearLayout feedContainer;

    @BindView(R.id.home_scroll_view)
    ObservableScrollView scrollView;

    @BindView(R.id.home_use_report_num)
    TextView useReportNum;

    private Drawable toolbarBackground;

    private HomeService homeService;

    @Override
    protected void initData() {
        homeService = RetrofitHelper.getInstance()
                                    .create(HomeService.class);
    }

    @Override
    protected void initView(View content) {
        super.initView(content);
        ViewUtils.convertToLeftTopCrop(topBg);

        toolbarBackground = toolbar.getBackground();
        toolbarBackground.setAlpha(0);
        scrollView.setOnScrollChangeListener(new ObservableScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                int alpha = (int) (255.0 * Math.abs(scrollY) / toolbar.getHeight());
                alpha = alpha > 255 ? 255 : alpha;
                toolbarBackground.setAlpha(alpha);
            }
        });
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
        homeService.getTotalNumber()
                   .subscribe(new PtrSubscriber<BaseModel<HomeReportModel>>(this) {
                       @Override
                       public void onNext(BaseModel<HomeReportModel> totalNumberBaseModel) {
                           super.onNext(totalNumberBaseModel);
                           HomeReportModel homeReportModel = totalNumberBaseModel.getData();
                           useReportNum.setText(new DecimalFormat("#,###").format(homeReportModel.getTotalNumber()));
                           fillFeed(homeReportModel.getReportInfo());
                       }
                   });
    }
}
