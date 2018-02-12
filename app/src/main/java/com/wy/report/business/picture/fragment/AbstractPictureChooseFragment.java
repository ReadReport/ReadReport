package com.wy.report.business.picture.fragment;

import android.widget.TextView;

import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.wy.report.R;
import com.wy.report.base.constant.RxKey;
import com.wy.report.base.fragment.ToolbarFragment;
import com.wy.report.business.upload.model.PictureModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author cantalou
 * @date 2018年02月12日 16:03
 * <p>
 */
public abstract class AbstractPictureChooseFragment extends ToolbarFragment {

    @BindView(R.id.vh_picture_choose_num)
    TextView chosenNum;

    protected ArrayList<PictureModel> allPictures = new ArrayList<>();

    protected void updateChosenNum() {
        int i = countChosenNum();
        chosenNum.setText(Integer.toString(i));
    }

    protected int countChosenNum() {
        int i = 0;
        for (PictureModel model : allPictures) {
            if (model.isChoose()) {
                i++;
            }
        }
        return i;
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

    @OnClick(R.id.vh_picture_choose_done)
    public void done() {
        rxBus.post(RxKey.RX_PICTURE_CHOOSE_LIST, getChosenList());
        rxBus.post(RxKey.RX_PICTURE_CHOOSE_BUCKET_FINISH, "");
    }

    @Subscribe(tags = {@Tag(RxKey.RX_PICTURE_CHOOSE_BUCKET_FINISH)})
    public void finish(String s) {
        getActivity().finish();
    }

    @Subscribe(tags = {@Tag(RxKey.RX_PICTURE_CHOOSE_CHANGE)})
    public void choose(PictureModel model) {
        for (PictureModel pictureModel : allPictures) {
            if (pictureModel.equals(model)) {
                pictureModel.setChoose(!model.isChoose());
                updateChosenNum();
                break;
            }
        }
    }

}
