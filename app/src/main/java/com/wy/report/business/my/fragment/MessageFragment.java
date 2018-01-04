package com.wy.report.business.my.fragment;

import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wy.report.R;
import com.wy.report.base.fragment.PtrListFragment;
import com.wy.report.base.model.ResponseModel;
import com.wy.report.business.my.model.MessageItemMode;
import com.wy.report.business.my.model.MessageListMode;
import com.wy.report.business.my.service.MyService;
import com.wy.report.helper.retrofit.RetrofitHelper;
import com.wy.report.helper.retrofit.subscriber.PtrSubscriber;
import com.wy.report.manager.auth.UserManger;
import com.wy.report.util.DensityUtils;
import com.wy.report.util.TimeUtils;

/**
 * 消息
 *
 * @author cantalou
 * @date 2017-11-26 23:04
 */
public class MessageFragment extends PtrListFragment<MessageItemMode, BaseViewHolder> {


    private MyService       myService;
    private MessageListMode newData;

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
        setTitle(getString(R.string.message_title));
        int padding = DensityUtils.dip2px(getActivity(), 10);
        recyclerView.setPadding(padding, padding, padding, 0);
        recyclerView.setBackground(getResources().getDrawable(R.drawable.shape_white_corner));

        loadData();
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        //        Drawable toolbarBackground = toolbar.getBackground();
        //        toolbarBackground.setAlpha(0);
    }

    @Override
    protected void loadData() {
        String uid = "";
        if (UserManger.getInstance().isLogin()) {
            uid = String.valueOf(UserManger.getInstance().getLoginUser().getId());
        }
        myService.getMessage(uid).subscribe(new PtrSubscriber<ResponseModel<MessageListMode>>(this) {
            @Override
            public void onNext(ResponseModel<MessageListMode> listResponseModel) {
                super.onNext(listResponseModel);
                newData = listResponseModel.getData();
                for (MessageItemMode item : newData.getViewedMessage()) {
                    item.setViewed(true);
                }
                quickAdapter.setNewData(newData.getAll());
            }
        });
    }

    @Override
    protected int toolbarFlag() {
        return TOOL_BAR_FLAG_SHOW;
    }

    @Override
    protected void initRecycleView() {
        super.initRecycleView();
        quickAdapter.bindToRecyclerView(recyclerView);
        quickAdapter.setEmptyView(R.layout.view_message_empty);
    }

    @Override
    protected BaseQuickAdapter<MessageItemMode, BaseViewHolder> createAdapter() {
        return new BaseQuickAdapter<MessageItemMode, BaseViewHolder>(R.layout.view_message_item) {
            @Override
            protected void convert(BaseViewHolder helper, final MessageItemMode item) {
                helper.setText(R.id.message_title, item.getTitle());
                helper.setText(R.id.message_content, item.getMessage());
                helper.setText(R.id.message_date, TimeUtils.millis2String(Long.valueOf(item.getCreateDate()), TimeUtils.DATE_FORMAT));
                helper.setVisible(R.id.message_notify, !item.isViewed());
                helper.setTag(R.id.message_delete, item);
                helper.setOnClickListener(R.id.message_delete, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        quickAdapter.getData().remove(item);
                        quickAdapter.notifyDataSetChanged();
                    }
                });
            }
        };
    }
}
