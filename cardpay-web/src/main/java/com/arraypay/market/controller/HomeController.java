package com.arraypay.market.controller;

import com.arraypay.market.constant.StatusCode;
import com.arraypay.market.constant.SysProperties;
import com.arraypay.market.dto.entity.User;
import com.arraypay.market.dto.model.TokenModel;
import com.arraypay.market.exception.CommonException;
import com.arraypay.market.rest.ResultData;
import com.arraypay.market.service.RedisService;
import com.arraypay.market.service.SmsService;
import com.arraypay.market.service.UserService;
import com.arraypay.market.util.DateUtils;
import com.arraypay.market.util.MD5Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private SmsService smsService;
    @Autowired
    private SysProperties properties;
    @Autowired
    private RedisService redisService;

    @PostMapping("/send_code")
    public ResultData sendVerifyCode(@RequestParam String mobile, @RequestParam("nation_code") String nationCode) throws Exception{
        smsService.sendVerifyCode(mobile, nationCode);
        return ResultData.ok();
    }

    @PostMapping("/register")
    public ResultData register(@RequestParam String mobile, @RequestParam String password, @RequestParam String code){
        // 校验手机号是否已注册
        User user = userService.getUserByUsername(mobile);
        if(user != null){
            return ResultData.error(StatusCode.USER_EXIST);
        }

        // 校验短信验证码
        String activeCode = redisService.get("active_code_" + mobile);
        if(activeCode == null){
            return ResultData.error(StatusCode.ACTIVE_CODE_EXPIRED);
        }

        if(!code.equals(activeCode)){
            return ResultData.error(StatusCode.ACTIVE_CODE_INVALID);
        }

        String accessToken = UUID.randomUUID().toString().replaceAll("-","");
        Date atExpiredTime = DateUtils.getNewDateByAddSecond(properties.getAtExpireTime());
        String refreshToken = UUID.randomUUID().toString().replaceAll("-","");
        Date rtExpiredTime = DateUtils.getNewDateByAddSecond(properties.getRtExpireTime());

        user = new User();
        user.setUsername(mobile);
        user.setPassword(MD5Utils.encode(password));
        user.setAccessToken(accessToken);
        user.setAtExpiredTime(atExpiredTime);
        user.setRefreshToken(refreshToken);
        user.setRtExpiredTime(rtExpiredTime);

        /**
         * Token存在Redis中
         */
        redisService.set("access_token_" + user.getId(), accessToken, properties.getAtExpireTime());
        redisService.set("refresh_token_" + user.getId(), refreshToken, properties.getRtExpireTime());

        return ResultData.one(userService.saveUser(user));
    }

    @PostMapping("/login")
    public ResultData login(@RequestParam String mobile, @RequestParam String password){
        return getToken(mobile, password);
    }

//    @PostMapping("/get_token")
    private ResultData getToken(String username, String password){
        logger.info("---get token start---");
        if(username == null || password == null){
            logger.info("---get token error: {}", StatusCode.INVALID_PARAM.getMessage());
            return ResultData.error(StatusCode.INVALID_PARAM);
        }

        User user = userService.getUserByUsernameAndPwd(username, MD5Utils.encode(password));
        if(user == null){
            logger.info("---get token error: {}", StatusCode.USER_NOT_EXIST.getMessage());
            return ResultData.error(StatusCode.USER_NOT_EXIST);
        }

        logger.info("---save token---");
        user = userService.genToken(user);

        logger.info("---get token done---");
        return ResultData.one(new TokenModel(user.getId(), user.getAccessToken(), user.getRefreshToken(), user.getAtExpiredTime()));
    }

    @PostMapping("/refresh_token")
    public ResultData refreshToken(@RequestParam String userId, @RequestParam String refreshToken){
        logger.info("---refresh token start---");
        if(refreshToken == null || userId == null){
            logger.info("---refresh token error: {}", StatusCode.INVALID_PARAM.getMessage());
            return ResultData.error(StatusCode.INVALID_PARAM);
        }

        User user = userService.getUserById(userId);
        if(user == null){
            logger.info("---refresh token error: {}", StatusCode.USER_NOT_EXIST.getMessage());
            return ResultData.error(StatusCode.USER_NOT_EXIST);
        }

        /**
         * 从Redis中获取refreshToken，并与前端传入对比
         * redisService.get("refresh_token_" + userId);
         */

        if(!refreshToken.equals(user.getRefreshToken())){
            throw new CommonException(StatusCode.REFRESH_TOKEN_INVALID.getCode());
        }

        if(DateUtils.getDistanceBetweenTimes(new Date(), user.getRtExpiredTime()) > 0){
            // 当前时间大于过期时间，token已过期
            throw new CommonException(StatusCode.REFRESH_TOKEN_EXPIRED.getCode());
        }

        logger.info("---save token---");
        user = userService.genToken(user);

        logger.info("---refresh token done---");
        return ResultData.one(new TokenModel(user.getId(), user.getAccessToken(), user.getRefreshToken(), user.getAtExpiredTime()));
    }

    @GetMapping("sms")
    public void testSms() throws Exception{
        smsService.send();
    }

    @GetMapping("sms1")
    public void testSms1() throws Exception{
        redisService.set("k", "{'id':'1'}");
        System.out.println(redisService.get("k"));
//        smsService.multiSend();
    }
}
