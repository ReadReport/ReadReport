package com.wy.report.business.picture.fragment;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.wy.report.R;
import com.wy.report.base.constant.RxKey;
import com.wy.report.base.fragment.ToolbarFragment;
import com.wy.report.business.upload.model.PictureModel;
import com.wy.report.helper.picture.PictureChoseHelper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author cantalou
 * @date 2018年02月12日 16:03
 * <p>
 */
public abstract class AbstractPictureChooseFragment extends ToolbarFragment {

    protected ArrayList<PictureModel> allPictures = new ArrayList<>();

    protected BaseQuickAdapter adapter;

    @BindView(R.id.fragment_picture_choose_preview)
    protected View preview;

    @BindView(R.id.fragment_picture_choose_num)
    protected TextView chosenNum;

    @BindView(R.id.fragment_picture_choose_done)
    protected View done;

    @Override
    protected void initView(View contentView) {
        super.initView(contentView);
        if (allPictures != null) {
            updateChosenInfo(allPictures);
        }
    }

    protected void updateChosenInfo(ArrayList<PictureModel> paths) {
        int i = PictureChoseHelper.size();
        chosenNum.setVisibility(i > 0 ? View.VISIBLE : View.GONE);
        chosenNum.setText(Integer.toString(i));
        preview.setEnabled(i > 0);
        done.setEnabled(i > 0);
    }

    protected ArrayList<PictureModel> getChosenList() {
        ArrayList<PictureModel> result = new ArrayList<>();
        for (PictureModel model : allPictures) {
            if (model.isChoose()) {
                result.add(model);
            }
        }
        return result;
    }

    @OnClick({R.id.fragment_picture_choose_done, R.id.fragment_picture_choose_num})
    public void done() {
        rxBus.post(RxKey.RX_PICTURE_CHOOSE_LIST, getChosenList());
        rxBus.post(RxKey.RX_PICTURE_CHOOSE_FINISH, "");
    }

    @Subscribe(tags = {@Tag(RxKey.RX_PICTURE_CHOOSE_FINISH)})
    public void finish(String s) {
        getActivity().finish();
    }

    @Subscribe(tags = {@Tag(RxKey.RX_PICTURE_CHOOSE_CHANGE)})
    public void choose(PictureModel model) {
        for (PictureModel pictureModel : allPictures) {
            if (pictureModel.equals(model)) {
                pictureModel.setChoose(model.isChoose());
                break;
            }
        }
        if (model.isChoose()) {
            PictureChoseHelper.add(model);
        } else {
            PictureChoseHelper.remove(model);
        }
        updateChosenInfo(allPictures);
    }

}
