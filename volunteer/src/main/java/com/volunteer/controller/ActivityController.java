package com.volunteer.controller;


import com.volunteer.common.Result;
import com.volunteer.dto.ActivityDTO;
import com.volunteer.entity.Activity;
import com.volunteer.service.ActivityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    @GetMapping
    public Result<List<Activity>> getActivity(){
        return activityService.getActivity();
    }
    //按地址查询
    //首页默认查询
    @GetMapping("/address")
    public Result<List<ActivityDTO>> getByAddress(@RequestParam String address){
        return activityService.getByAddress(address);
    }

    //按标签查询
    @GetMapping("/tip")
    public Result<List<ActivityDTO>> getByTip(@RequestParam String tip){
        return activityService.getByTip(tip);
    }

    //搜索框模糊查询
    @GetMapping("/theme-like")
    public Result<List<ActivityDTO>> listByTheme(@RequestParam String theme){
        return activityService.listByTheme(theme);
    }

    //点击活动查看详情
    @GetMapping("/{id}")
    public Result<Activity> getByActivityId(@PathVariable("id") Integer id){
        return activityService.getByActivityId(id);
    }

    //添加活动
    @PostMapping("/ratifyInsert")
    public Result<Object> insert(@RequestBody Activity activity, @RequestParam Integer institutionId){
        return activityService.insert(activity, institutionId);
    }

    //web端管理员查询全部活动申请列表
    @GetMapping("/ratify")
    public Result<Object> insert_back(){
        return activityService.ratify();
    }

    //web端管理员审批活动  审批通过，加入数据库
    @PostMapping("/ratifyOk/{institutionId}")
    public Result<Object> insert_back_ok(@RequestBody Activity activity, @PathVariable("institutionId") Integer institutionId) {
        return activityService.add(activity, institutionId);
    }

    //web端管理员审批活动  审批不通过，在redis里面删除
    @DeleteMapping("/ratifyFalse/{institutionId}")
    public Result<Object> insert_back_false(@RequestBody Activity activity, @PathVariable("institutionId") Integer institutionId) {
        return activityService.ratifyFalse(activity, institutionId);
    }
}
