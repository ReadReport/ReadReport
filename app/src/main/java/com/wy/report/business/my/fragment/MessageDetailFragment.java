package com.wy.report.business.my.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wy.report.R;
import com.wy.report.base.constant.BundleKey;
import com.wy.report.base.constant.RxKey;
import com.wy.report.base.fragment.PtrFragment;
import com.wy.report.base.model.ResponseModel;
import com.wy.report.business.my.model.MessageItemMode;
import com.wy.report.business.my.service.MyService;
import com.wy.report.helper.retrofit.RetrofitHelper;
import com.wy.report.helper.retrofit.subscriber.PtrSubscriber;
import com.wy.report.manager.auth.UserManger;

import butterknife.BindView;

/**
 * 消息详情
 *
 * @author cantalou
 * @date 2017-11-26 23:04
 */
public class MessageDetailFragment extends PtrFragment {

    private MyService myService;

    @BindView(R.id.message_detail_title)
    TextView title;
    @BindView(R.id.message_detail_content)
    TextView content;
    @BindView(R.id.message_detail_time)
    TextView time;
    @BindView(R.id.message_detail_date)
    TextView date;

    private String mid;


    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        myService = RetrofitHelper.getInstance()
                                  .create(MyService.class);
        ptrWithoutToolbar = true;
        mid = getActivity().getIntent().getStringExtra(BundleKey.BUNDLE_KEY_MESSAGE_MID);
    }

    @Override
    protected void initView(View content) {
        super.initView(content);
        setTitle(getString(R.string.message_detail_title));
        loadData();
    }

    @Override
    protected int contentLayoutID() {
        return R.layout.view_message_detail;
    }


    @Override
    protected void loadData() {
        String uid = String.valueOf(UserManger.getInstance().getLoginUser().getId());
        myService.getMessageDetail(uid, mid).subscribe(new PtrSubscriber<ResponseModel<MessageItemMode>>(this) {
            @Override
            public void onNext(ResponseModel<MessageItemMode> listResponseModel) {
                super.onNext(listResponseModel);
                MessageItemMode detail = listResponseModel.getData();
                title.setText(detail.getTitle());
                content.setText(detail.getMessage());
                time.setText(detail.getCreateDate());
                date.setText(detail.getCreateDate());
                rxBus.post(RxKey.RX_MESSAGE_READED, detail.getId());
            }
        });
    }

    @Override
    protected int toolbarFlag() {
        return TOOL_BAR_FLAG_SHOW;
    }

}
