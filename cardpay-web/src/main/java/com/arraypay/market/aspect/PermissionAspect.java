package com.arraypay.market.aspect;

import com.arraypay.market.annotation.Permission;
import com.arraypay.market.exception.CommonException;
import com.arraypay.market.rest.StatusCode;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class PermissionAspect {
    //切面点
    @Pointcut("execution(public * com.arraypay.market.controller..*.*(..))")
    public void per(){}

    @Before(value = "per()&&@annotation(annotation)")
    public void doBefore(JoinPoint joinPoint, Permission annotation) throws Throwable {
        Object[] args = joinPoint.getArgs();
        if(args == null || args.length < 1){
            throw new CommonException(StatusCode.INVALID_PARAM.getCode(), StatusCode.INVALID_PARAM.getMessage());
        }

        //Controller中所有方法的参数，最后一个参数必须是Request
        HttpServletRequest request = (HttpServletRequest) args[args.length-1];

        String token = request.getParameter("token");
        String userId = request.getParameter("userId");
        String timestamp = request.getParameter("timestamp");

        if(StringUtils.isEmpty(token)){
            throw new CommonException(StatusCode.PERMISSION_ERROR.getCode(), StatusCode.PERMISSION_ERROR.getMessage());
        }

        if(StringUtils.isEmpty(userId)){
            throw new CommonException(StatusCode.PERMISSION_ERROR.getCode(), StatusCode.PERMISSION_ERROR.getMessage());
        }

        if(StringUtils.isEmpty(timestamp)){
            throw new CommonException(StatusCode.PERMISSION_ERROR.getCode(), StatusCode.PERMISSION_ERROR.getMessage());
        }
    }
}
