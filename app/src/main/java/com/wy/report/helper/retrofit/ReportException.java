package com.wy.report.helper.retrofit;

/**
 * Project Name: ReadReport<p>
 * File Name:    ReportException.java<p>
 * ClassName:    ReportException<p>
 * <p>
 * TODO.
 *
 * @author cantalou
 * @date 2017年11月26日 15:48
 * <p>
 */
public class ReportException extends RuntimeException {

    private int code;

    public ReportException(String detailMessage, int code) {
        super(detailMessage);
        this.code = code;
    }
}
