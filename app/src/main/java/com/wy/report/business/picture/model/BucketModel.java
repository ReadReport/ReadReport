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

    private int chosenNum;

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

    public void addPath(String path) {
        pictures.add(new PictureModel(path));
    }

    public int getChosenNum() {
        return chosenNum;
    }

    public String getFirst() {
        return pictures.get(0)
                       .getPath();
    }

    public void countChosenNum(ArrayList<PictureModel> chosenPaths) {
        chosenNum = 0;
        for (PictureModel model : pictures) {
            if (chosenPaths.contains(model)) {
                chosenNum++;
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
