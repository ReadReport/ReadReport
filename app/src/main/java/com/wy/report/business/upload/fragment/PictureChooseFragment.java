package com.wy.report.business.upload.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cantalou.android.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wy.report.R;
import com.wy.report.base.constant.BundleKey;
import com.wy.report.base.fragment.BaseFragment;

import java.util.ArrayList;

import butterknife.BindView;

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

    private int maxChooseNum;

    private ArrayList<String> choosedPicture = new ArrayList<>();

    private ArrayList<String> allPictures = new ArrayList<>();

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        maxChooseNum = getArguments().getInt(BundleKey.BUNDLE_KEY_MAX_CHOOSE_PICTURE_NUM, 12);
        allPictures = loadPicture();
        allPictures.add(0, CAMERA_PATH);
    }

    @Override
    protected void initView(View contentView) {
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.setAdapter(new BaseQuickAdapter<String, BaseViewHolder>(R.layout.vh_choose_picture) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                ImageView picture = helper.getView(R.id.vh_choose_picture);
                Glide.with(getActivity())
                     .load(item)
                     .into(picture);
            }
        });
    }

    @Override
    protected int contentLayoutID() {
        return R.layout.fragment_choose_picture;
    }


    public ArrayList<String> loadPicture() {

        //全部相册
        ArrayList<String> pictureList = new ArrayList<String>();

        String selection = null;
        String[] params = null;

        String order = DATE_TAKEN + " DESC LIMIT ";

        Cursor mCursor = null;
        try {
            mCursor = getActivity().getContentResolver()
                                   .query(EXTERNAL_CONTENT_URI, new String[]{DATA}, selection, params, order);
            if (mCursor == null) {
                return pictureList;
            }

            while (mCursor.moveToNext()) {
                String path = mCursor.getString(0);
                if (StringUtils.isBlank(path)) {
                    continue;
                }
                pictureList.add(path);
            }
        } finally {
            if (mCursor != null) {
                mCursor.close();
            }
        }
        return pictureList;
    }
}
