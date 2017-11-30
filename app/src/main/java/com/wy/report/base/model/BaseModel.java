package com.wy.report.base.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 成功：json对象{state:0,data:json格式的token对象信息}
 * 失败：json对象{errcode:40006,errmsg:'',now:1428889859}
 * json对象{errcode:40007,errmsg:'',now:1428889859}
 * json对象{errcode:40003,errmsg:'',now:1428889859}
 *
 * @author cantalou
 * @date 2017年11月24日 9:26
 */
public class BaseModel<T> {

    private int state;

    @JSONField(name = "errcode")
    private String errCode;

    @JSONField(name = "errmsg")
    private String errMsg;

    private T data;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
