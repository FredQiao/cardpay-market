package com.arraypay.market.service;

import com.arraypay.market.constant.StatusCode;
import com.arraypay.market.constant.SysProperties;
import com.arraypay.market.exception.CommonException;
import com.arraypay.market.sms.SmsSender;
import com.arraypay.market.util.StringUtils;
import com.github.qcloudsms.SmsSingleSenderResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by fred on 2017/12/5.
 */
@Service
public class SmsService {

    @Autowired
    private SmsSender smsSender;
    @Autowired
    private SysProperties properties;
    @Autowired
    private RedisService redisService;

    public void sendVerifyCode(String mobile, String nationCode) throws Exception{
        String activeCode = StringUtils.getRandom();

//        SmsSingleSenderResult result = smsSender.sendSms(0, nationCode, mobile, "【"+ properties.getSysTitle() +"】验证码为" + activeCode, "", "");

//        if(result.result == 0){
            redisService.set("active_code_" + mobile, activeCode, 30, TimeUnit.MINUTES);
//        }else{
//            throw new CommonException(StatusCode.SYS_ERROR.getCode(), result.errMsg);
//        }
    }

    public void send() throws Exception{
        smsSender.sendSms(0, "86", "18326693192", "【腾讯】验证码测试1234", "", "123");

        ArrayList<String> params = new ArrayList<>();
        params.add("指定模板单发");
        params.add("深圳");
        params.add("小明");
        smsSender.sendSmsWithTemplate("86", "18326693192", 123, params, "", "", "");
    }

    public void multiSend() throws Exception{
        ArrayList<String> phoneNumbers = new ArrayList<>();
        phoneNumbers.add("13101116651");
        phoneNumbers.add("13101116652");
        phoneNumbers.add("13101116653");
        smsSender.multiSendSms(0, "86", phoneNumbers, "【腾讯】测试短信，普通群发，深圳，小明，上学。", "", "");

        ArrayList<String> params = new ArrayList<>();
        params.add("指定模板群发");
        params.add("深圳");
        params.add("小明");
        smsSender.multiSendSmsWithTemplate("86", phoneNumbers, 123, params, "", "", "");
    }

}
