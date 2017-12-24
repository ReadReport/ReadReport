package com.wy.report.business.family.fragment;

import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.wy.report.R;
import com.wy.report.base.constant.RxKey;
import com.wy.report.base.fragment.PtrListFragment;
import com.wy.report.base.model.ResponseModel;
import com.wy.report.business.auth.model.User;
import com.wy.report.business.family.model.FamilyMemberModel;
import com.wy.report.business.family.service.FamilyMemberService;
import com.wy.report.helper.retrofit.RetrofitHelper;
import com.wy.report.helper.retrofit.subscriber.PtrSubscriber;
import com.wy.report.manager.auth.UserManger;

import java.util.List;


/*
 *
 * @author cantalou
 * @date 2017-12-10 12:02
 */
public class FamilyMemberSelectFragment extends PtrListFragment<FamilyMemberModel, BaseViewHolder> {

    private FamilyMemberService familyMemberService;

    private User user;

    @Override
    protected void initData(Bundle savedInstanceState) {
        familyMemberService = RetrofitHelper.getInstance()
                                            .create(FamilyMemberService.class);
        user = UserManger.getInstance()
                         .getLoginUser();
        loadData();
        ptrWithoutToolbar = true;
    }

    @Override
    protected void loadData() {
        familyMemberService.getFamilyMember(user.getId())
                           .subscribe(new PtrSubscriber<ResponseModel<List<FamilyMemberModel>>>(this) {
                               @Override
                               public void onNext(ResponseModel<List<FamilyMemberModel>> listResponseModel) {
                                   super.onNext(listResponseModel);
                                   quickAdapter.setNewData(listResponseModel.getData());
                               }
                           });
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        setTitle(R.string.family_member_select_title);
    }

    @Override
    protected int contentLayoutID() {
        return R.layout.fragment_family_member_select;
    }

    @Override
    protected BaseQuickAdapter createAdapter() {
        return new BaseQuickAdapter<FamilyMemberModel, BaseViewHolder>(R.layout.vh_family_member_select) {
            @Override
            protected void convert(BaseViewHolder helper, FamilyMemberModel item) {
                helper.setText(R.id.vh_family_member_select_name, item.getName())
                      .setText(R.id.vh_family_member_select_relation, getString(R.string.family_member_select_relation_template, item.getRelationship()))
                      .setVisible(R.id.vh_family_member_select_selected, item.isSelected());
            }
        };
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        rxBus.post(RxKey.RX_FAMILY_MEMBER_SELECT, adapter.getItem(position));
        getActivity().finish();
    }

    @Subscribe(tags = {@Tag(RxKey.RX_FAMILY_MEMBER_ADD)})
    public void addMember(FamilyMemberModel model) {
        quickAdapter.addData(model);
    }
}
