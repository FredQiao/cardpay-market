package com.arraypay.market.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.arraypay.market.annotation.Permission;
import com.arraypay.market.constant.Constant;
import com.arraypay.market.dto.entity.User;
import com.arraypay.market.dto.form.TokenForm;
import com.arraypay.market.exception.CommonException;
import com.arraypay.market.rest.StatusCode;
import com.arraypay.market.service.UserService;
import com.arraypay.market.util.DateUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Aspect
@Component
public class PermissionAspect {

    @Autowired
    private UserService userService;

    @Value("${spring.token.expire-time}")
    private Integer expireTime;

    //切面点
    @Pointcut("execution(public * com.arraypay.market.controller..*.*(..))")
    public void per(){
        // Do nothing.
    }

    @Before(value = "per()&&@annotation(annotation)")
    public void doBefore(JoinPoint joinPoint, Permission annotation) {
        Object[] args = joinPoint.getArgs();
        JSONObject prams = null;
        for(Object c:args ){
            if(c instanceof TokenForm){
                TokenForm dto = (TokenForm) c;
                prams = JSONObject.parseObject(JSON.toJSONString(dto));
                break;
            }
        }

        if(prams == null || prams.isEmpty()){
            throw new CommonException(StatusCode.INVALID_PARAM.getCode(), StatusCode.INVALID_PARAM.getMessage());
        }

        if(!prams.containsKey(Constant.AuthorizationKey.CUSTOMER_TOKEN)){
            throw new CommonException(StatusCode.PERMISSION_ERROR.getCode(), "缺少token参数");
        }
        String token = prams.getString(Constant.AuthorizationKey.CUSTOMER_TOKEN);
        if(!prams.containsKey(Constant.AuthorizationKey.CUSTOMER_USERID)){
            throw new CommonException(StatusCode.PERMISSION_ERROR.getCode(), "缺少userId参数");
        }
        String userId = prams.getString(Constant.AuthorizationKey.CUSTOMER_USERID);

        User user = userService.getUserById(userId);
        if(user == null){
            throw new CommonException(StatusCode.USER_NOT_EXIST.getCode(), StatusCode.USER_NOT_EXIST.getMessage());
        }

        String myToken = user.getToken();
        Date eTime = user.getExpiredTime();

        if(!token.equals(myToken)){
            throw new CommonException(StatusCode.PERMISSION_ERROR.getCode(), "token不匹配");
        }

        if(DateUtils.getDistanceBetweenTimes(new Date(), eTime) > 0){
            // 当前时间大于过期时间，token已过期
            throw new CommonException(StatusCode.PERMISSION_ERROR.getCode(), "token已过期");
        }

        if(DateUtils.getDistanceBetweenTimes(eTime, new Date()) < 1800){
            // 距离过期时间小于半小时，将过期时间重置
            Date expiredTime = DateUtils.getNewDateByAddSecond(expireTime);
            user.setExpiredTime(expiredTime);
            userService.saveUser(user);
        }
    }
}
