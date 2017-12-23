package com.wy.report.helper.retrofit.converter.json;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.wy.report.base.model.ResponseModel;
import com.wy.report.helper.retrofit.ResponseCode;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * @author cantalou
 * @date 2017年11月23日 11:02
 */
final class FastJsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private static final Feature[] EMPTY_SERIALIZER_FEATURES = new Feature[0];

    private Type mType;

    private ParserConfig config;
    private int featureValues;
    private Feature[] features;

    FastJsonResponseBodyConverter(Type type, ParserConfig config, int featureValues,
                                  Feature... features) {
        mType = type;
        this.config = config;
        this.featureValues = featureValues;
        this.features = features;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        try {
            T result = JSON.parseObject(value.string(), mType, config, featureValues, features != null ? features : EMPTY_SERIALIZER_FEATURES);
            ResponseModel model = (ResponseModel) result;
            if (!TextUtils.isEmpty(model.getErrCode())) {
                ResponseCode.convert2Exception(model.getErrCode(), model.getErrMsg());
            }
            return result;
        } finally {
            value.close();
        }
    }
}
