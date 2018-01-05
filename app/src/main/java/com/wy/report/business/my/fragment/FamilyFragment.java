package com.wy.report.business.my.fragment;

import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wy.report.R;
import com.wy.report.base.fragment.PtrListFragment;
import com.wy.report.base.model.ResponseModel;
import com.wy.report.business.my.model.FamilyItemMode;
import com.wy.report.business.my.service.MyService;
import com.wy.report.helper.retrofit.RetrofitHelper;
import com.wy.report.helper.retrofit.subscriber.PtrSubscriber;
import com.wy.report.manager.auth.UserManger;
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

        loadData();
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
    protected void initRecycleView() {
        super.initRecycleView();
        //        quickAdapter.bindToRecyclerView(recyclerView);
        //        quickAdapter.setEmptyView(R.layout.view_message_empty);
    }

    @Override
    protected BaseQuickAdapter<FamilyItemMode, BaseViewHolder> createAdapter() {
        return new BaseQuickAdapter<FamilyItemMode, BaseViewHolder>(R.layout.view_family_item) {
            @Override
            protected void convert(BaseViewHolder helper, final FamilyItemMode item) {
                helper.setText(R.id.family_name,item.getName());
                helper.setText(R.id.family_relation,item.getRelationship());
            }
        };
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        super.onItemClick(adapter, view, position);
    }
}
