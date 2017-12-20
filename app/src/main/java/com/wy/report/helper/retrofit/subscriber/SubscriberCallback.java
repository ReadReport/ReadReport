package com.wy.report.helper.retrofit.subscriber;

/**
 * @author cantalou
 * @date 2017年12月20日 13:47
 */
public interface SubscriberCallback<T> {

    void handleStart();


    void handleError(Throwable t);


    void handleSuccess(T t);
}
