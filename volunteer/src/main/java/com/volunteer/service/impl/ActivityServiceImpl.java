package com.volunteer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.volunteer.common.Result;
import com.volunteer.entity.Activity;
import com.volunteer.mapper.ActivityMapper;
import com.volunteer.service.ActivityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements ActivityService {

    private LocalDateTime dateTime;

    @Override
    public Result<List<Activity>> getActivity() {
        List<Activity> list = list();
        return Result.success(list);
    }

    @Override
    public Result<List<Activity>> getByAddress(String address) {
        dateTime = LocalDateTime.now();

        QueryWrapper<Activity> queryWrapper = new QueryWrapper<>();
        queryWrapper.gt("start_time",dateTime);
        queryWrapper.like("address",address);

        List<Activity> list = list(queryWrapper);

        return Result.success(list);
    }

    @Override
    public Result<List<Activity>> getByTheme(String theme) {
        dateTime = LocalDateTime.now();

        QueryWrapper<Activity> queryWrapper = new QueryWrapper<>();
        queryWrapper.gt("start_time",dateTime);
        queryWrapper.like("theme",theme);

        List<Activity> list = list(queryWrapper);

        return Result.success(list);
    }

    @Override
    public Result<List<Activity>> getByTip(String tip) {
        dateTime = LocalDateTime.now();
        QueryWrapper<Activity> queryWrapper = new QueryWrapper<>();
        queryWrapper.gt("start_time",dateTime);
        if(tip != null){
            queryWrapper.eq("tip", tip);
        }
        return Result.success(list(queryWrapper));
    }

    @Override
    public Result<Object> insert() {
        return null;
    }

    @Override
    public Result<Object> update(Activity activity) {
        boolean update = update(new QueryWrapper<>(activity));
        if(!update){
            return Result.fail("更新失败");
        }
        return Result.success();
    }

    @Override
    public Result<Object> deleteById(int id) {
        boolean remove = removeById(id);
        if(!remove){
            return Result.fail("删除失败");
        }
        return Result.success();
    }
}
