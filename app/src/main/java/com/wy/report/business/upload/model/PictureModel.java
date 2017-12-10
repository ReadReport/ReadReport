package com.wy.report.business.upload.model;

import com.wy.report.base.model.BaseModel;

/*
 *
 * @author cantalou
 * @date 2017-12-08 22:21
 */
public class PictureModel extends BaseModel {

    public static final int TYPE_NORMAL = 0;

    public static final int TYPE_ADD = 1;

    private int type;

    private String path;

    public PictureModel() {
    }

    public PictureModel(String path) {
        this.path = path;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
