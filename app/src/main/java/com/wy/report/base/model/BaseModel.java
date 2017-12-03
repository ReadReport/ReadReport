package com.wy.report.base.model;

import android.os.Parcelable;

import com.alibaba.fastjson.JSON;

import java.util.Map;

/**
 * @author cantalou
 * @date 2017年12月01日 14:53
 */
public abstract class BaseModel{

    public Map<String, Object> toFieldMap() {
        String json = JSON.toJSONString(this);
        return (Map<String, Object>) JSON.parseObject(json, Map.class);
    }

    public int describeContents() {
        return 0;
    }

}
