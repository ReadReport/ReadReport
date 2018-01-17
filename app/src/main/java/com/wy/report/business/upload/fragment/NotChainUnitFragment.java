package com.wy.report.business.upload.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wy.report.R;
import com.wy.report.base.constant.RxKey;
import com.wy.report.base.fragment.PtrFragment;
import com.wy.report.base.model.ResponseModel;
import com.wy.report.business.upload.model.HospitalProvinceModel;
import com.wy.report.business.upload.model.UnitModel;
import com.wy.report.business.upload.service.HospitalService;
import com.wy.report.helper.retrofit.RetrofitHelper;
import com.wy.report.helper.retrofit.subscriber.PtrSubscriber;
import com.wy.report.widget.view.recycleview.SmoothScrollLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/*
 *
 * @author cantalou
 * @date 2017-12-10 23:02
 */
public class NotChainUnitFragment extends PtrFragment {

    @BindView(R.id.recycle_view_left)
    RecyclerView recycleViewLeft;

    @BindView(R.id.recycle_view_right)
    RecyclerView recycleViewRight;

    private BaseQuickAdapter<HospitalProvinceModel, BaseViewHolder> adapterLeft;

    private BaseQuickAdapter<UnitModel, BaseViewHolder> adapterRight;

    private HospitalService hospitalService;

    private int selectedProvince = 0;

    private List<HospitalProvinceModel> provinces;

    private List<UnitModel> allUnits = new ArrayList<>();

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        hospitalService = RetrofitHelper.getInstance()
                                        .create(HospitalService.class);
        ptrWithoutToolbar = false;
    }

    @Override
    protected void initView(View contentView) {
        super.initView(contentView);
        adapterLeft = new BaseQuickAdapter<HospitalProvinceModel, BaseViewHolder>(R.layout.vh_hospital_province) {
            @Override
            protected void convert(BaseViewHolder helper, HospitalProvinceModel item) {
                TextView province = helper.getView(R.id.vh_hospital_province);
                province.setText(item.getProvince());
                Resources res = getResources();
                province.setTextColor(item.isSelected() ? res.getColor(R.color.lan_30acff) : res.getColor(R.color.hei_333333));
            }
        };
        adapterLeft.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                HospitalProvinceModel provinceModel = provinces.get(position);
                rightShowProvince(provinceModel.getProvince());
            }
        });
        recycleViewLeft.setLayoutManager(new SmoothScrollLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recycleViewLeft.setAdapter(adapterLeft);
        recycleViewLeft.setItemAnimator(null);

        adapterRight = new RightAdapter(null);
        adapterRight.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                rxBus.post(RxKey.RX_HOSPITAL_UNIT_SELECT, adapterRight.getItem(position));
                getActivity().finish();
            }
        });
        recycleViewRight.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recycleViewRight.setAdapter(adapterRight);
        recycleViewRight.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                UnitModel model = adapterRight.getItem(manager.findFirstVisibleItemPosition());
                leftShowProvince(model.getProvince());
            }
        });
    }

    @Override
    protected void loadData() {
        hospitalService.getNotChainUnits()
                       .subscribe(new PtrSubscriber<ResponseModel<List<HospitalProvinceModel>>>(this) {
                           @Override
                           public void onNext(ResponseModel<List<HospitalProvinceModel>> listResponseModel) {
                               super.onNext(listResponseModel);

                               provinces = listResponseModel.getData();
                               provinces.get(0)
                                        .setSelected(true);
                               adapterLeft.setNewData(provinces);

                               allUnits.clear();
                               for (HospitalProvinceModel provinceModel : listResponseModel.getData()) {
                                   allUnits.addAll(provinceModel.getUnits());
                               }
                               adapterRight.setNewData(allUnits);
                           }
                       });
    }


    private void leftShowProvince(String province) {

        int pendingPosition = 0;
        List<HospitalProvinceModel> provinces = adapterLeft.getData();
        HospitalProvinceModel provinceModel = null;
        for (int i = 0; i < provinces.size(); i++) {
            provinceModel = provinces.get(i);
            if (province.equals(provinceModel.getProvince())) {
                pendingPosition = i;
                break;
            }
        }

        if (provinceModel == null || selectedProvince == pendingPosition) {
            return;
        }

        provinceModel.setSelected(true);
        adapterLeft.notifyItemChanged(pendingPosition);

        provinceModel = adapterLeft.getItem(selectedProvince);
        provinceModel.setSelected(false);
        adapterLeft.notifyItemChanged(selectedProvince);
        selectedProvince = pendingPosition;

        LinearLayoutManager manager = (LinearLayoutManager) recycleViewLeft.getLayoutManager();
        int showCount = manager.findLastVisibleItemPosition() - manager.findFirstVisibleItemPosition();
        int toPosition = pendingPosition - showCount / 2;
        if (toPosition < 0) {
            toPosition = 0;
        }
        recycleViewLeft.smoothScrollToPosition(toPosition);
    }

    private void rightShowProvince(String province) {
        for (int i = 0; i < allUnits.size(); i++) {
            UnitModel unit = allUnits.get(i);
            if (province.equals(unit.getProvince())) {
                LinearLayoutManager layoutManager = (LinearLayoutManager) recycleViewRight.getLayoutManager();
                layoutManager.scrollToPositionWithOffset(i + 1, 0);
                layoutManager.setStackFromEnd(true);
                break;
            }
        }
    }

    @Override
    protected int contentLayoutID() {
        return R.layout.fragment_hospital_not_chain_unit;
    }

    @Override
    protected int toolbarFlag() {
        return 0;
    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return super.checkCanDoRefresh(frame, content, header) && PtrDefaultHandler.checkContentCanBePulledDown(frame, recycleViewLeft, header)
                && PtrDefaultHandler.checkContentCanBePulledDown(frame, recycleViewRight, header);
    }

    private static class RightAdapter extends BaseMultiItemQuickAdapter<UnitModel, BaseViewHolder> {

        public RightAdapter(List<UnitModel> data) {
            super(data);
            addItemType(UnitModel.TYPE_TITLE, R.layout.vh_hospital_unit);
            addItemType(UnitModel.TYPE_HOSPITAL, R.layout.vh_hospital_unit);
        }

        @Override
        protected void convert(BaseViewHolder helper, UnitModel item) {
            helper.setText(R.id.vh_hospital_title, item.getTitle());
        }
    }

    ;

}
