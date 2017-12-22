package com.wy.report.business.upload.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.wy.report.R;
import com.wy.report.base.constant.ActivityRequestCode;
import com.wy.report.base.constant.BundleKey;
import com.wy.report.base.constant.RxKey;
import com.wy.report.base.fragment.ToolbarFragment;
import com.wy.report.business.family.model.FamilyMemberModel;
import com.wy.report.business.upload.model.PictureModel;
import com.wy.report.business.upload.model.UnitModel;
import com.wy.report.manager.router.AuthRouterManager;
import com.wy.report.manager.router.Router;
import com.wy.report.util.PhotoUtil;
import com.wy.report.util.SystemIntentUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

import static com.wy.report.base.constant.ActivityRequestCode.CODE_OPEN_ALBUM;

/*
 *
 * @author cantalou
 * @date 2017-12-05 21:20
 */
public class ReportUploadFragment extends ToolbarFragment {

    private static final String SAVED_PICTURE_LIST = "picture_list";

    private static final int MAX_PICTURE_NUM = 12;

    @BindView(R.id.report_upload_medical_examiner_name)
    TextView name;

    @BindView(R.id.report_upload_examine_time_name)
    TextView time;

    @BindView(R.id.report_upload_examine_hospital_name)
    TextView hospital;

    @BindView(R.id.report_upload_remark_name)
    EditText remark;

    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;

    @BindView(R.id.nested_scroll_view)
    NestedScrollView nestedScrollView;

    private FamilyMemberModel familyMemberModel;

    private UnitModel unitModel;

    private BaseQuickAdapter<PictureModel, BaseViewHolder> adapter;

    private ArrayList<String> savedPictureList;

