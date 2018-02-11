package com.wy.report.business.upload.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.wy.report.base.model.BaseModel;

/*
 *
 * @author cantalou
 * @date 2017-12-08 22:21
 */
public class PictureModel extends BaseModel implements Parcelable{

    public static final int TYPE_NORMAL = 0;

    public static final int TYPE_ADD = 1;
    public static final Creator<PictureModel> CREATOR = new Creator<PictureModel>() {
        @Override
        public PictureModel createFromParcel(Parcel source) {
            return new PictureModel(source);
        }

        @Override
        public PictureModel[] newArray(int size) {
            return new PictureModel[size];
        }
    };
    private int type;
    private String path;
    private boolean choose;

    public PictureModel() {
    }

    public PictureModel(int type) {
        this.type = type;
    }

    public PictureModel(String path) {
        this.path = path;
    }

    protected PictureModel(Parcel in) {
        this.type = in.readInt();
        this.path = in.readString();
        this.choose = in.readByte() != 0;
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

    public boolean isChoose() {
        return choose;
    }

    public void setChoose(boolean choose) {
        this.choose = choose;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PictureModel that = (PictureModel) o;

        return path.equals(that.path);

    }

    @Override
    public int hashCode() {
        return path.hashCode();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.type);
        dest.writeString(this.path);
        dest.writeByte(this.choose ? (byte) 1 : (byte) 0);
    }
}
