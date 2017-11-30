package com.wy.report.helper.retrofit;

/**

 * @author cantalou
 * @date 2017年11月26日 15:48
 * <p>
 */
public class ReportException extends RuntimeException {

    private String code;

    public ReportException(String detailMessage, String code) {
        super(detailMessage);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
