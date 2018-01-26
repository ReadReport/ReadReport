package com.wy.report.business.read.fragment;

import android.os.Bundle;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.wy.report.R;
import com.wy.report.base.constant.BundleKey;
import com.wy.report.base.fragment.PtrListFragment;
import com.wy.report.base.model.ResponseModel;
import com.wy.report.business.read.mode.AskItemMode;
import com.wy.report.business.read.service.ReadService;
import com.wy.report.helper.retrofit.RetrofitHelper;
import com.wy.report.helper.retrofit.subscriber.PtrSubscriber;
import com.wy.report.manager.auth.UserManger;
import com.wy.report.manager.router.AuthRouterManager;
import com.wy.report.util.StringUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

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

    @BindView(R.id.input_content)
    EditText    mContent;


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
    }

    @Override
    protected BaseQuickAdapter createAdapter() {
        return mAskAdapter;
    }

    @Override
    protected void loadData() {
        super.loadData();
        mReadService.getAskList(mReportId, 1).subscribe(new PtrSubscriber<ResponseModel<List<AskItemMode>>>(this)
        {
            @Override
            public void onNext(ResponseModel<List<AskItemMode>> listResponseModel) {
                super.onNext(listResponseModel);
                mAskAdapter.setNewData(listResponseModel.getData());
            }
        });
    }

    @Override
    protected int contentLayoutID() {
        return R.layout.fragment_ask_fragment;
    }

    public class AskAdapter extends BaseQuickAdapter<AskItemMode, BaseViewHolder> {

        private MultiTypeDelegate<AskItemMode> mTypeDelegate;
        private SparseIntArray                 mLayouts;

        public AskAdapter() {
            super(0);
            mLayouts = new SparseIntArray();
            mLayouts.append(0, R.layout.view_ask_left);
            mLayouts.append(1, R.layout.view_ask_right);
            mTypeDelegate = new MultiTypeDelegate<AskItemMode>(mLayouts) {
                @Override
                protected int getItemType(AskItemMode askItemMode) {
                    if (askItemMode.isDoctor()) {
                        return 0;
                    } else {
                        return 1;
                    }
                }
            };

            setMultiTypeDelegate(mTypeDelegate);
        }

        @Override
        protected void convert(BaseViewHolder helper, AskItemMode item) {
            helper.setText(R.id.ask_content, item.getConversation());
            helper.setText(R.id.ask_date, item.getShowTime());
            helper.setText(R.id.ask_name, item.getDoctorId());
        }
    }

    @OnClick(R.id.input_submit)
    public void submit()
    {
        String       uid     = UserManger.getInstance().getLoginUser().getId();
        final String name    = UserManger.getInstance().getLoginUser().getName();
        final String content = mContent.getText().toString();
        if(StringUtils.isNotBlank(mContent))
        {
            AskItemMode askItemMode = new AskItemMode();
            askItemMode.setConversation(content);
            askItemMode.setDoctorId(name);
            mAskAdapter.addData(askItemMode);

//            mReadService.ask(mReportId,uid,content).subscribe(new PtrSubscriber<ResponseModel>(this)
//            {
//                @Override
//                public void onNext(ResponseModel responseModel) {
//                    super.onNext(responseModel);
//                    AskItemMode askItemMode = new AskItemMode();
//                    askItemMode.setConversation(content);
//                    askItemMode.setDoctorId(name);
//                    mAskAdapter.addData(askItemMode);
//                }
//            });
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        super.onItemClick(adapter, view, position);
        AuthRouterManager.getInstance().getRouter().open(getActivity(),AuthRouterManager.ROUTER_DOCTOR_DETAIL);
    }
}
