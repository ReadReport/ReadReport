package com.wy.report.business.picture.fragment;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wy.report.R;
import com.wy.report.base.constant.BundleKey;
import com.wy.report.base.constant.RxKey;
import com.wy.report.business.upload.model.PictureModel;
import com.wy.report.helper.picture.PictureChoseHelper;
import com.wy.report.util.ToastUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import uk.co.senab.photoview.PhotoView;

import static com.wy.report.base.constant.Constants.PICTURE_CHOOSE_MAX_NUM;

/*
 *
 * @author cantalou
 * @date 2017-12-09 13:56
 */
public class PictureChoosePreviewFragment extends AbstractPictureChooseFragment {

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @BindView(R.id.toolbar_choose_status)
    ImageView chooseStatus;

    private PagerAdapter adapter;

    private int position;

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        Bundle bundle = getArguments();
        pictureModels = bundle.getParcelableArrayList(BundleKey.BUNDLE_KEY_PICTURE_PATH_LIST);
        position = bundle.getInt(BundleKey.BUNDLE_KEY_PICTURE_PATH_LIST_INDEX);
    }

    @Override
    protected void initView(View contentView) {
        super.initView(contentView);
        adapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return pictureModels.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView imageView = new PhotoView(getActivity());
                imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
                container.addView(imageView, lp);
                PictureModel model = pictureModels.get(position);
                Glide.with(getActivity())
                     .load(model.getPath())
                     .into(imageView);
                return imageView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }

            @Override
            public int getItemPosition(Object object) {
                return object.hashCode();
            }
        };
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position_) {
                position = position_;
                updateToolbarChooseStatus();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        preview.setVisibility(View.GONE);
        updateToolbarChooseStatus();
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        statusBarBg.setImageResource(R.color.hei_1e1e1e);
    }

    @Override
    protected int contentLayoutID() {
        return R.layout.fragment_picture_choose_preview;
    }

    @Override
    protected int toolbarLayoutID() {
        return R.layout.view_picture_choose_preview_toolbar;
    }

    @OnClick(R.id.toolbar_choose_status)
    public void chooseStatusClick() {
        PictureModel model = pictureModels.get(position);
        if (!model.isChoose() && PictureChoseHelper.size() >= PICTURE_CHOOSE_MAX_NUM) {
            ToastUtils.showShort(getString(R.string.report_upload_picture_limit1, PICTURE_CHOOSE_MAX_NUM));
            return;
        }
        model.setChoose(!model.isChoose());
        rxBus.post(RxKey.RX_PICTURE_CHOOSE_CHANGE, model);
    }

    @Override
    protected void updateChosenInfo(ArrayList<PictureModel> paths) {
        super.updateChosenInfo(paths);
        updateToolbarChooseStatus();
    }

    private void updateToolbarChooseStatus() {
        PictureModel model = pictureModels.get(position);
        chooseStatus.setSelected(model.isChoose());
    }

}
