package com.arraypay.market.aspect;

import com.arraypay.market.annotation.Permission;
import com.arraypay.market.dto.entity.User;
import com.arraypay.market.exception.CommonException;
import com.arraypay.market.constant.StatusCode;
import com.arraypay.market.service.RedisService;
import com.arraypay.market.service.UserService;
import com.arraypay.market.util.DateUtils;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Aspect
@Component
@Slf4j
public class PermissionAspect {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisService redisService;

    //切面点
    @Pointcut("execution(public * com.arraypay.market.controller..*.*(..))")
    public void per(){
        // Do nothing.
    }
    
    /**
     *返回日志
     *@Author: zhoufu
     *@param:
     *@return:
     */
    @AfterReturning(returning = "ret", pointcut = "per()")
    public void doAfterReturning(Object ret) throws Throwable {
        log.info("返回数据 : " + ret);
    }

    @Before(value = "per()&&@annotation(annotation)")
    public void doBefore(JoinPoint joinPoint, Permission annotation) {
        Object[] args = joinPoint.getArgs();
        if(args == null || args.length < 1){
            throw new CommonException(StatusCode.INVALID_PARAM);
        }

        //Controller中所有方法的参数，最后一个参数必须是Request
        HttpServletRequest request = (HttpServletRequest) args[args.length-1];
        String token = request.getHeader("token");
        String userId = request.getHeader("userId");

        if(StringUtils.isEmpty(token)){
            throw new CommonException(StatusCode.PERMISSION_ERROR.getCode(), "缺少token参数");
        }

        if(StringUtils.isEmpty(userId)){
            throw new CommonException(StatusCode.PERMISSION_ERROR.getCode(), "缺少userId参数");
        }

        User user = userService.getUserById(userId);
        if(user == null){
            throw new CommonException(StatusCode.USER_NOT_EXIST);
        }

        String myToken = user.getAccessToken();  // 从Redis中获取：redisService.get("access_token_" + userId);
        Date eTime = user.getAtExpiredTime();

        if(!token.equals(myToken)){
            throw new CommonException(StatusCode.ACCESS_TOKEN_INVALID);
        }

        if(DateUtils.getDistanceBetweenTimes(new Date(), eTime) > 0){
            // 当前时间大于过期时间，token已过期
            throw new CommonException(StatusCode.ACCESS_TOKEN_EXPIRED);
        }
    }
}
