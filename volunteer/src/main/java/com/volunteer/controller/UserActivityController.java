package com.volunteer.controller;


import com.volunteer.common.Result;
import com.volunteer.entity.Institution;
import com.volunteer.entity.UserActivity;
import com.volunteer.service.UserActivityService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user-activity")
public class UserActivityController {

    @Resource
    private UserActivityService userActivityService;
    //后台查询
    @GetMapping("/back")
    public Result<List<UserActivity>> select(){
        return userActivityService.select();
    }

    //后台修改
    @PostMapping("/back")
    public Result<Object> update(@RequestBody UserActivity userActivity){
        return userActivityService.updateUserActivity(userActivity);
    }

    //后台添加
    @PutMapping("/back")
    public Result<Object> insert(@RequestBody UserActivity userActivity){
        return userActivityService.insert(userActivity);
    }

    //后台删除
    @DeleteMapping("/back/{id}")
    public Result<Object> deleteById(@PathVariable("id") int id){
        return userActivityService.deleteById(id);
    }
}
