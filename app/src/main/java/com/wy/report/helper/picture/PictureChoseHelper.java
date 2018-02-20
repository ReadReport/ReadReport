package com.wy.report.helper.picture;

import com.wy.report.business.upload.model.PictureModel;

import java.util.ArrayList;

/*
 *
 * @author cantalou
 * @date 2018-02-20 17:33
 */
public class PictureChoseHelper {


    public static ArrayList<PictureModel> chosenPictures = new ArrayList<>();

    public static int size() {
        return chosenPictures.size();
    }

    public static void add(PictureModel model) {
        if (chosenPictures.contains(model) || model.getType() != PictureModel.TYPE_NORMAL) {
            return;
        }
        chosenPictures.add(model);
    }

    public static void remove(PictureModel model) {
        chosenPictures.remove(model);
    }

    public static void set(ArrayList<PictureModel> pictureModels) {
        chosenPictures.clear();
        if (pictureModels != null) {
            chosenPictures.addAll(pictureModels);
        }
    }
}
