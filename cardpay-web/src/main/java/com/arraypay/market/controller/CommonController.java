package com.arraypay.market.controller;

import com.arraypay.market.rest.ResultList;
import com.arraypay.market.service.CountryService;
import com.arraypay.market.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
@RequestMapping("/common")
public class CommonController {

    @Autowired
    private CountryService countryService;

    @ApiOperation(value = "获取国家码列表")
    @PostMapping("/list")
    public ResultList listNationCode(){
        return ResultList.list(countryService.listCountries());
    }

}
