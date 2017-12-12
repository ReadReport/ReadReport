package com.wy.report.business.upload.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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

import butterknife.BindView;
import butterknife.OnClick;

import static com.wy.report.base.constant.ActivityRequestCode.CODE_OPEN_ALBUM;

/*
 *
 * @author cantalou
 * @date 2017-12-05 21:20
 */
public class ReportUploadFragment extends ToolbarFragment {

    private static final String SAVED_PICTURE_LIST = "picture_list";

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
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        createAdapter();
        recyclerView.setAdapter(adapter);
        restorePictureModel();
    }

    private void createAdapter() {
        adapter = new BaseQuickAdapter<PictureModel, BaseViewHolder>(R.layout.vh_select_image) {
            @Override
            protected void convert(BaseViewHolder helper, PictureModel item) {
                switch (item.getType()) {
                    case PictureModel.TYPE_ADD: {
                        helper.setImageResource(R.id.vh_select_image_image, R.drawable.upload_plus)
                              .setVisible(R.id.vh_select_image_delete, false);
                        break;
                    }
                    case PictureModel.TYPE_NORMAL: {
                        helper.setVisible(R.id.vh_select_image_delete, true);
                        Glide.with(getActivity())
                             .load(item.getPath())
                             .into((ImageView) helper.getView(R.id.vh_select_image_image));
                        break;
                    }
                }
                helper.addOnClickListener(R.id.vh_select_image_delete)
                      .addOnClickListener(R.id.vh_select_image_image);
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
                    getActivity().startActivity(SystemIntentUtil.createJumpIntoSystemAlbumIntent());
                }
            }
        });
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.vh_select_image_delete: {
                        adapter.getData()
                               .remove(position);
                        adapter.notifyItemRemoved(position);
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
            picturePaths.add(model.getPath());
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

    @OnClick({R.id.report_upload_medical_examiner_name, R.id.report_upload_medical_examiner_more})
    public void nameClick() {
        router.open(getActivity(), AuthRouterManager.ROUTER_FAMILY_MEMBER_SELECT);
    }

    @OnClick({R.id.report_upload_examine_time_name, R.id.report_upload_examine_time_more})
    public void timeClick() {
        Calendar cal = Calendar.getInstance();
        new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                time.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
            }
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show();
    }

    @OnClick({R.id.report_upload_examine_hospital_name, R.id.report_upload_examine_hospital_more})
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
        if (requestCode != Activity.RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case CODE_OPEN_ALBUM: {
                String picturePath = PhotoUtil.onActivityResult(getActivity(), requestCode, resultCode, data);
                adapter.addData(new PictureModel(picturePath));
                break;
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList(SAVED_PICTURE_LIST, toPicturePath());
    }
}
