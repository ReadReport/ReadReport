package com.wy.report.business.my.fragment;

import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.daimajia.swipe.SwipeLayout;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.wy.report.R;
import com.wy.report.base.constant.BundleKey;
import com.wy.report.base.constant.RxKey;
import com.wy.report.base.fragment.PtrListFragment;
import com.wy.report.base.model.ResponseModel;
import com.wy.report.business.my.model.MessageItemMode;
import com.wy.report.business.my.model.MessageListMode;
import com.wy.report.business.my.service.MyService;
import com.wy.report.helper.retrofit.RetrofitHelper;
import com.wy.report.helper.retrofit.subscriber.NetworkSubscriber;
import com.wy.report.helper.retrofit.subscriber.PtrSubscriber;
import com.wy.report.manager.auth.UserManger;
import com.wy.report.manager.router.AuthRouterManager;
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
    private MessageListMode messageData;

    private String uid;

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
    protected void loadData() {
        uid = String.valueOf(UserManger.getInstance().getLoginUser().getId());
        myService.getMessage(uid).subscribe(new PtrSubscriber<ResponseModel<MessageListMode>>(this) {
            @Override
            public void onNext(ResponseModel<MessageListMode> listResponseModel) {
                super.onNext(listResponseModel);
                messageData = listResponseModel.getData();
                for (MessageItemMode item : messageData.getViewedMessage()) {
                    item.setViewed(true);
                }
                quickAdapter.setNewData(messageData.getAll());
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
                //设置click事件到swipelayout避免冲突
                SwipeLayout layout = helper.getView(R.id.parent);
                layout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle          bundle = new Bundle();
                        bundle.putString(BundleKey.BUNDLE_KEY_MESSAGE_MID, item.getId());
                        AuthRouterManager.getInstance().getRouter().open(getActivity(), AuthRouterManager.ROUTER_MESSAGE_DETAIL, bundle);
                    }
                });
                helper.setText(R.id.message_title, item.getTitle());
                helper.setText(R.id.message_content, item.getMessage());
                helper.setText(R.id.message_date, TimeUtils.millis2String(Long.valueOf(item.getCreateDate()), TimeUtils.DATE_FORMAT));
                helper.setVisible(R.id.message_notify, !item.isViewed());
                helper.setTag(R.id.message_delete, item);
                helper.setOnClickListener(R.id.message_delete, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deleteItem(item);
                    }
                });
            }
        };
    }

    @Subscribe(tags = {@Tag(RxKey.RX_MESSAGE_READED)})
    public void notifyReaded(String mid) {
        for (MessageItemMode itemMode : quickAdapter.getData()) {
            if (itemMode.getId().equals(mid)) {
                itemMode.setViewed(true);
                quickAdapter.notifyDataSetChanged();
                return;
            }
        }
    }

    /**
     * 删除
     * @param item
     */
    private void deleteItem(final MessageItemMode item) {
        myService.delMessage(uid, item.getId()).subscribe(new NetworkSubscriber<ResponseModel>(this) {
            @Override
            public void onNext(ResponseModel responseModel) {
                super.onNext(responseModel);
                quickAdapter.getData().remove(item);
                quickAdapter.notifyDataSetChanged();
            }
        });
    }
}
