package com.arraypay.market;

import com.github.qcloudsms.SmsMultiSender;
import com.github.qcloudsms.SmsMultiSenderResult;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class SmsSender {

    Logger logger = LoggerFactory.getLogger(SmsSender.class);

    @Value("${spring.qcloudsms.appid}")
    private Integer appid;

    @Value("${spring.qcloudsms.appkey}")
    private String appKey;

    private SmsSingleSender singleSender;

    private SmsMultiSender multiSender;

    private void initSmsSingleSender() throws Exception{
        if(singleSender == null){
            logger.info("initSmsSingleSender");
            singleSender = new SmsSingleSender(appid, appKey);
        }
    }

    private void initSmsMultiSender() throws Exception{
        if(multiSender == null){
            logger.info("initSmsMultiSender");
            multiSender = new SmsMultiSender(appid, appKey);
        }
    }

    public SmsSingleSenderResult sendSms(Integer type, String nationCode, String phoneNumber, String msg, String extend, String ext) throws Exception{
        initSmsSingleSender();
        return singleSender.send(type, nationCode, phoneNumber, msg, extend, ext);
    }

    public SmsSingleSenderResult sendSmsWithTemplate(String nationCode, String phoneNumber, Integer templId, ArrayList<String> params, String sign, String extend, String ext) throws Exception{
        initSmsSingleSender();
        return singleSender.sendWithParam(nationCode, phoneNumber, templId, params, sign, extend, ext);
    }

    public SmsMultiSenderResult multiSendSms(Integer type, String nationCode, ArrayList<String> phoneNumbers, String msg, String extend, String ext) throws Exception{
        initSmsMultiSender();
        return multiSender.send(type, nationCode, phoneNumbers, msg, extend, ext);
    }

    public SmsMultiSenderResult multiSendSmsWithTemplate(String nationCode, ArrayList<String> phoneNumbers, Integer templId, ArrayList<String> params, String sign, String extend, String ext) throws Exception{
        initSmsMultiSender();
        return multiSender.sendWithParam(nationCode, phoneNumbers, templId, params, sign, extend, ext);
    }

}
