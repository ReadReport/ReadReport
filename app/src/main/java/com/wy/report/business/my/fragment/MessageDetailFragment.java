package com.wy.report.business.my.fragment;

import android.os.Bundle;
import android.view.View;

import com.wy.report.R;
import com.wy.report.base.fragment.PtrFragment;
import com.wy.report.base.model.ResponseModel;
import com.wy.report.business.my.model.MessageItemMode;
import com.wy.report.business.my.service.MyService;
import com.wy.report.helper.retrofit.RetrofitHelper;
import com.wy.report.helper.retrofit.subscriber.PtrSubscriber;
import com.wy.report.manager.auth.UserManger;

/**
 * 消息详情
 *
 * @author cantalou
 * @date 2017-11-26 23:04
 */
public class MessageDetailFragment extends PtrFragment {

    private MyService  myService;

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        myService = RetrofitHelper.getInstance()
                                  .create(MyService.class);
        ptrWithoutToolbar = true;
    }

    @Override
    protected void initView(View content) {
        super.initView(content);
        setTitle(getString(R.string.message_detail_title));
        loadData();
    }

    @Override
    protected int contentLayoutID() {
        return 0;
    }


    @Override
    protected void loadData() {
        String uid = "";
        if (UserManger.getInstance().isLogin()) {
            uid = String.valueOf(UserManger.getInstance().getLoginUser().getId());
        }
        myService.getMessageDetail(uid,uid).subscribe(new PtrSubscriber<ResponseModel<MessageItemMode>>(this) {
            @Override
            public void onNext(ResponseModel<MessageItemMode> listResponseModel) {
                super.onNext(listResponseModel);
            }
        });
    }

    @Override
    protected int toolbarFlag() {
        return TOOL_BAR_FLAG_SHOW;
    }

}
