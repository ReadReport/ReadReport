package com.wy.report.business.read.mode;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.wy.report.R;
import com.wy.report.base.fragment.PtrFragment;
import com.wy.report.base.model.ResponseModel;
import com.wy.report.business.auth.model.User;
import com.wy.report.business.read.service.ReadService;
import com.wy.report.databinding.ViewReportItemBinding;
import com.wy.report.helper.retrofit.RetrofitHelper;
import com.wy.report.helper.retrofit.subscriber.PtrSubscriber;
import com.wy.report.manager.auth.UserManger;
import java.util.List;


/**
 * Created by BHM on 17/12/16.
 */
public class ReportManageViewMode {


    private ReadService readService;

    private PtrFragment fragment;

    private User user;


    private ObservableField<String> toolBarPopContent;



    public ReportManageViewMode(PtrFragment context)
    {
        this.fragment = context;

    }

    public ObservableArrayList<ReportItemMode> data;


    public ObservableArrayList<ReportItemMode> getData()
    {
        data = new ObservableArrayList<>();

        ReportItemMode data1 = new ReportItemMode();
        data1.setName(new ObservableField<String>("haha"));
        data1.setHospital(new ObservableField<String>("yiyuan1"));
        data1.setTime(new ObservableField<String>("timesss12321"));

        ReportItemMode data2 = new ReportItemMode();
        data2.setName(new ObservableField<String>("haha2"));
        data2.setHospital(new ObservableField<String>("yiyuan2"));
        data2.setTime(new ObservableField<String>("timesss76547651"));

        data.add(data1);
        data.add(data2);
        data.add(data2);
        data.add(data2);
        data.add(data2);
        data.add(data2);
        data.add(data2);
        data.add(data2);
        data.add(data2);
        data.add(data2);
        data.add(data2);
        data.add(data2);
        data.add(data2);
        data.add(data2);
        data.add(data2);
        data.add(data2);
        data.add(data2);
        data.add(data2);
        data.add(data2);
        data.add(data2);
        data.add(data2);
        return data;
    }

    public void getDataFromNet()
    {
        readService = RetrofitHelper.getInstance()
                .create(ReadService.class);
        readService.getReportList(user.getId()).subscribe(new PtrSubscriber<ResponseModel<List<ReportListMode.ReportItem>>>(fragment)
        {
            @Override
            public void onNext(ResponseModel<List<ReportListMode.ReportItem>> listResponseModel) {
                super.onNext(listResponseModel);
                getData();
            }
        });
    }

    public void initData()
    {
        getData();
        user = UserManger.getInstance().getLoginUser();
    }


    @BindingAdapter("bind:image")
    public static void loadImage(ImageView image, Drawable resId){
        image.setImageDrawable(resId);
    }

    @BindingAdapter("bind:data")
    public static void setData(RecyclerView recyclerView, ObservableArrayList<ReportItemMode> list){
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new TestBindingAdapter(recyclerView.getContext(), list));
    }



    public static class TestBindingAdapter extends RecyclerView.Adapter<ItemHolder> {

        private ObservableArrayList<ReportItemMode> data;

        private Context context;

        public TestBindingAdapter(Context context, ObservableArrayList<ReportItemMode> list) {
            this.context = context;
            this.data = list;
        }

        @Override
        public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ViewReportItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.view_report_item, parent, false);
            return new ItemHolder(binding);
        }

        @Override
        public void onBindViewHolder(ItemHolder holder, int position) {
            holder.getBinding().setItem(data.get(position));
            // 立刻刷新界面
            holder.getBinding().executePendingBindings();
        }

        @Override
        public int getItemCount() {
            return data == null ? 0 : data.size();
        }
    }

    public  static class ItemHolder extends RecyclerView.ViewHolder {

        public ItemHolder(View itemView) {
            super(itemView);
        }

        private ViewReportItemBinding binding;

        public ItemHolder(ViewReportItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public ViewReportItemBinding getBinding() {
            return binding;
        }

        public void setBinding(ViewReportItemBinding binding) {
            this.binding = binding;
        }


    }
}
