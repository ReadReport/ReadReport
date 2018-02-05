package com.wy.report.business.read.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wy.report.R;
import com.wy.report.base.constant.BundleKey;
import com.wy.report.base.fragment.PtrListFragment;
import com.wy.report.base.model.ResponseModel;
import com.wy.report.business.read.mode.AskItemMode;
import com.wy.report.business.read.mode.AskMode;
import com.wy.report.business.read.service.ReadService;
import com.wy.report.helper.retrofit.RetrofitHelper;
import com.wy.report.helper.retrofit.subscriber.PtrSubscriber;
import com.wy.report.manager.auth.UserManger;
import com.wy.report.manager.router.AuthRouterManager;
import com.wy.report.util.StringUtils;

import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-1-17 下午7:55
 * @description: ReadReport
 */
public class AskFragment extends PtrListFragment {

    private AskAdapter  mAskAdapter;
    private ReadService mReadService;
    private String      mReportId;

    int page;
    int pageCount = 1;


    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mReportId = getActivity().getIntent().getStringExtra(BundleKey.BUNDLE_KEY_REPORT_ID);
        ptrWithoutToolbar = true;
        mAskAdapter = new AskAdapter();
        mReadService = RetrofitHelper.getInstance().create(ReadService.class);
    }

    @Override
    protected void initView(View contentView) {
        super.initView(contentView);
        setTitle(R.string.report_ask_title);
        ptrFrameLayout.autoRefresh();
        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                recyclerView.scrollToPosition(mAskAdapter.getData().size() - 1);
            }
        });
    }


    @Override
    protected BaseQuickAdapter createAdapter() {
        return mAskAdapter;
    }

    @Override
    protected void loadData() {
        page++;
        if (page > pageCount) {
            return;
        }
        getData(page);
    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler2.checkContentCanBePulledDown(frame, recyclerView, header);
    }

    @Override
    public boolean checkCanDoLoadMore(PtrFrameLayout frame, View view, View view1) {
        return PtrDefaultHandler2.checkContentCanBePulledUp(frame, recyclerView, view1);
    }

    @Override
    protected int contentLayoutID() {
        return R.layout.fragment_ask_fragment;
    }

    public class AskAdapter extends BaseMultiItemQuickAdapter<AskItemMode, BaseViewHolder> {

        public AskAdapter() {
            super(null);
            addItemType(AskItemMode.DOCTOR_LAYOUT_TYPE, R.layout.view_ask_left);
            addItemType(AskItemMode.USER_LAYOUT_TYPE, R.layout.view_ask_right);
        }

        @Override
        protected void convert(BaseViewHolder helper, AskItemMode item) {
            helper.setText(R.id.ask_content, item.getConversation());
            helper.setText(R.id.ask_date, item.getShowTime());
            String name = item.isDoctor() ? item.getDoctorName() : item.getName();
            helper.setText(R.id.ask_name, name);
            //设置头像
            ImageView header = helper.getView(R.id.ask_head_icon);
            String    url    = item.isDoctor() ? item.getDoctorImage() : item.getUserImage();
            Glide.with(AskFragment.this).load(url).into(header);
        }
    }

    public void submitContent(final EditText editText) {
        String       uid     = UserManger.getInstance().getLoginUser().getId();
        final String name    = UserManger.getInstance().getLoginUser().getName();
        final String content = editText.getText().toString();
        if (StringUtils.isNotBlank(editText)) {
            mReadService.ask(mReportId, uid, content).subscribe(new PtrSubscriber<ResponseModel>(this) {
                @Override
                public void onNext(ResponseModel responseModel) {
                    super.onNext(responseModel);
                    editText.getText().clear();
                    AskItemMode askItemMode = new AskItemMode();
                    askItemMode.setConversation(content);
                    askItemMode.setUserImage(UserManger.getInstance().getLoginUser().getHead());
                    askItemMode.setMemberId(UserManger.getInstance().getLoginUser().getId());
                    askItemMode.setName(name);
                    mAskAdapter.addData(askItemMode);
                }
            });
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        super.onItemClick(adapter, view, position);
        AskItemMode askItemMode = (AskItemMode) adapter.getData().get(position);
        if (askItemMode.isDoctor()) {
            Bundle bundle = new Bundle();
            bundle.putString(BundleKey.BUNDLE_KEY_DOCTOR_ID, askItemMode.getDoctorId());
            AuthRouterManager.getInstance().getRouter().open(getActivity(), AuthRouterManager.ROUTER_DOCTOR_DETAIL,bundle);
        }
    }

    private void getData(final int page) {
        mReadService.getAskList(mReportId, page).subscribe(new PtrSubscriber<ResponseModel<AskMode>>(this) {
            @Override
            public void onNext(ResponseModel<AskMode> askModeResponseModel) {
                super.onNext(askModeResponseModel);
                mAskAdapter.addData(0, askModeResponseModel.getData().getAskItemModes());
                pageCount = askModeResponseModel.getData().getCount();
                if (page == 1) {
                    recyclerView.scrollToPosition(mAskAdapter.getData().size() - 1);
                }
            }
        });
    }
}
