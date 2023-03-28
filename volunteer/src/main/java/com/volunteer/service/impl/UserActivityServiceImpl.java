package com.volunteer.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.volunteer.common.JWTUtil;
import com.volunteer.common.Result;
import com.volunteer.dto.UserDTO;
import com.volunteer.entity.Activity;
import com.volunteer.entity.User;
import com.volunteer.entity.UserActivity;
import com.volunteer.mapper.UserActivityMapper;
import com.volunteer.service.ActivityService;
import com.volunteer.service.UserActivityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.volunteer.service.UserService;
import com.volunteer.utils.MessageConstants;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.volunteer.utils.MessageConstants.FALSE_ACTIVITY;
import static com.volunteer.utils.MessageConstants.OK_ACTIVITY;
import static com.volunteer.utils.RedisConstants.*;

@Service
public class UserActivityServiceImpl extends ServiceImpl<UserActivityMapper, UserActivity> implements UserActivityService {

    @Resource
    private UserService userService;

    @Resource
    private ActivityService activityService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Result<List<UserDTO>> getUserActivity(Integer id) {
        List<UserActivity> list = list(new QueryWrapper<UserActivity>()
                        .eq("activity_id", id));
        List<String> list1 = new ArrayList<>();
        for (UserActivity userActivity : list) {
            list1.add(userActivity.getOpenid());
        }
        List<User> users = userService.listByIds(list1);
        List<UserDTO> res = new ArrayList<>();
        for (User user : users) {
            res.add(BeanUtil.copyProperties(user, UserDTO.class));
        }
        return Result.success(res);
    }

    @Override
    public Result<List<UserActivity>> getUserActivity(String token) {
        if(token == null){
            return Result.fail("未登录");
        }
        DecodedJWT jwt = JWTUtil.getToken(token);
        String openid = jwt.getClaim("openid").asString();
        List<UserActivity> userActivities = query().eq("openid", openid).list();
        return Result.success(userActivities);
    }

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
        stringRedisTemplate.opsForZSet().add(CACHE_USER_ACTIVITY_KEY + userActivity.getActivityId(), JSON.toJSONString(userActivity.getOpenid()), System.currentTimeMillis());
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
        String s = JSON.toJSONString(userActivity.getOpenid());
        //审批通过从redis之中移除数据
        stringRedisTemplate.opsForZSet().remove(CACHE_USER_ACTIVITY_KEY + userActivity.getActivityId(), s);
        //在redis之中记录通过的所有用户，方便最后统一推送消息
        stringRedisTemplate.opsForZSet().add(ACTIVITY_USER_KEY + userActivity.getActivityId(), s, System.currentTimeMillis());
        //给用户发送通知审批通过
        stringRedisTemplate.opsForZSet().add(USER_MESSAGE_KEY + userActivity.getOpenid(), MessageConstants.OK_MESSAGE, System.currentTimeMillis());
        return Result.success("审批成功，已添加至数据库！");
    }

    @Override
    public Result<Object> ratifyFalse(UserActivity userActivity) {
        String jsonString = JSON.toJSONString(userActivity.getOpenid());
        stringRedisTemplate.opsForZSet().remove(CACHE_USER_ACTIVITY_KEY + userActivity.getActivityId(), jsonString);
        stringRedisTemplate.opsForZSet().add(USER_MESSAGE_KEY + userActivity.getOpenid(), MessageConstants.FALSE_MESSAGE, System.currentTimeMillis());
        return Result.fail("审批完成，已驳回！");
    }

    @Override
    public Result<Object> check(String code, UserActivity userActivity) {
        String key = CHECK_KEY + userActivity.getActivityId();
        String codeId = stringRedisTemplate.opsForValue().get(CHECK_KEY);
        if (codeId.equals(code)) {
            //签到成功
            Integer activityId = userActivity.getActivityId();
            Activity one = activityService.query().eq("activity_id", activityId).one();
            //计算时长
            LocalDateTime startTime = one.getStartTime();
            LocalDateTime endTime = one.getEndTime();
            Duration duration = Duration.between(startTime, endTime);
            long hours = duration.toHours();
            return Result.success(OK_ACTIVITY + hours);
        }
        return Result.fail(FALSE_ACTIVITY);
    }
}
