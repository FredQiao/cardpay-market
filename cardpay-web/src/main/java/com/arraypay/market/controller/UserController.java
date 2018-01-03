package com.arraypay.market.controller;

import com.arraypay.market.annotation.Permission;
import com.arraypay.market.rest.ResultList;
import com.arraypay.market.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
    @RequestMapping("/list")
    public ResultList indexMobile(@RequestParam("pageNumber") int pageNumber, HttpServletRequest request){
        return ResultList.list(userService.listUsers(pageNumber));
    }
}
