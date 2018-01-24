package com.wy.report.business.upload.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.wy.report.R;
import com.wy.report.base.constant.ActivityRequestCode;
import com.wy.report.base.constant.BundleKey;
import com.wy.report.base.constant.RxKey;
import com.wy.report.base.fragment.NetworkFragment;
import com.wy.report.base.model.ResponseModel;
import com.wy.report.business.auth.model.User;
import com.wy.report.business.family.model.FamilyMemberModel;
import com.wy.report.business.upload.model.PictureModel;
import com.wy.report.business.upload.model.UnitModel;
import com.wy.report.business.upload.model.UploadModel;
import com.wy.report.business.upload.service.ReportService;
import com.wy.report.helper.dialog.DialogHelper;
import com.wy.report.helper.retrofit.RetrofitHelper;
import com.wy.report.helper.retrofit.subscriber.NetworkSubscriber;
import com.wy.report.helper.retrofit.util.PartUtils;
import com.wy.report.manager.auth.UserManger;
import com.wy.report.manager.router.AuthRouterManager;
import com.wy.report.manager.router.Router;
import com.wy.report.util.PhotoUtil;
import com.wy.report.util.StringUtils;
import com.wy.report.util.SystemIntentUtil;
import com.wy.report.util.ToastUtils;
import com.wy.report.util.ViewUtils;
import com.wy.report.widget.view.recycleview.NestedGridLayoutManager;

import java.io.File;
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
public class ReportUploadFragment extends NetworkFragment {

    private static final String SAVED_PICTURE_LIST = "picture_list";

    private static final int MAX_PICTURE_NUM = 12;

    private static final int MAX_REMARK_NUM = 200;

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

    @BindView(R.id.report_upload_image_num)
    TextView pictureNum;

    private FamilyMemberModel familyMemberModel;

    private UnitModel unitModel;

    private BaseItemDraggableAdapter<PictureModel, BaseViewHolder> adapter;

    private ArrayList<String> savedPictureList;

    private Router router;

    private ReportService reportService;

