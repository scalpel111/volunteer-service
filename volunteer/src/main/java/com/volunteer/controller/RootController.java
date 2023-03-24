package com.volunteer.controller;


import com.volunteer.common.Result;
import com.volunteer.entity.Root;
import com.volunteer.mapper.RootMapper;
import com.volunteer.service.RootService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/root")
public class RootController {

    @Resource
    private RootService rootService;

    @PostMapping("/login")
    public Result<Object> login(@RequestBody Root root){
        return rootService.login(root);
    }

    @PostMapping("/register")
    public Result<Object> register(@RequestBody Root root){
        return rootService.register(root);
    }
}
