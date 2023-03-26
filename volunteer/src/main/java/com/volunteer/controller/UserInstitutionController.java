package com.volunteer.controller;


import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.volunteer.common.JWTUtil;
import com.volunteer.common.Result;
import com.volunteer.service.UserInstitutionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user-institution")
public class UserInstitutionController {

    @Resource
    private UserInstitutionService userInstitutionService;
    //查看是否是组织管理员
    @GetMapping
    public Result<Object> isAdmin(@RequestHeader String token){
        return userInstitutionService.isAdmin(token);
    }

}
