package com.arraypay.market.service;

import com.arraypay.market.SmsSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by fred on 2017/12/5.
 */
@Service
public class SmsService {

    @Autowired
    private SmsSender smsSender;

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
