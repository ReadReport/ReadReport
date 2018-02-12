package com.wy.report.business.picture.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.wy.report.R;
import com.wy.report.base.constant.BundleKey;
import com.wy.report.base.constant.RxKey;
import com.wy.report.base.fragment.BaseFragment;
import com.wy.report.business.picture.model.BucketModel;
import com.wy.report.business.upload.model.PictureModel;
import com.wy.report.manager.router.AuthRouterManager;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @author cantalou
 * @date 2018年02月11日 17:13
 * <p>
 */
public class PictureChooseCategoryFragment extends AbstractPictureChooseFragment {

    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;

    private ArrayList<BucketModel> buckets = new ArrayList<>();

    private BaseQuickAdapter<BucketModel, BaseViewHolder> adapter;

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
    }

    @Override
    protected void initView(View contentView) {
        super.initView(contentView);
        adapter = new BaseQuickAdapter<BucketModel, BaseViewHolder>(R.layout.vh_picture_choose_bucket) {
            @Override
            protected void convert(BaseViewHolder helper, BucketModel item) {
                Glide.with(getActivity())
                     .load(item.getFirst())
                     .into((ImageView) helper.getView(R.id.vh_choose_picture_bucket_img));
                helper.setText(R.id.vh_choose_picture_bucket_name, item.getName())
                      .setText(R.id.vh_choose_picture_bucket_num, getString(R.string.picture_category_num, item.getChosenNum()));
            }
        };
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle param = new Bundle();
                BucketModel model = buckets.get(position);
                param.putParcelableArrayList(BundleKey.BUNDLE_KEY_PICTURE_CHOOSE_PICTURE_LIST, model.getPictures());
                router.open(getActivity(), authRouterManager.ROUTER_PICTURE_CHOOSE);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        router.open(getActivity(), AuthRouterManager.ROUTER_PICTURE_CHOOSE);
    }

    @Override
    protected int contentLayoutID() {
        return R.layout.fragment_picture_choose;
    }

    @Subscribe
    public void updateChosenPictures(ArrayList<PictureModel> chosenPictures) {
        for (BucketModel bucket : buckets) {
            bucket.countChosenNum(chosenPictures);
        }
        adapter.notifyDataSetChanged();
    }

    @Subscribe(tags = {@Tag(RxKey.RX_PICTURE_CHOOSE_BUCKET_LIST)})
    public void receiveData(ArrayList<BucketModel> buckets_) {
        buckets.clear();
        buckets.addAll(buckets_);
        adapter.setNewData(buckets);
    }

}
