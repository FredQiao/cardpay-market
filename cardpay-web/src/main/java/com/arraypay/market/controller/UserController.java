package com.arraypay.market.controller;

import com.arraypay.market.annotation.Permission;
import com.arraypay.market.dto.form.TokenForm;
import com.arraypay.market.rest.ResultList;
import com.arraypay.market.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Permission
    @ApiOperation(value = "获取用户列表")
    @PostMapping("/list")
    public ResultList indexMobile(@RequestParam(value = "pageNumber", required = false) Integer pageNumber, @RequestBody TokenForm token){
        return ResultList.list(userService.listUsers(pageNumber));
    }
}
