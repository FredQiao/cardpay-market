package com.arraypay.market.rest;

/**
 * Created by fred on 2017/12/5.
 */
public class AbstractResult {
    String code;

    String message;

    public AbstractResult(String code) {
        this.code = code;
        this.message = StatusCode.getByCode(code).getMessage();
    }

    public AbstractResult(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
