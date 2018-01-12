package com.arraypay.market.controller;

import com.arraypay.market.annotation.Permission;
import com.arraypay.market.rest.ResultData;
import com.arraypay.market.rest.ResultList;
import com.arraypay.market.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Permission
    @ApiOperation(value = "获取用户列表")
    @PostMapping("/list")
    public ResultList indexMobile(@RequestParam(value = "pageNumber", required = false) Integer pageNumber, HttpServletRequest request){
        return ResultList.list(userService.listUsers(pageNumber));
    }

    @PostMapping("/get")
    public ResultData get(@RequestParam String id){
        return ResultData.one(userService.getUserById(id));
    }
}
