package com.wy.report.business.other.fragment;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wy.report.R;
import com.wy.report.base.constant.BundleKey;
import com.wy.report.base.constant.RxKey;
import com.wy.report.base.fragment.ToolbarFragment;
import com.wy.report.business.upload.model.PictureModel;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import uk.co.senab.photoview.PhotoView;

/*
 *
 * @author cantalou
 * @date 2017-12-09 13:56
 */
public class PictureChoosePreviewFragment extends ToolbarFragment {

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @BindView(R.id.toolbar_choose_status)
    TextView chooseStatus;

    @BindView(R.id.vh_picture_choose_num)
    TextView chosenNum;

    private List<PictureModel> models;

    private int index;

    private PagerAdapter adapter;

    private int position;

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        Bundle bundle = getArguments();
        models = bundle.getParcelableArrayList(BundleKey.BUNDLE_KEY_PICTURE_PATH_LIST);
        index = bundle.getInt(BundleKey.BUNDLE_KEY_PICTURE_PATH_LIST_INDEX);
    }

    @Override
    protected void initView(View contentView) {
        super.initView(contentView);
        adapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return models.size();
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
                PictureModel model = models.get(position);
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
        viewPager.setCurrentItem(index);
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
        PictureModel model = models.get(position);
        model.setChoose(!model.isChoose());
        updateToolbarChooseStatus();
        updateChosenNum();
    }

    private void updateToolbarChooseStatus() {
        PictureModel model = models.get(position);
        chooseStatus.setSelected(model.isChoose());
    }

    private void updateChosenNum() {
        int i = 0;
        for (PictureModel model : models) {
            if (model.isChoose()) {
                i++;
            }
        }
        chosenNum.setText(Integer.toString(i));
    }

    @OnClick(R.id.vh_picture_choose_done)
    public void done() {
        getActivity().finish();
    }

    @Override
    public void onPause() {
        rxBus.post(RxKey.RX_PICTURE_CHOOSE_PREVIEW_LIST, models);
        super.onPause();
    }
}
