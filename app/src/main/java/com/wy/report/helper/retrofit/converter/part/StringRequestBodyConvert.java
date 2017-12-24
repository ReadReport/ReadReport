package com.wy.report.helper.retrofit.converter.part;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

/*
 *
 * @author cantalou
 * @date 2017-12-23 17:08
 */
public class StringRequestBodyConvert implements Converter<Object, RequestBody> {

    @Override
    public RequestBody convert(Object value) throws IOException {
        return RequestBody.create(MediaType.parse("plain/text"), value.toString());
    }
}
