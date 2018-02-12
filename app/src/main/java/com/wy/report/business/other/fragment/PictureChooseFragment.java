package com.wy.report.business.other.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cantalou.android.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.wy.report.R;
import com.wy.report.base.constant.BundleKey;
import com.wy.report.base.constant.RxKey;
import com.wy.report.base.fragment.BaseFragment;
import com.wy.report.business.upload.model.PictureModel;
import com.wy.report.manager.router.AuthRouterManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

import static android.provider.MediaStore.Images.ImageColumns.DATE_TAKEN;
import static android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
import static android.provider.MediaStore.MediaColumns.DATA;

/**
 * @author cantalou
 * @date 2018年02月11日 17:13
 * <p>
 */
public class PictureChooseFragment extends BaseFragment {

    private static final String CAMERA_PATH = "camera";

    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;

    @BindView(R.id.vh_picture_choose_num)
    TextView chosenNum;

    private ArrayList<PictureModel> allPictures = new ArrayList<>();

    private BaseQuickAdapter<PictureModel, BaseViewHolder> adapter;

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        loadPicture();
    }

    @Override
    protected void initView(View contentView) {
        adapter = new BaseQuickAdapter<PictureModel, BaseViewHolder>(R.layout.vh_choose_picture) {
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
                pictureModel.setChoose(!pictureModel.isChoose());
                adapter.notifyItemChanged(position);
                updateChosenNum();
            }
        });
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        recyclerView.setAdapter(adapter);
        adapter.setNewData(allPictures);
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


    public void loadPicture() {
        String selection = null;
        String[] params = null;
        String order = DATE_TAKEN + " DESC LIMIT ";
        Cursor mCursor = null;
        try {
            mCursor = getActivity().getContentResolver()
                                   .query(EXTERNAL_CONTENT_URI, new String[]{DATA}, selection, params, order);

            while (mCursor.moveToNext()) {
                String path = mCursor.getString(0);
                if (StringUtils.isBlank(path)) {
                    continue;
                }
                allPictures.add(new PictureModel(path));
            }
        } finally {
            if (mCursor != null) {
                mCursor.close();
            }
        }
        //allPictures.add(0, CAMERA_PATH);
    }

    @OnClick(R.id.vh_picture_choose_preview)
    public void preview() {
        openPicturePreview(0);
    }

    @OnClick(R.id.vh_picture_choose_done)
    public void done() {
        rxBus.post(RxKey.RX_PICTURE_CHOOSE_LIST, allPictures);
        getActivity().finish();
    }

    @Subscribe(tags = {@Tag(RxKey.RX_PICTURE_CHOOSE_PREVIEW_LIST)})
    public void previewChoose(ArrayList<PictureModel> list) {
        allPictures.clear();
        allPictures.addAll(list);
        adapter.notifyDataSetChanged();
        updateChosenNum();
    }

    private void updateChosenNum() {
        int i = 0;
        for (PictureModel model : allPictures) {
            if (model.isChoose()) {
                i++;
            }
        }
        chosenNum.setText(Integer.toString(i));
    }
}
