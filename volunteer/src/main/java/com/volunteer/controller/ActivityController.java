package com.volunteer.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.volunteer.common.Result;
import com.volunteer.entity.Activity;
import com.volunteer.mapper.ActivityMapper;
import com.volunteer.service.ActivityService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/activity")
public class ActivityController {

    @Resource
    private ActivityMapper activityMapper;

    @GetMapping("/tip")
    public Result<List<Activity>> getActivityByTip(@RequestParam String tip){

        Map<String,Object> objectMap = new HashMap<>();
        objectMap.put("tip",tip);

        List<Activity> list = activityMapper.selectByMap(objectMap);
        return  Result.success(list);
    }

    @PostMapping
    public Result<Object> insert(@RequestBody Activity activity){
        int success = activityMapper.insert(activity);
        if(success == 0){
            return Result.fail("添加失败");
        }
        return Result.success();
    }

    @PutMapping
    public Result<Object> updateById(@RequestBody Activity activity){

        int success = activityMapper.updateById(activity);
        if(success == 0){
            return Result.fail("更新失败");
        }

        return Result.success();
    }

    @DeleteMapping("/theme")
    public Result<Object> deleteByTheme(@RequestParam String theme){
        Map<String,Object> objectMap = new HashMap<>();
        objectMap.put("theme",theme);
        int success = activityMapper.deleteByMap(objectMap);
        if(success == 0){
            return Result.fail("删除失败");
        }
        return Result.success();
    }
}
