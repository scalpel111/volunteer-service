package com.volunteer.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.volunteer.common.Result;
import com.volunteer.dto.ActivityDTO;
import com.volunteer.entity.Activity;
import com.volunteer.mapper.ActivityMapper;
import com.volunteer.service.ActivityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.volunteer.utils.RedisConstants.*;


@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements ActivityService {

    private LocalDateTime dateTime;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Result<List<Activity>> getActivity() {
        List<Activity> list = list();
        return Result.success(list);
    }

    @Override
    public Result<List<ActivityDTO>> getByAddress(String address) {
        dateTime = LocalDateTime.now();

        QueryWrapper<Activity> queryWrapper = new QueryWrapper<>();
        queryWrapper.gt("start_time", dateTime);
        queryWrapper.like("address", address);

        List<Activity> list = list(queryWrapper);

        List<ActivityDTO> res = new ArrayList<>();
        for (Activity activity : list) {
            res.add(new ActivityDTO(activity.getTheme(), activity.getRecruitNumber(), activity.getAddress()));
        }

        return Result.success(res);
    }

    @Override
    public Result<List<ActivityDTO>> listByTheme(String theme) {
        dateTime = LocalDateTime.now();

        QueryWrapper<Activity> queryWrapper = new QueryWrapper<>();
        queryWrapper.gt("start_time", dateTime);
        queryWrapper.like("theme", theme);

        List<Activity> list = list(queryWrapper);
        List<ActivityDTO> res = new ArrayList<>();
        for (Activity activity : list) {
            res.add(new ActivityDTO(activity.getTheme(), activity.getRecruitNumber(), activity.getAddress()));
        }

        return Result.success(res);
    }

    @Override
    public Result<List<ActivityDTO>> getByTip(String tip) {
        dateTime = LocalDateTime.now();
        QueryWrapper<Activity> queryWrapper = new QueryWrapper<>();
        queryWrapper.gt("start_time", dateTime);
        if (tip != null) {
            queryWrapper.eq("tip", tip);
        }
        List<Activity> list = list(queryWrapper);
        List<ActivityDTO> res = new ArrayList<>();
        for (Activity activity : list) {
            res.add(new ActivityDTO(activity.getTheme(), activity.getRecruitNumber(), activity.getAddress()));
        }

        return Result.success(res);
    }


    @Override
    public Result<Object> insert(Activity activity) {
        stringRedisTemplate.opsForZSet().add(CACHE_ACTIVITYS_KEY, JSON.toJSONString(activity), System.currentTimeMillis());
        return Result.success("活动申请已提交，正在审批中！");
    }

    @Override
    public Result<Object> update(Activity activity) {
        stringRedisTemplate.opsForZSet().add(
                CACHE_ACTIVITYS_UPDATE_KEY,
                JSON.toJSONString(activity),
                System.currentTimeMillis());
        return Result.success("活动修改审批已提交！");
    }

    @Override
    public Result<Object> deleteById(int id) {
        boolean remove = removeById(id);
        if (!remove) {
            return Result.fail("删除失败");
        }
        return Result.success();
    }

    @Override
    public Result<Activity> getByTheme(String theme) {
        Activity activity = query().eq("theme", theme).one();
        return Result.success(activity);
    }

    @Override
    public Result<Object> ratify() {
        Set<String> range = stringRedisTemplate.opsForZSet().range(CACHE_ACTIVITYS_KEY, 0l, Long.MAX_VALUE);
        List<Activity> activities = new ArrayList<>();
        for (String s : range) {
            Activity activity = JSON.parseObject(s, Activity.class);
            activities.add(activity);
        }
        return Result.success(activities);
    }


    @Override
    public Result<Object> add(Activity activity) {
        boolean save = save(activity);
        if (save) {
            stringRedisTemplate.opsForZSet().remove(CACHE_ACTIVITYS_KEY, JSON.toJSONString(activity));
            //生成一个活动的验证码
            String code = RandomUtil.randomNumbers(6);
            //将验证码存入到redis
            stringRedisTemplate.opsForValue().set(CHECK_KEY + activity.getActivityId(), code);
            return Result.success("活动审批成功，已添加至数据库！");
        } else return Result.fail("添加失败！");
    }

    @Override
    public Result<Object> ratifyFalse(Activity activity) {
        String jsonString = JSON.toJSONString(activity);
        stringRedisTemplate.opsForZSet().remove(CACHE_ACTIVITYS_KEY, jsonString);
        return Result.fail("审批完成，已驳回！");
    }

    @Override
    public Result<Object> ratifyUpdate() {
        Set<String> range = stringRedisTemplate.opsForZSet().range(CACHE_ACTIVITYS_UPDATE_KEY, 0l, Long.MAX_VALUE);
        List<Activity> activities = new ArrayList<>();
        for (String s : range) {
            Activity activity = JSON.parseObject(s, Activity.class);
            activities.add(activity);
        }
        return Result.success(activities);
    }

}