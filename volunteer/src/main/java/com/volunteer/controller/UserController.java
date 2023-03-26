package com.volunteer.controller;


import com.volunteer.common.Result;
import com.volunteer.dto.LoginDTO;
import com.volunteer.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/{code}")
    public Result<Object> login(@PathVariable("code") String code){
        return userService.login(code);
    }
}
