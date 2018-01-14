//package com.wy.report.business.read.mode;
//
//import android.databinding.BindingAdapter;
//import android.databinding.ObservableArrayList;
//import android.databinding.ObservableField;
//import android.graphics.drawable.Drawable;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.widget.ImageView;
//
//import com.wy.report.R;
//import com.wy.report.base.adapter.QuickDataBindingAdapter;
//import com.wy.report.base.fragment.PtrFragment;
//import com.wy.report.base.model.ResponseModel;
//import com.wy.report.base.viewholder.BaseDataBindingViewHolder;
//import com.wy.report.business.auth.model.User;
//import com.wy.report.business.read.service.ReadService;
//import com.wy.report.databinding.ViewReportItemBinding;
//import com.wy.report.helper.retrofit.RetrofitHelper;
//import com.wy.report.helper.retrofit.subscriber.PtrSubscriber;
//import com.wy.report.manager.auth.UserManger;
//
//import java.util.List;
//
//
///**
// * Created by BHM on 17/12/16.
// */
//public class ReportManageViewMode {
//
//    public ObservableArrayList<ReportItemMode> data;
//    private ReadService readService;
//    private PtrFragment fragment;
//    private User user;
//
//    public ReportManageViewMode(PtrFragment context) {
//        this.fragment = context;
//    }
//
//    @BindingAdapter({"image"})
//    public static void loadImage(ImageView image, Drawable resId) {
//        image.setImageDrawable(resId);
//    }
//
//    @BindingAdapter({"data"})
//    public static void setData(RecyclerView recyclerView, ObservableArrayList<ReportItemMode> list) {
//        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.VERTICAL, false));
//        QuickAdapter quickAdapter = new QuickAdapter(R.layout.view_report_item);
//        quickAdapter.setNewData(list);
//        recyclerView.setAdapter(quickAdapter);
//        quickAdapter.bindToRecyclerView(recyclerView);
//        quickAdapter.setEmptyView(R.layout.view_report_manage_empty);
//    }
//
//    public ObservableArrayList<ReportItemMode> getData() {
//        if (data == null) {
//            data = new ObservableArrayList<>();
//        }
//        return data;
//    }
//
//    public void getDataFromNet() {
//        readService = RetrofitHelper.getInstance()
//                .create(ReadService.class);
//        readService.getReportList(user.getId()).subscribe(new PtrSubscriber<ResponseModel<List<ReportItemMode>>>(fragment) {
//            @Override
//            public void onNext(ResponseModel<List<ReportItemMode>> listResponseModel) {
//                super.onNext(listResponseModel);
//                data.clear();
//                data.addAll(listResponseModel.getData());
//            }
//        });
//    }
//
//    public void initData() {
//        getData();
//        user = UserManger.getInstance().getLoginUser();
//    }
//
//    public static class QuickAdapter extends QuickDataBindingAdapter<ReportItemMode, ViewReportItemBinding> {
//
//        public QuickAdapter(int layoutResId) {
//            super(layoutResId);
//        }
//
//        @Override
//        protected void convert(BaseDataBindingViewHolder<ViewReportItemBinding> helper, ReportItemMode item) {
//            helper.getBinding().setItem(item);
//            // 立刻刷新界面
//            helper.getBinding().executePendingBindings();
//        }
//    }
//
//}
