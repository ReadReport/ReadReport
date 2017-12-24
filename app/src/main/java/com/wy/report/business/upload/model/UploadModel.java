package com.wy.report.business.upload.model;

import com.wy.report.base.model.BaseModel;

/*
 *
 * @author cantalou
 * @date 2017-12-22 23:35
 */
public class UploadModel extends BaseModel {

    private int status;

    private String msg;

    private String rid;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }
}

