package com.wy.report.helper.retrofit.converter.part;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * @author cantalou
 * @date 2017年11月23日 11:00
 */
public class PartConverterFactory extends Converter.Factory {

    private PartConverterFactory() {
    }

    public static PartConverterFactory create() {
        return new PartConverterFactory();
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return new StringRequestBodyConvert();
    }
}