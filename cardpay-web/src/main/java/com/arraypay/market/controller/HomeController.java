package com.arraypay.market.controller;

import com.arraypay.market.dto.entity.User;
import com.arraypay.market.dto.model.TokenModel;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

@RestController
public class HomeController {

    Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private UserService userService;

    @Value("${spring.token.expire-time}")
    private Integer expireTime;

    @GetMapping("/get_token")
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
        String token = UUID.randomUUID().toString().replaceAll("-","");
        Date expiredTime = DateUtils.getNewDateByAddSecond(expireTime);
        user.setToken(token);
        user.setExpiredTime(expiredTime);

        userService.saveUser(user);

        logger.info("---get token done---");
        return ResultData.one(new TokenModel(user.getId(), user.getToken(), user.getExpiredTime()));
    }
}
