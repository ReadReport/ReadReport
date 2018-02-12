package com.wy.report.business.picture.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wy.report.R;
import com.wy.report.base.constant.BundleKey;
import com.wy.report.base.constant.RxKey;
import com.wy.report.base.fragment.ToolbarFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import uk.co.senab.photoview.PhotoView;

/*
 *
 * @author cantalou
 * @date 2017-12-09 13:56
 */
public class PicturePreviewFragment extends ToolbarFragment {

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.toolbar_delete)
    ImageView toolBarDelete;
    private List<String> paths;
    private int index;
    private PagerAdapter adapter;
    private boolean needDelete;

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        Bundle bundle = getArguments();
        paths = bundle.getStringArrayList(BundleKey.BUNDLE_KEY_PICTURE_PATH_LIST);
        index = bundle.getInt(BundleKey.BUNDLE_KEY_PICTURE_PATH_LIST_INDEX);
        needDelete = bundle.getBoolean(BundleKey.BUNDLE_KEY_PICTURE_NEED_DELETE,true);
    }

    @Override
    protected void initView(View contentView) {
        super.initView(contentView);
        adapter = new PagerAdapter() {
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
        statusBarBg.setImageResource(R.color.hei_1e1e1e);
        int visible = needDelete ? View.VISIBLE : View.GONE;
        toolBarDelete.setVisibility(visible);
    }

    @Override
    protected int contentLayoutID() {
        return R.layout.fragment_picture_preview;
    }

    @Override
    protected int toolbarLayoutID() {
        return R.layout.view_picture_toolbar;
    }

    @OnClick(R.id.toolbar_delete)
    public void deleteClick() {
        new AlertDialog.Builder(getActivity()).setTitle("是否删除图片")
                                              .setCancelable(true)
                                              .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                                  @Override
                                                  public void onClick(DialogInterface dialog, int which) {
                                                      dialog.dismiss();
                                                  }
                                              })
                                              .setPositiveButton("删除图片", new DialogInterface.OnClickListener() {
                                                  @Override
                                                  public void onClick(DialogInterface dialog, int which) {
                                                      int currentIndex = viewPager.getCurrentItem();
                                                      rxBus.post(RxKey.RX_REPORT_UPLOAD_DELETE_PICTURE, currentIndex);
                                                      paths.remove(currentIndex);
                                                      if (paths.size() == 0) {
                                                          getActivity().finish();
                                                          return;
                                                      }
                                                      viewPager.setAdapter(adapter);
                                                      viewPager.setCurrentItem(currentIndex);
                                                      setTitle((currentIndex + 1) + "/" + paths.size());
                                                      dialog.dismiss();
                                                  }
                                              })
                                              .create()
                                              .show();
    }


}
