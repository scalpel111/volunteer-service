package com.volunteer.controller;


import com.volunteer.common.Result;
import com.volunteer.dto.ActivityDTO;
import com.volunteer.dto.UserDTO;
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

    //根据活动查询参加的用户
    @GetMapping("/{activity_id}")
    public Result<List<UserDTO>> getUserActivity(@PathVariable("activity_id") Integer id){
        return userActivityService.getUserActivity(id);
    }

    //根据用户查询已参加过的活动
    @GetMapping("/join")
    public Result<List<ActivityDTO>> getUserActivity(@RequestHeader String token){
        return userActivityService.getUserActivity(token);
    }
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

    //后台删除
    @DeleteMapping("/back/{id}")
    public Result<Object> deleteById(@PathVariable("id") int id){
        return userActivityService.deleteById(id);
    }

    //用户报名活动，存入redis
    @PutMapping("/ratifyInsert")
    public Result<Object> ratifyInsert(@RequestParam Integer activityId){
        return userActivityService.ratifyInsert(activityId);
    }

    //组织方管理员端进行审批，从redis中取出申请信息
    @GetMapping("/ratify")
    public Result<Object> ratify(@RequestParam Integer activityId){
        return userActivityService.ratify(activityId);
    }

    //组织方管理员端审批通过，加入数据库
    @PutMapping("/ratifyOk")
    public Result<Object> insert(@RequestBody UserActivity userActivity){
        return userActivityService.ratifyOk(userActivity);
    }

    //组织方管理员端审批不通过，从redis之中移除
    @DeleteMapping("/ratifyFalse")
    public Result<Object> ratifyFalse(@RequestBody UserActivity userActivity) {
        return userActivityService.ratifyFalse(userActivity);
    }

    //用户签到
    @PutMapping("/check")
    public Result<Object> check(@RequestParam String code, @RequestParam Integer activityId){
        return userActivityService.check(code, activityId);
    }
}
