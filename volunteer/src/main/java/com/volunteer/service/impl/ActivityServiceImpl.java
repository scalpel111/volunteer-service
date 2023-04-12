package com.volunteer.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.volunteer.common.Result;
import com.volunteer.dto.ActivityDTO;
import com.volunteer.dto.WebActivityDTO;
import com.volunteer.entity.Activity;
import com.volunteer.entity.InstitutionActivity;
import com.volunteer.mapper.ActivityMapper;
import com.volunteer.mapper.InstitutionActivityMapper;
import com.volunteer.service.ActivityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;

import static com.volunteer.utils.RedisConstants.*;


@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements ActivityService {
    @DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm:ss")
    private LocalDateTime dateTime;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private InstitutionActivityMapper institutionActivityMapper;

    @Override
    public Result<List<Activity>> getActivity() {
        List<Activity> list = list();
        return Result.success(list);
    }

    @Override
    public Result<List<ActivityDTO>> getByAddress(String address) {
        dateTime = LocalDateTime.now();

//        QueryWrapper<Activity> queryWrapper = new QueryWrapper<>();
//        queryWrapper.gt("start_time", dateTime);
//        queryWrapper.like("address", address);

//        List<Activity> list = list(queryWrapper);

        List<Activity> list = query()
                .gt("start_time", dateTime)
                .like("address", address)
                .list();

        List<ActivityDTO> res = new ArrayList<>();
        for (Activity activity : list) {
            System.out.println(activity.getStartTime());
            res.add(BeanUtil.copyProperties(activity, ActivityDTO.class));
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
            res.add(BeanUtil.copyProperties(activity,ActivityDTO.class));
        }

        return Result.success(res);
    }

    @Override
    public Result<List<ActivityDTO>> getByTip(String tip) {
        dateTime = LocalDateTime.now();
        QueryWrapper<Activity> queryWrapper = new QueryWrapper<>();
        queryWrapper.gt("start_time", dateTime);
        if (tip != "") {
            queryWrapper.eq("tip", tip);
        }
        List<Activity> list = list(queryWrapper);
        List<ActivityDTO> res = new ArrayList<>();
        for (Activity activity : list) {
            res.add(BeanUtil.copyProperties(activity,ActivityDTO.class));
        }

        return Result.success(res);
    }


    @Override
    public Result<Object> insert(Activity activity, Integer institutionId) {
        Map<Integer, Activity> map = new HashMap<>();
        map.put(institutionId, activity);
        stringRedisTemplate.opsForZSet().add(CACHE_ACTIVITYS_KEY, JSON.toJSONString(map), System.currentTimeMillis());
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
    public Result<Activity> getByActivityId(Integer id) {
        Activity activity = getById(id);
        return Result.success(activity);
    }

    @Override
    public Result<Object> ratify() {
        Set<String> range = stringRedisTemplate.opsForZSet().range(CACHE_ACTIVITYS_KEY, 0l, Long.MAX_VALUE);
        List<WebActivityDTO> activities = new ArrayList<>();
        for (String s : range) {
            String[] split = s.split(":", 2);
            String s1 = split[0].substring(1);
            Integer institutionId = Integer.parseInt(s1);
            String s2 = split[1].substring(0, split[1].length() - 1);
            Activity activity = JSON.parseObject(s2, Activity.class);
            activities.add(new WebActivityDTO(institutionId,activity));
        }
        return Result.success(activities);
    }


    @Override
    public Result<Object> add(Activity activity, Integer institutionId) {
        //添加在活动实体表中
        System.out.println(activity.toString());
        boolean save = save(activity);
        if (save) {
            //添加在组织-活动表中
            InstitutionActivity institutionActivity = new InstitutionActivity();
            institutionActivity.setActivityId(query().eq("theme",activity.getTheme()).one().getActivityId());
            institutionActivity.setInstitutionId(institutionId);
            institutionActivityMapper.insert(institutionActivity);
            activity.setActivityId(null);
            Map<Integer, Activity> map = new HashMap<>();
            map.put(institutionId, activity);
            stringRedisTemplate.opsForZSet().remove(CACHE_ACTIVITYS_KEY, JSON.toJSONString(map));
//            System.out.println(remove.longValue());
            //生成一个活动的验证码
            String code = RandomUtil.randomNumbers(6);
            //将验证码存入到redis
            stringRedisTemplate.opsForValue().set(CHECK_KEY + activity.getActivityId(), code);
            return Result.success("活动审批成功，活动编码为：" + code);
        } else return Result.fail("添加失败！");
    }

    @Override
    public Result<Object> ratifyFalse(Activity activity, Integer institutionId) {
        System.out.println(activity);
        activity.setActivityId(null);
        Map<Integer, Activity> map = new HashMap<>();
        map.put(institutionId, activity);
        String jsonString = JSON.toJSONString(map);
        System.out.println(jsonString);
        stringRedisTemplate.opsForZSet().remove(CACHE_ACTIVITYS_KEY, jsonString);
        System.out.println(map);
        return Result.success();
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