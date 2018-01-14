package com.wy.report.business.my.fragment;

import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.wy.report.R;
import com.wy.report.base.constant.BundleKey;
import com.wy.report.base.constant.RxKey;
import com.wy.report.base.fragment.PtrListFragment;
import com.wy.report.base.model.ResponseModel;
import com.wy.report.business.my.model.FamilyItemMode;
import com.wy.report.business.my.service.MyService;
import com.wy.report.helper.retrofit.RetrofitHelper;
import com.wy.report.helper.retrofit.subscriber.PtrSubscriber;
import com.wy.report.manager.auth.UserManger;
import com.wy.report.manager.router.AuthRouterManager;
import com.wy.report.util.DensityUtils;

import java.util.List;

/**
 * 家庭成员
 *
 * @author cantalou
 * @date 2017-11-26 23:04
 */
public class FamilyFragment extends PtrListFragment<FamilyItemMode, BaseViewHolder> {


    private MyService myService;

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
        setTitle(getString(R.string.family_title));
        int padding = DensityUtils.dip2px(getActivity(), 10);
        recyclerView.setPadding(padding, padding, padding, 0);
        recyclerView.setBackground(getResources().getDrawable(R.drawable.shape_white_corner));

        ptrFrameLayout.autoRefresh();
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        toolbarMenu.setText(getString(R.string.family_add));
        toolbarMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthRouterManager.getInstance().getRouter().open(getActivity(), AuthRouterManager.ROUTER_EDIT_FAMILY);
            }
        });
    }

    @Override
    protected void loadData() {
        uid = String.valueOf(UserManger.getInstance().getLoginUser().getId());
        myService.getFamily(uid).subscribe(new PtrSubscriber<ResponseModel<List<FamilyItemMode>>>(this) {

            @Override
            public void onNext(ResponseModel<List<FamilyItemMode>> listResponseModel) {
                super.onNext(listResponseModel);
                quickAdapter.setNewData(listResponseModel.getData());

            }
        });
    }

    @Override
    protected int toolbarFlag() {
        return TOOL_BAR_FLAG_SHOW;
    }

    @Override
    protected BaseQuickAdapter<FamilyItemMode, BaseViewHolder> createAdapter() {
        return new BaseQuickAdapter<FamilyItemMode, BaseViewHolder>(R.layout.view_family_item) {
            @Override
            protected void convert(BaseViewHolder helper, final FamilyItemMode item) {
                helper.setText(R.id.family_name, item.getName());
                helper.setText(R.id.family_relation, item.getRelationship());
            }
        };
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        super.onItemClick(adapter, view, position);
        Bundle         bundle         = new Bundle();
        FamilyItemMode familyItemMode = (FamilyItemMode) adapter.getData().get(position);
        bundle.putParcelable(BundleKey.BUNDLE_KEY_FAMILY_ITEM, familyItemMode);
        AuthRouterManager.getInstance().getRouter().open(getActivity(), AuthRouterManager.ROUTER_EDIT_FAMILY, bundle);
    }

    @Subscribe(tags = {@Tag(RxKey.RX_FAMILY_EDIT)})
    public void notifyMemberModify(FamilyItemMode newMode) {
        for (int i = 0; i < quickAdapter.getData().size(); i++) {
            if (newMode.getId().equals(quickAdapter.getData().get(i).getId())) {
                quickAdapter.getData().set(i, newMode);
                break;
            }
        }
        quickAdapter.notifyDataSetChanged();
    }

    @Subscribe(tags = {@Tag(RxKey.RX_FAMILY_EDIT_ADD)})
    public void notifyMemberAdd(FamilyItemMode mode) {
        quickAdapter.addData(mode);
        quickAdapter.notifyDataSetChanged();
    }
}
