package com.volunteer.controller;


import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.volunteer.common.JWTUtil;
import com.volunteer.common.Result;
import com.volunteer.entity.UserInstitution;
import com.volunteer.service.UserInstitutionService;
import org.springframework.web.bind.annotation.*;

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

    //用户找组织申请，存入redis
    @PutMapping("/ratifyInsert")
    public Result<Object> ratifyInsert(@RequestParam Integer institutionId){
        return userInstitutionService.ratifyInsert(institutionId);
    }

    //组织方管理员端进行审批，从redis中取出申请信息
    @GetMapping("/ratify")
    public Result<Object> ratify(@RequestParam Integer institutionId){
        return userInstitutionService.ratify(institutionId);
    }

    //组织方管理员端审批通过，加入数据库
    @PutMapping("/ratifyOk")
    public Result<Object> insert(@RequestBody UserInstitution userInstitution){
        return userInstitutionService.ratifyOk(userInstitution);
    }

    //组织方管理员端审批不通过，从redis之中移除
    @DeleteMapping("/ratifyFalse")
    public Result<Object> ratifyFalse(@RequestBody UserInstitution userInstitution) {
        return userInstitutionService.ratifyFalse(userInstitution);
    }

}
