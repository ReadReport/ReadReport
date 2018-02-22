package com.wy.report.business.picture.fragment;

import android.database.Cursor;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cantalou.android.util.DeviceUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wy.report.R;
import com.wy.report.base.constant.BundleKey;
import com.wy.report.base.constant.RxKey;
import com.wy.report.business.picture.model.BucketModel;
import com.wy.report.business.upload.model.PictureModel;
import com.wy.report.helper.picture.PictureChoseHelper;
import com.wy.report.manager.router.AuthRouterManager;
import com.wy.report.util.DensityUtils;
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

    private static int LINE_PICTURE_NUM = 4;
    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;
    private BaseQuickAdapter<PictureModel, BaseViewHolder> adapter;
    private ArrayList<BucketModel> buckets = new ArrayList<>();
    private int pictureWidth;
    private int itemOffset;
    private GridLayoutManager gridLayoutManager;

    private ArrayList<PictureModel> chosenPaths;

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        Bundle arguments = getArguments();
        if (arguments != null) {
            pictureModels = arguments.getParcelableArrayList(BundleKey.BUNDLE_KEY_PICTURE_CHOOSE_PICTURE_LIST);
            chosenPaths = arguments.getParcelableArrayList(BundleKey.BUNDLE_KEY_PICTURE_PATH_LIST);
        }
        PictureChoseHelper.set(chosenPaths);
        if (pictureModels == null || pictureModels.isEmpty()) {
            load();
            rxBus.post(RxKey.RX_PICTURE_CHOOSE_BUCKET_LIST, buckets);
        }

        pictureWidth = (DeviceUtils.getDeviceWidthPixels(getActivity()) - DensityUtils.dip2px(getActivity(), 50)) / LINE_PICTURE_NUM;
        itemOffset = DensityUtils.dip2px(getActivity(), 10);
    }

    @Override
    protected void initView(View contentView) {
        super.initView(contentView);
        adapter = new BaseQuickAdapter<PictureModel, BaseViewHolder>(R.layout.vh_picture_choose) {
            @Override
            protected void convert(BaseViewHolder helper, final PictureModel item) {
                final ImageView picture = helper.getView(R.id.vh_choose_picture);
                ViewGroup.LayoutParams layoutParams = picture.getLayoutParams();
                layoutParams.width = pictureWidth;
                layoutParams.height = pictureWidth;
                Glide.with(getActivity())
                     .load(item.getPath())
                     .into(picture);
                helper.addOnClickListener(R.id.vh_choose_picture_status);
                helper.getView(R.id.vh_choose_picture_status)
                      .setSelected(item.isChoose());
            }
        };
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                openPicturePreview(position ,false);
            }
        });
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                PictureModel pictureModel = pictureModels.get(position);
                if (!pictureModel.isChoose() && PictureChoseHelper.size() >= PICTURE_CHOOSE_MAX_NUM) {
                    ToastUtils.showShort(getString(R.string.report_upload_picture_limit1, PICTURE_CHOOSE_MAX_NUM));
                    return;
                }
                pictureModel.setChoose(!pictureModel.isChoose());
                rxBus.post(RxKey.RX_PICTURE_CHOOSE_CHANGE, pictureModel);
                adapter.notifyItemChanged(position);
            }
        });
        gridLayoutManager = new GridLayoutManager(getActivity(), LINE_PICTURE_NUM);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
                int half = itemOffset / 2;
                outRect.top = itemPosition < LINE_PICTURE_NUM ? itemOffset : half;
                outRect.left = (itemPosition % LINE_PICTURE_NUM == 0) ? itemOffset : half;
                outRect.right = ((itemPosition + 1) % LINE_PICTURE_NUM == 0) ? itemOffset : half;
                outRect.bottom += itemPosition >= adapter.getItemCount() / LINE_PICTURE_NUM * LINE_PICTURE_NUM ? itemOffset : half;
            }
        });
        recyclerView.getItemAnimator()
                    .setSupportsChangeAnimations(false);
        recyclerView.setAdapter(adapter);
        adapter.setNewData(pictureModels);
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        setTitle(R.string.picture_choose_title);
        toolbar.setBackgroundColor(getColor(R.color.lan_30acff));
        statusBarBg.setImageResource(R.color.lan_30acff);
    }

    @Override
    protected int toolbarLayoutID() {
        return R.layout.view_picture_choose_toolbar;
    }

    private void openPicturePreview(int index, boolean onlyChosen) {
        Bundle param = new Bundle();
        if (onlyChosen) {
            ArrayList<PictureModel> paths = new ArrayList<>();
            for (PictureModel model : pictureModels) {
                if(model.isChoose()){
                    paths.add(model);
                }
            }
            param.putParcelableArrayList(BundleKey.BUNDLE_KEY_PICTURE_PATH_LIST, paths);
        } else {
            param.putParcelableArrayList(BundleKey.BUNDLE_KEY_PICTURE_PATH_LIST, pictureModels);
        }
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
                BucketModel newBucket = bucketMap.get(bucketName);
                if (newBucket == null) {
                    newBucket = new BucketModel(bucketName);
                    bucketMap.put(bucketName, newBucket);
                    buckets.add(newBucket);
                }
                PictureModel pictureModel = new PictureModel(path);
                newBucket.addPath(pictureModel);
                allBucket.addPath(pictureModel);
                if(chosenPaths != null && chosenPaths.contains(pictureModel)){
                    pictureModel.setChoose(true);
                }
            }
            if (pictureModels == null) {
                pictureModels = new ArrayList<>();
            }
            pictureModels.addAll(allBucket.getPictures());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


    @OnClick(R.id.fragment_picture_choose_preview)
    public void preview() {
        openPicturePreview(0,true);
    }

    @OnClick(R.id.toolbar_cancel)
    public void cancelClick() {
        rxBus.post(RxKey.RX_PICTURE_CHOOSE_FINISH, "");
    }

    public void choose(PictureModel model) {
        super.choose(model);
        for (int i = 0; i < pictureModels.size(); i++) {
            PictureModel pictureModel = pictureModels.get(i);
            if(model.equals(pictureModel)){
                pictureModel.setChoose(model.isChoose());
                adapter.notifyItemChanged(i);
                break;
            }
        }
    }
}