    private UserManger userManger;

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        if (savedInstanceState != null) {
            savedPictureList = savedInstanceState.getStringArrayList(SAVED_PICTURE_LIST);
        }
        router = AuthRouterManager.getInstance()
                                  .getRouter();
        userManger = UserManger.getInstance();
        reportService = RetrofitHelper.getInstance()
                                      .create(ReportService.class);
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
        updateSelectedPictureInfo();
    }

    private void createAdapter() {
        adapter = new BaseItemDraggableAdapter<PictureModel, BaseViewHolder>(R.layout.vh_select_image, null) {


            /**
             * To bind different types of holder and solve different the bind events
             *
             * @param holder
             * @param position
             * @see #getDefItemViewType(int)
             */
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                PictureModel item = getItem(position);
                if (item.getType() == PictureModel.TYPE_ADD) {
                    holder.itemView.setOnLongClickListener(null);
                }
            }

            @Override
            protected void convert(BaseViewHolder helper, PictureModel item) {
                helper.setAssociatedObject(item);
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
                } else if (adapter.getItemCount() == MAX_PICTURE_NUM + 1) {
                    ToastUtils.showLong(R.string.report_upload_picture_limit);
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
                        PictureModel lastModel = list.get(list.size() - 1);
                        if (lastModel.getType() == PictureModel.TYPE_NORMAL) {
                            list.remove(position);
                            list.add(new PictureModel(PictureModel.TYPE_ADD));
                            adapter.replaceData(list);
                        } else {
                            adapter.remove(position);
                        }
                        updateSelectedPictureInfo();
                        break;
                    }
                }
            }
        });
        adapter.onAttachedToRecyclerView(recyclerView);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemDragAndSwipeCallback(adapter) {
            @Override
            public boolean canDropOver(RecyclerView recyclerView, RecyclerView.ViewHolder current, RecyclerView.ViewHolder target) {
                if (target instanceof BaseViewHolder) {
                    PictureModel model = (PictureModel) ((BaseViewHolder) target).getAssociatedObject();
                    if (model != null && model.getType() == PictureModel.TYPE_ADD) {
                        return false;
                    }
                }
                return super.canDropOver(recyclerView, current, target);
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
        adapter.enableDragItem(itemTouchHelper, 0, true);
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
        Bundle param = new Bundle();
        param.putParcelable(BundleKey.BUNDLE_KEY_MODEL, familyMemberModel);
        router.open(getActivity(), AuthRouterManager.ROUTER_FAMILY_MEMBER_SELECT, param);
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
        Bundle param = new Bundle();
        if (unitModel != null) {
            param.putParcelable(BundleKey.BUNDLE_KEY_MODEL, unitModel);
        }
        router.open(getActivity(), AuthRouterManager.ROUTER_REPORT_HOSPITAL_LIST, param);
    }

    @Subscribe(tags = {@Tag(RxKey.RX_REPORT_UPLOAD_DELETE_PICTURE)})
    public void pictureDelete(Integer index) {
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
                if (TextUtils.isEmpty(picturePath)) {
                    return;
                }
                for (PictureModel pictureModel : adapter.getData()) {
                    if (picturePath.equals(pictureModel.getPath())) {
                        ToastUtils.showLong(R.string.report_upload_picture_duplicate);
                        return;
                    }
                }
                adapter.addData(adapter.getItemCount() - 1, new PictureModel(picturePath));
                updateSelectedPictureInfo();
                break;
            }
        }
    }

    private void updateSelectedPictureInfo() {
        List<PictureModel> models = adapter.getData();
        int size = models.size();
        PictureModel lastModel = models.get(size - 1);
        if (lastModel.getType() != PictureModel.TYPE_NORMAL) {
            size--;
        }
        pictureNum.setText(getString(R.string.report_picture_num, size, MAX_PICTURE_NUM));

        if ((size % 3 == 0 || (size + 1) % 3 == 0) && size > 1) {
            recyclerView.getLayoutManager()
                        .requestLayout();
            Observable.timer(10, TimeUnit.MILLISECONDS)
                      .observeOn(AndroidSchedulers.mainThread())
                      .subscribe(new Action1<Long>() {
                          @Override
                          public void call(Long aLong) {
                              nestedScrollView.fullScroll(View.FOCUS_DOWN);
                          }
                      });
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList(SAVED_PICTURE_LIST, toPicturePath());
    }


    @OnClick(R.id.upload_report)
    public void upload() {

        if (unitModel == null) {
            return;
        }

        if (time == null) {
            return;
        }

        String remarkStr = ViewUtils.getText(remark);
        if (remarkStr.length() > MAX_REMARK_NUM) {
            ToastUtils.showLong(R.string.report_upload_remark_limit);
            return;
        }

        final ArrayList<File> files = new ArrayList<>();
        for (PictureModel pictureModel : adapter.getData()) {
            if (pictureModel.getType() != PictureModel.TYPE_NORMAL) {
                continue;
            }
            files.add(new File(pictureModel.getPath()));
        }
        User user = userManger.getLoginUser();
        reportService.submitReport(user.getId(), "android", unitModel.getId(), ViewUtils.getText(time), remarkStr, PartUtils.convertFile2Part(files))
                     .subscribe(new NetworkSubscriber<ResponseModel<UploadModel>>(this) {
                         @Override
                         public void handleError(Throwable t) {
                             super.handleError(t);
                         }

                         @Override
                         public void handleSuccess(ResponseModel<UploadModel> responseModel) {
                             super.handleSuccess(responseModel);
                             DialogHelper.showReportUploadSuccessDialog(getActivity(), new DialogInterface.OnClickListener() {
                                 @Override
                                 public void onClick(DialogInterface dialog, int which) {
                                     getActivity().finish();
                                 }
                             }, new DialogInterface.OnClickListener() {
                                 @Override
                                 public void onClick(DialogInterface dialog, int which) {
                                     router.open(getActivity(), AuthRouterManager.ROUTER_REPORT_UPLOAD_SUCCESS);
                                 }
                             });
                         }
                     });
    }

    @Override
    public boolean onBackPressed() {
        if (familyMemberModel != null || unitModel != null || StringUtils.isNotBlank(time) || StringUtils.isNotBlank(remark) || adapter.getItemCount() > 1) {
            DialogHelper.showReportUploadConfirmDialog(getActivity(), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    getActivity().finish();
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            return true;
        }
        return super.onBackPressed();
    }

}
