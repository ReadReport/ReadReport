package com.wy.report.business.picture.model;

import com.wy.report.base.model.BaseModel;
import com.wy.report.business.upload.model.PictureModel;

import java.util.ArrayList;

/**
 * @author cantalou
 * @date 2018年02月12日 14:32
 * <p>
 */
public class BucketModel extends BaseModel {

    private String name;

    private ArrayList<PictureModel> pictures = new ArrayList<>();

    public BucketModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<PictureModel> getPictures() {
        return pictures;
    }

    public void addPath(PictureModel path) {
        pictures.add(path);
    }

    public int getChosenNum() {
        int chosenNum = 0;
        for (PictureModel model : pictures) {
            if (model.isChoose()) {
                chosenNum++;
            }
        }
        return chosenNum;
    }

    public int getNum() {
        return pictures.size();
    }

    public String getFirst() {
        return pictures.get(0)
                       .getPath();
    }

    public void updateChosenStatus(ArrayList<PictureModel> chosenPaths) {
        for (PictureModel model : pictures) {
            if (chosenPaths.contains(model)) {
                model.setChoose(true);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BucketModel that = (BucketModel) o;

        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