    private Router router;

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            savedPictureList = savedInstanceState.getStringArrayList(SAVED_PICTURE_LIST);
        }
        router = AuthRouterManager.getInstance()
                                  .getRouter();
    }

    @Override
    protected void initView(View contentView) {
        super.initView(contentView);
        recyclerView.setLayoutManager(new NestedGridLayoutManager(getActivity(), 3));
        createAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        restorePictureModel();
    }

    private void createAdapter() {
        adapter = new BaseQuickAdapter<PictureModel, BaseViewHolder>(R.layout.vh_select_image) {
            @Override
            protected void convert(BaseViewHolder helper, PictureModel item) {
                switch (item.getType()) {
                    case PictureModel.TYPE_ADD: {
                        ImageView iv = helper.getView(R.id.vh_select_image_image);
                        iv.setBackgroundResource(R.drawable.selector_white_bg);
                        iv.setImageResource(R.drawable.upload_plus);
                        iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                        helper.setVisible(R.id.vh_select_image_delete, false);
                        break;
                    }
                    case PictureModel.TYPE_NORMAL: {
                        helper.setVisible(R.id.vh_select_image_delete, true);
                        ImageView iv = helper.getView(R.id.vh_select_image_image);
                        iv.setBackgroundResource(0);
                        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        Glide.with(getActivity())
                             .load(item.getPath())
                             .into(iv);
                        helper.addOnClickListener(R.id.vh_select_image_delete);
                        break;
                    }
                }
            }
        };
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                PictureModel model = (PictureModel) adapter.getItem(position);
                if (model.getType() == PictureModel.TYPE_NORMAL) {
                    Bundle bundleKey = new Bundle();
                    bundleKey.putStringArrayList(BundleKey.BUNDLE_KEY_PICTURE_PATH_LIST, toPicturePath());
                    bundleKey.putInt(BundleKey.BUNDLE_KEY_PICTURE_PATH_LIST_INDEX, position);
                    AuthRouterManager.getInstance()
                                     .getRouter()
                                     .open(getActivity(), AuthRouterManager.ROUTER_OTHER_PICTURE_PREVIEW, bundleKey);
                } else {
                    startActivityForResult(SystemIntentUtil.createJumpIntoSystemAlbumIntent(), ActivityRequestCode.CODE_OPEN_ALBUM);
                }
            }
        });
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.vh_select_image_delete: {
                        List<PictureModel> list = adapter.getData();
                        if (list.size() == MAX_PICTURE_NUM) {
                            list.remove(position);
                            list.add(new PictureModel(PictureModel.TYPE_ADD));
                            adapter.replaceData(list);
                        } else {
                            adapter.remove(position);
                        }
                        break;
                    }
                }
            }
        });
        adapter.onAttachedToRecyclerView(recyclerView);
    }

    private ArrayList<String> toPicturePath() {
        List<PictureModel> models = adapter.getData();
        ArrayList<String> picturePaths = new ArrayList<>(models.size());
        for (PictureModel model : models) {
            if (model.getType() == PictureModel.TYPE_NORMAL) {
                picturePaths.add(model.getPath());
            }
        }
        return picturePaths;
    }

    private void restorePictureModel() {
        if (savedPictureList != null && savedPictureList.size() > 0) {
            ArrayList<PictureModel> models = new ArrayList<>();
            for (String path : savedPictureList) {
                PictureModel model = new PictureModel();
                model.setPath(path);
                model.setType(PictureModel.TYPE_NORMAL);
            }
            adapter.setNewData(models);
        } else {
            adapter.addData(new PictureModel(PictureModel.TYPE_ADD));
        }
    }

    @Override
    protected int contentLayoutID() {
        return R.layout.fragment_report_upload;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        setTitle(R.string.report_upload);
    }

    @OnClick({R.id.report_upload_medical_examiner})
    public void nameClick() {
        router.open(getActivity(), AuthRouterManager.ROUTER_FAMILY_MEMBER_SELECT);
    }

    @OnClick({R.id.report_upload_examine_time})
    public void timeClick() {
        Calendar cal = Calendar.getInstance();
        new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                time.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
            }
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show();
    }

    @OnClick({R.id.report_upload_examine_hospital})
    public void hospitalClick() {
        router.open(getActivity(), AuthRouterManager.ROUTER_REPORT_HOSPITAL_LIST);
    }

    @Subscribe(tags = {@Tag(RxKey.RX_REPORT_UPLOAD_DELETE_PICTURE)})
    public void pictureDelete(int index) {
        adapter.remove(index);
    }

    @Subscribe(tags = {@Tag(RxKey.RX_FAMILY_MEMBER_SELECT)})
    public void familyMemberSelected(FamilyMemberModel model) {
        familyMemberModel = model;
        name.setText(model.getName());
    }

    @Subscribe(tags = {@Tag(RxKey.RX_HOSPITAL_UNIT_SELECT)})
    public void hospitalUnitSelected(UnitModel model) {
        unitModel = model;
        hospital.setText(model.getTitle());
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case CODE_OPEN_ALBUM: {
                String picturePath = PhotoUtil.onActivityResult(getActivity(), requestCode, resultCode, data);
                List<PictureModel> list = adapter.getData();
                adapter.addData(list.size() - 1, new PictureModel(picturePath));
                recyclerView.getLayoutManager()
                            .requestLayout();

                if (list.size() == MAX_PICTURE_NUM + 1) {
                    list.remove(MAX_PICTURE_NUM);
                    adapter.replaceData(list);
                }
                Observable.timer(10, TimeUnit.MILLISECONDS)
                          .observeOn(AndroidSchedulers.mainThread())
                          .subscribe(new Action1<Long>() {
                              @Override
                              public void call(Long aLong) {
                                  nestedScrollView.fullScroll(View.FOCUS_DOWN);
                              }
                          });
                break;
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList(SAVED_PICTURE_LIST, toPicturePath());
    }


    @OnClick(R.id.submit_report)
    public void submit(){

    }

    private class NestedGridLayoutManager extends GridLayoutManager {
        private int[] mMeasuredDimension = new int[2];

        public NestedGridLayoutManager(Context context, int spanCount) {
            super(context, spanCount);
        }

        @Override
        public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
            final int widthMode = View.MeasureSpec.getMode(widthSpec);
            final int heightMode = View.MeasureSpec.getMode(heightSpec);
            final int widthSize = View.MeasureSpec.getSize(widthSpec);
            final int heightSize = View.MeasureSpec.getSize(heightSpec);

            int width = 0;
            int height = 0;
            int count = getItemCount();
            int span = getSpanCount();
            for (int i = 0; i < count; i++) {
                measureScrapChild(recycler, i,
                        View.MeasureSpec.makeMeasureSpec(i, View.MeasureSpec.UNSPECIFIED),
                        View.MeasureSpec.makeMeasureSpec(i, View.MeasureSpec.UNSPECIFIED),
                        mMeasuredDimension);

                if (getOrientation() == HORIZONTAL) {
                    if (i % span == 0) {
                        width = width + mMeasuredDimension[0];
                    }
                    if (i == 0) {
                        height = mMeasuredDimension[1];
                    }
                } else {
                    if (i % span == 0) {
                        height = height + mMeasuredDimension[1];
                    }
                    if (i == 0) {
                        width = mMeasuredDimension[0];
                    }
                }
            }

            switch (widthMode) {
                case View.MeasureSpec.EXACTLY:
                    width = widthSize;
                case View.MeasureSpec.AT_MOST:
                case View.MeasureSpec.UNSPECIFIED:
            }

            switch (heightMode) {
                case View.MeasureSpec.EXACTLY:
                    height = heightSize;
                case View.MeasureSpec.AT_MOST:
                case View.MeasureSpec.UNSPECIFIED:
            }

            setMeasuredDimension(width, height);
        }

        private void measureScrapChild(RecyclerView.Recycler recycler, int position, int widthSpec,
                                       int heightSpec, int[] measuredDimension) {
            if (position < getItemCount()) {
                try {
                    View view = recycler.getViewForPosition(0);
                    if (view != null) {
                        RecyclerView.LayoutParams p = (RecyclerView.LayoutParams) view.getLayoutParams();
                        int childWidthSpec = ViewGroup.getChildMeasureSpec(widthSpec, getPaddingLeft() + getPaddingRight(), p.width);
                        int childHeightSpec = ViewGroup.getChildMeasureSpec(heightSpec, getPaddingTop() + getPaddingBottom(), p.height);
                        view.measure(childWidthSpec, childHeightSpec);
                        measuredDimension[0] = view.getMeasuredWidth() + p.leftMargin + p.rightMargin;
                        measuredDimension[1] = view.getMeasuredHeight() + p.bottomMargin + p.topMargin;
                        recycler.recycleView(view);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
