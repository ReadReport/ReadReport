package com.wy.report.business.my.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
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
import com.wy.report.manager.massage.MessageManager;
import com.wy.report.manager.router.AuthRouterManager;
import com.wy.report.util.DensityUtils;
import com.wy.report.util.ToastUtils;

import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 消息
 *
 * @author cantalou
 * @date 2017-11-26 23:04
 */
public class MessageFragment extends PtrListFragment<MessageItemMode.MessageMode, BaseViewHolder> {


    private MyService       myService;
    private MessageListMode messageData;

    private String uid;

    private int pageConut;
    private int page;

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        myService = RetrofitHelper.getInstance()
                                  .create(MyService.class);
        ptrWithoutToolbar = true;
    }

    @Override
    protected int contentLayoutID() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initView(View content) {
        super.initView(content);
        ptrFrameLayout.setMode(PtrFrameLayout.Mode.BOTH);
        setTitle(getString(R.string.message_title));
        loadData();
    }


    @Override
    protected void loadData() {
        page = 0;
        uid = String.valueOf(UserManger.getInstance().getLoginUser().getId());
        myService.getMessage(uid, page).subscribe(new PtrSubscriber<ResponseModel<MessageListMode>>(this) {
            @Override
            public void onNext(ResponseModel<MessageListMode> listResponseModel) {
                super.onNext(listResponseModel);
                messageData = listResponseModel.getData();
                if (messageData != null) {
                    quickAdapter.setNewData(messageData.getAll());
                    pageConut = Math.round(listResponseModel.getData().getCount() / 10);
                }
                MessageManager.getInstance().notifyAllMessageRead();
            }
        });
    }

    @Override
    protected void loadNext() {
        page++;
        if (page > pageConut) {
            page = pageConut;
            ToastUtils.showLong(getString(R.string.report_not_more_data));
            ptrFrameLayout.refreshComplete();
            quickAdapter.notifyDataSetChanged();
            return;
        }
        myService.getMessage(uid, page).subscribe(new PtrSubscriber<ResponseModel<MessageListMode>>(this) {
            @Override
            public void onNext(ResponseModel<MessageListMode> listResponseModel) {
                super.onNext(listResponseModel);
                messageData = listResponseModel.getData();
                quickAdapter.addData(messageData.getAll());
                quickAdapter.notifyDataSetChanged();
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

    }

    @Override
    public void handlePtrSuccess(Object o) {
        super.handlePtrSuccess(o);
        quickAdapter.bindToRecyclerView(recyclerView);
        quickAdapter.setEmptyView(R.layout.view_message_empty);
    }

    @Override
    protected BaseQuickAdapter<MessageItemMode.MessageMode, BaseViewHolder> createAdapter() {
        return new BaseQuickAdapter<MessageItemMode.MessageMode, BaseViewHolder>(R.layout.view_message_item) {
            @Override
            protected void convert(BaseViewHolder helper, final MessageItemMode.MessageMode item) {

                int                       position = getData().indexOf(item);
                RecyclerView.LayoutParams params   = (RecyclerView.LayoutParams) helper.getConvertView().getLayoutParams();
                if (position == 0) {
                    helper.getConvertView().setBackgroundResource(R.drawable.shape_white_corner_top);
                    params.setMargins(0, DensityUtils.dip2px(getActivity(), 10), 0, 0);
                } else if (position == this.getData().size() - 1) {
                    params.setMargins(0, 0, 0, DensityUtils.dip2px(getActivity(), 10));
                    helper.getConvertView().setBackgroundResource(R.drawable.shape_white_corner_bottom);
                } else {
                    helper.getConvertView().setBackgroundResource(R.drawable.shape_white);
                    params.setMargins(0, 0, 0, 0);
                }
                //设置click事件到swipelayout避免冲突
                SwipeLayout layout = helper.getView(R.id.parent);
                layout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putString(BundleKey.BUNDLE_KEY_MESSAGE_MID, item.getId());
                        AuthRouterManager.getInstance().getRouter().open(getActivity(), AuthRouterManager.ROUTER_MESSAGE_DETAIL, bundle);
                    }
                });
                helper.setText(R.id.message_title, item.getTitle());
                helper.setText(R.id.message_content, item.getMessage());
                helper.setText(R.id.message_date, item.getCreateDate());
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
        for (MessageItemMode.MessageMode itemMode : quickAdapter.getData()) {
            if (itemMode.getId().equals(mid)) {
                itemMode.setViewed(true);
                quickAdapter.notifyDataSetChanged();
                return;
            }
        }
    }

    /**
     * 删除
     *
     * @param item
     */
    private void deleteItem(final MessageItemMode.MessageMode item) {
        myService.delMessage(uid, item.getId()).subscribe(new NetworkSubscriber<ResponseModel>(this) {
            @Override
            public void onNext(ResponseModel responseModel) {
                super.onNext(responseModel);
                quickAdapter.getData().remove(item);
                quickAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler2.checkContentCanBePulledDown(frame, recyclerView, header);
    }

    @Override
    public boolean checkCanDoLoadMore(PtrFrameLayout frame, View view, View view1) {
        return PtrDefaultHandler2.checkContentCanBePulledUp(frame, recyclerView, view1);
    }
}
