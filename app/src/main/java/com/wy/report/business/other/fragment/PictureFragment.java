package com.wy.report.business.other.fragment;

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
import com.wy.report.base.fragment.ToolbarFragment;

import java.util.List;

import butterknife.BindView;
import uk.co.senab.photoview.PhotoView;

/*
 *
 * @author cantalou
 * @date 2017-12-09 13:56
 */
public class PictureFragment extends ToolbarFragment {

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private List<String> paths;

    private int index;

    @Override
    protected void initData(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        paths = bundle.getStringArrayList(BundleKey.BUNDLE_KEY_PICTURE_PATH_LIST);
        index = bundle.getInt(BundleKey.BUNDLE_KEY_PICTURE_PATH_LIST_INDEX);
    }

    @Override
    protected void initView(View contentView) {
        super.initView(contentView);
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return paths.size();
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
                Glide.with(getActivity())
                     .load(paths.get(position))
                     .into(imageView);
                return imageView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View)object);
            }
        });
        viewPager.setCurrentItem(index);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setTitle((position + 1) + "/" + paths.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        setTitle((index + 1) + "/" + paths.size());
    }

    @Override
    protected int contentLayoutID() {
        return R.layout.fragment_picture;
    }

    @Override
    protected int toolbarLayoutID() {
        return R.layout.view_picture_toolbar;
    }
}
