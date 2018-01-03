package com.arraypay.market.controller;

import com.arraypay.market.rest.ResultData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import util.MD5Util;

@RestController
public class HomeController {

    @RequestMapping("/get_token")
    public ResultData getToken(@RequestParam String userid){
        return ResultData.one(MD5Util.encode(userid));
    }
}
