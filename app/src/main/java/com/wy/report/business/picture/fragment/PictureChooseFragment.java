package com.wy.report.business.picture.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wy.report.R;
import com.wy.report.base.constant.BundleKey;
import com.wy.report.base.constant.RxKey;
import com.wy.report.business.picture.model.BucketModel;
import com.wy.report.business.upload.model.PictureModel;
import com.wy.report.manager.router.AuthRouterManager;
import com.wy.report.util.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

import static android.provider.MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME;
import static android.provider.MediaStore.Images.ImageColumns.DATE_TAKEN;
import static android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
import static android.provider.MediaStore.MediaColumns.DATA;
import static com.wy.report.base.constant.Constants.PICTURE_CHOOSE_MAX_NUM;

/**
 * @author cantalou
 * @date 2018年02月11日 17:13
 * <p>
 */
public class PictureChooseFragment extends AbstractPictureChooseFragment {

    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;

    private BaseQuickAdapter<PictureModel, BaseViewHolder> adapter;

    private ArrayList<BucketModel> buckets = new ArrayList<>();

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        Bundle arguments = getArguments();
        if (arguments != null) {
            allPictures = arguments.getParcelableArrayList(BundleKey.BUNDLE_KEY_PICTURE_CHOOSE_PICTURE_LIST);
        }
        if (allPictures == null || allPictures.isEmpty()) {
            load();
            rxBus.post(RxKey.RX_PICTURE_CHOOSE_BUCKET_LIST, buckets);
        }
    }

    @Override
    protected void initView(View contentView) {
        super.initView(contentView);
        adapter = new BaseQuickAdapter<PictureModel, BaseViewHolder>(R.layout.vh_picture_choose) {
            @Override
            protected void convert(BaseViewHolder helper, PictureModel item) {
                ImageView picture = helper.getView(R.id.vh_choose_picture);
                Glide.with(getActivity())
                     .load(item.getPath())
                     .into(picture);
                helper.addOnClickListener(R.id.vh_choose_picture_status);
                helper.getView(R.id.vh_choose_picture_status)
                      .setSelected(item.isChoose());
            }
        };
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                openPicturePreview(position);
            }
        });
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                PictureModel pictureModel = allPictures.get(position);
                if (!pictureModel.isChoose() && countChosenNum() >= PICTURE_CHOOSE_MAX_NUM) {
                    ToastUtils.showShort(getString(R.string.report_upload_picture_limit1, PICTURE_CHOOSE_MAX_NUM));
                    return;
                }
                pictureModel.setChoose(!pictureModel.isChoose());
                adapter.notifyItemChanged(position);
                updateChosenNum();
            }
        });
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        recyclerView.setAdapter(adapter);
        adapter.setNewData(allPictures);
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        setTitle(R.string.picture_choose_title);
        toolbar.setBackgroundColor(getColor(R.color.lan_30acff));
        statusBarBg.setBackgroundResource(R.color.lan_30acff);
    }

    @Override
    protected int toolbarLayoutID() {
        return R.layout.view_picture_choose_toolbar;
    }

    private void openPicturePreview(int index) {
        Bundle param = new Bundle();
        param.putParcelableArrayList(BundleKey.BUNDLE_KEY_PICTURE_PATH_LIST, allPictures);
        param.putInt(BundleKey.BUNDLE_KEY_PICTURE_PATH_LIST_INDEX, index);
        router.open(getActivity(), AuthRouterManager.ROUTER_PICTURE_CHOOSE_PREVIEW, param);
    }

    @Override
    protected int contentLayoutID() {
        return R.layout.fragment_picture_choose;
    }

    public void load() {

        String[] projection = new String[]{BUCKET_DISPLAY_NAME, DATA};
        Cursor cursor = null;
        try {
            cursor = getActivity().getContentResolver()
                                  .query(EXTERNAL_CONTENT_URI, projection, null, null, DATE_TAKEN + " DESC");
            if (cursor == null) {
                return;
            }

            BucketModel allBucket = new BucketModel(getString(R.string.picture_all));
            buckets.add(allBucket);

            HashMap<String, BucketModel> bucketMap = new HashMap<>();
            while (cursor.moveToNext()) {
                String path = cursor.getString(1);
                String bucketName = cursor.getString(0);
                BucketModel model = bucketMap.get(bucketName);
                if (model == null) {
                    model = new BucketModel(bucketName);
                    bucketMap.put(bucketName, model);
                    buckets.add(model);
                }
                model.addPath(path);
                allBucket.addPath(path);
            }
            if (allPictures == null) {
                allPictures = new ArrayList<>();
            }
            allPictures.addAll(allBucket.getPictures());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


    @OnClick(R.id.vh_picture_choose_preview)
    public void preview() {
        openPicturePreview(0);
    }

    @OnClick(R.id.toolbar_cancel)
    public void cancelClick(){
        rxBus.post(RxKey.RX_PICTURE_CHOOSE_BUCKET_FINISH, "");
    }
}
