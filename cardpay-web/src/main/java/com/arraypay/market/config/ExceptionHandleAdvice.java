package com.arraypay.market.config;

import com.arraypay.market.exception.CommonException;
import com.arraypay.market.rest.ResultData;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHandleAdvice {

    /**
     * 全局异常捕捉处理
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResultData errorHandler(Exception ex) {
        return ResultData.error();
    }

    /**
     * 拦截捕捉自定义异常 CommonException.class
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = CommonException.class)
    public ResultData myErrorHandler(CommonException ex) {
        return ResultData.error(ex.getCode(), ex.getMessage());
    }
}
