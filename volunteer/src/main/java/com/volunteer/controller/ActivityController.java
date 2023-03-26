package com.volunteer.controller;


import com.volunteer.common.Result;
import com.volunteer.entity.Activity;
import com.volunteer.service.ActivityService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/activity")
public class ActivityController {

    @Resource
    private ActivityService activityService;

    //查询所有活动
    //后台管理
    @GetMapping("/back")
    public Result<List<Activity>> getActivity(){
        return activityService.getActivity();
    }
    //按地址查询
    //首页默认查询
    @GetMapping("/{address}")
    public Result<List<Activity>> getByAddress(@PathVariable("address") String address){

        return activityService.getByAddress(address);
    }

    //按标签查询
    @GetMapping("/{tip}")
    public Result<List<Activity>> getByTip(@PathVariable("tip") String tip){
        return activityService.getByTip(tip);
    }

    @GetMapping("/{theme}")
    public Result<List<Activity>> getByTheme(@PathVariable("theme") String theme){
        return activityService.getByTheme(theme);
    }

    // todo
    //添加活动
    @PostMapping("/ratifyInsert")
    public Result<Object> insert(@RequestBody Activity activity){
        return activityService.insert(activity);
    }

    //web端管理员查询全部活动申请列表
    @GetMapping("/ratify")
    public Result<Object> insert_back(){
        return activityService.ratify();
    }

    //web端管理员审批活动  审批通过，加入数据库
    @PostMapping("/ratifyOk")
    public Result<Object> insert_back_ok(@RequestBody Activity activity) {
        return activityService.add(activity);
    }

    //web端管理员审批活动  审批不通过，在redis里面删除
    @DeleteMapping("/ratifyFalse")
    public Result<Object> insert_back_false(@RequestBody Activity activity) {
        return activityService.ratifyFalse(activity);
    }

    //后台修改
    @PutMapping("/back")
    public Result<Object> update(@RequestBody Activity activity){

        return activityService.update(activity);
    }

    //按id删除活动
    //后台删除
    @DeleteMapping("/back/{id}")
    public Result<Object> deleteById(@PathVariable("id") int id){
        return activityService.deleteById(id);
    }

}
