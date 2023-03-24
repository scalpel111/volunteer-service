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
    @PostMapping
    public Result<Object> insert(){
        return null;
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
