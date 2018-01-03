package com.arraypay.market.rest;

/**
 * Created by fred on 2017/12/5.
 */
public class ResultData<T> extends AbstractResult {
    private T data;

    @SuppressWarnings(value = "unchecked")
    public static <T> ResultData<T> ok() {
        return new ResultData(StatusCode.SUCCESS.getCode());
    }

    @SuppressWarnings(value = "unchecked")
    public static <T> ResultData<T> error() {
        return new ResultData(StatusCode.SYS_ERROR.getCode());
    }

    @SuppressWarnings(value = "unchecked")
    public static <T> ResultData<T> error(String code) {
        return new ResultData(code);
    }

    public ResultData(String code) {
        super(code);
    }

    public static <T> ResultData<T> one(T obj) {
        ResultData<T> res = new ResultData(StatusCode.SUCCESS.getCode());
        res.data = obj;
        return res;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
