package com.arraypay.market.controller;

import com.arraypay.market.dto.entity.User;
import com.arraypay.market.dto.model.TokenModel;
import com.arraypay.market.exception.CommonException;
import com.arraypay.market.rest.ResultData;
import com.arraypay.market.rest.StatusCode;
import com.arraypay.market.service.UserService;
import com.arraypay.market.util.DateUtils;
import com.arraypay.market.util.MD5Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

@RestController
public class HomeController {

    Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private UserService userService;

    @Value("${spring.access_token.expire-time}")
    private Integer atExpireTime;

    @Value("${spring.refresh_token.expire-time}")
    private Integer rtExpireTime;

    @PostMapping("/get_token")
    public ResultData getToken(@RequestParam String username, @RequestParam String password){
        logger.info("---get token start---");
        if(username == null || password == null){
            logger.info("---get token error: {}", StatusCode.INVALID_PARAM.getMessage());
            return ResultData.error(StatusCode.INVALID_PARAM.getCode());
        }

        User user = userService.getUserByUsernameAndPwd(username, MD5Utils.encode(password));
        if(user == null){
            logger.info("---get token error: {}", StatusCode.USER_NOT_EXIST.getMessage());
            return ResultData.error(StatusCode.USER_NOT_EXIST.getCode());
        }

        logger.info("---save token---");
        String accessToken = UUID.randomUUID().toString().replaceAll("-","");
        Date atExpiredTime = DateUtils.getNewDateByAddSecond(atExpireTime);
        String refreshToken = UUID.randomUUID().toString().replaceAll("-","");
        Date rtExpiredTime = DateUtils.getNewDateByAddSecond(rtExpireTime);

        user.setAccessToken(accessToken);
        user.setAtExpiredTime(atExpiredTime);
        user.setRefreshToken(refreshToken);
        user.setRtExpiredTime(rtExpiredTime);

        userService.saveUser(user);

        logger.info("---get token done---");
        return ResultData.one(new TokenModel(user.getId(), user.getAccessToken(), user.getRefreshToken(), user.getAtExpiredTime()));
    }

    @PostMapping("/refresh_token")
    public ResultData refreshToken(@RequestParam String userId, @RequestParam String refreshToken){
        logger.info("---refresh token start---");
        if(refreshToken == null || userId == null){
            logger.info("---refresh token error: {}", StatusCode.INVALID_PARAM.getMessage());
            return ResultData.error(StatusCode.INVALID_PARAM.getCode());
        }

        User user = userService.getUserById(userId);
        if(user == null){
            logger.info("---refresh token error: {}", StatusCode.USER_NOT_EXIST.getMessage());
            return ResultData.error(StatusCode.USER_NOT_EXIST.getCode());
        }

        if(!refreshToken.equals(user.getRefreshToken())){
            throw new CommonException(StatusCode.REFRESH_TOKEN_INVALID.getCode(), StatusCode.REFRESH_TOKEN_INVALID.getMessage());
        }

        if(DateUtils.getDistanceBetweenTimes(new Date(), user.getRtExpiredTime()) > 0){
            // 当前时间大于过期时间，token已过期
            throw new CommonException(StatusCode.REFRESH_TOKEN_EXPIRED.getCode(), StatusCode.REFRESH_TOKEN_EXPIRED.getMessage());
        }

        logger.info("---save token---");
        String accessToken = UUID.randomUUID().toString().replaceAll("-","");
        Date atExpiredTime = DateUtils.getNewDateByAddSecond(atExpireTime);
        refreshToken = UUID.randomUUID().toString().replaceAll("-","");
        Date rtExpiredTime = DateUtils.getNewDateByAddSecond(rtExpireTime);

        user.setAccessToken(accessToken);
        user.setAtExpiredTime(atExpiredTime);
        user.setRefreshToken(refreshToken);
        user.setRtExpiredTime(rtExpiredTime);

        userService.saveUser(user);

        logger.info("---refresh token done---");
        return ResultData.one(new TokenModel(user.getId(), user.getAccessToken(), user.getRefreshToken(), user.getAtExpiredTime()));

    }
}
