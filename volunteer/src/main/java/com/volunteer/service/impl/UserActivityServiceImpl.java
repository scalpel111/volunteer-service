package com.volunteer.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.volunteer.common.Result;
import com.volunteer.entity.Institution;
import com.volunteer.entity.UserActivity;
import com.volunteer.entity.UserInstitution;
import com.volunteer.mapper.UserActivityMapper;
import com.volunteer.service.UserActivityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.volunteer.utils.RedisConstants.CACHE_USER_ACTIVITY_KEY;
import static com.volunteer.utils.RedisConstants.CACHE_USER_INSTITUTION_KEY;

@Service
public class UserActivityServiceImpl extends ServiceImpl<UserActivityMapper, UserActivity> implements UserActivityService {

    @Resource
    private StringRedisTemplate  stringRedisTemplate;
    @Override
    public Result<List<UserActivity>> select() {
        List<UserActivity> list = list();
        return Result.success(list);
    }

    @Override
    public Result<Object> updateUserActivity(UserActivity userActivity) {
        QueryWrapper<UserActivity> queryWrapper = new QueryWrapper<>(userActivity);
        boolean update = update(queryWrapper);
        if(!update){
            return Result.fail("更新失败");
        }
        return Result.success();
    }

    @Override
    public Result<Object> insert(UserActivity userActivity) {
        boolean save = save(userActivity);
        if(!save){
            return Result.fail("添加失败");
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

    @Override
    public Result<Object> ratifyInsert(UserActivity userActivity) {
        stringRedisTemplate.opsForZSet().add(CACHE_USER_ACTIVITY_KEY + userActivity.getActivityId(), JSON.toJSONString(userActivity.getUserId()), System.currentTimeMillis());
        return Result.success("报名申请已提交，正在审批中！");
    }

    @Override
    public Result<Object> ratify(Integer activityId) {
        Set<String> range = stringRedisTemplate.opsForZSet().range(CACHE_USER_ACTIVITY_KEY + activityId, 0l, Long.MAX_VALUE);
        List<UserActivity> userActivities = new ArrayList<>();
        for (String s : range) {
            UserActivity userActivity = JSON.parseObject(s, UserActivity.class);
            userActivities.add(userActivity);
        }
        return Result.success(userActivities);
    }

    @Override
    public Result<Object> ratifyOk(UserActivity userActivity) {
        boolean save = save(userActivity);
        if(!save){
            return Result.fail("审批不通过！");
        }
        userActivity.setStatus(1);
        return Result.success("审批成功，已添加至数据库！");
    }

    @Override
    public Result<Object> ratifyFalse(UserActivity userActivity) {
        String jsonString = JSON.toJSONString(userActivity.getUserId());
        stringRedisTemplate.opsForZSet().remove(CACHE_USER_ACTIVITY_KEY + userActivity.getActivityId(), jsonString);
        return Result.fail("审批完成，已驳回！");
    }
}
