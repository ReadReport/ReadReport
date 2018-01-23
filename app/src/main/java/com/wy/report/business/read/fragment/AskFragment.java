package com.wy.report.business.read.fragment;

import android.os.Bundle;
import android.util.SparseIntArray;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.wy.report.R;
import com.wy.report.base.fragment.PtrListFragment;
import com.wy.report.base.model.ResponseModel;
import com.wy.report.business.read.mode.AskItemMode;
import com.wy.report.business.read.service.ReadService;
import com.wy.report.helper.retrofit.RetrofitHelper;
import com.wy.report.helper.retrofit.subscriber.PtrSubscriber;

import java.util.List;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-1-17 下午7:55
 * @description: ReadReport
 */
public class AskFragment extends PtrListFragment {

    private AskAdapter  mAskAdapter;
    private ReadService mReadService;


    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mAskAdapter = new AskAdapter();
        mReadService = RetrofitHelper.getInstance().create(ReadService.class);
    }

    @Override
    protected BaseQuickAdapter createAdapter() {
        return mAskAdapter;
    }

    @Override
    protected void loadData() {
        super.loadData();
        mReadService.getAskList("1", 1).subscribe(new PtrSubscriber<ResponseModel<List<AskItemMode>>>(this)
        {
            @Override
            public void onNext(ResponseModel<List<AskItemMode>> listResponseModel) {
                super.onNext(listResponseModel);
                mAskAdapter.setNewData(listResponseModel.getData());
            }
        });
    }

    public class AskAdapter extends BaseQuickAdapter<AskItemMode, BaseViewHolder> {

        private MultiTypeDelegate<AskItemMode> mTypeDelegate;
        private SparseIntArray                 mLayouts;

        public AskAdapter() {
            super(R.layout.view_ask_left);
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

}
