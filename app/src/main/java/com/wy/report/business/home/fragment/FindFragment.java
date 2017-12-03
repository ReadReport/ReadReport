package com.wy.report.business.home.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wy.report.R;
import com.wy.report.base.fragment.BaseFragment;
import com.wy.report.base.fragment.PtrFragment;
import com.wy.report.base.model.ResponseModel;
import com.wy.report.business.home.model.HomeFindModel;
import com.wy.report.business.home.service.HomeService;
import com.wy.report.helper.retrofit.RetrofitHelper;
import com.wy.report.helper.retrofit.subscriber.PtrSubscriber;
import com.wy.report.manager.massage.MessageManager;

import java.util.List;

import butterknife.BindView;

/*
 * @author cantalou
 * @date 2017-11-26 23:03
 */
public class FindFragment extends PtrFragment {

    @BindView(R.id.toolbar_msg_icon)
    ImageView toolbarMsgIcon;

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    private MessageManager messageManager;

    private HomeService homeService;

    @Override
    protected void initData() {
        messageManager = MessageManager.getInstance();
        homeService = RetrofitHelper.getInstance()
                                    .create(HomeService.class);
    }

    @Override
    protected void initView(View contentView) {
        super.initView(contentView);
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        toolbarTitle.setText(R.string.home_find_title);
        updateToolbarMessageState();
    }

    private void updateToolbarMessageState() {
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
                   .subscribe(new PtrSubscriber<ResponseModel<HomeFindModel>>(this){
                       @Override
                       public void onNext(ResponseModel<HomeFindModel> homeFindModelResponseModel) {
                           super.onNext(homeFindModelResponseModel);

                       }
                   });
    }
}
