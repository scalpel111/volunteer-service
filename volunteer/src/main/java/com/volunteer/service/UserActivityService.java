package com.volunteer.service;

import com.volunteer.common.Result;
import com.volunteer.dto.ActivityDTO;
import com.volunteer.dto.UserDTO;
import com.volunteer.entity.UserActivity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface UserActivityService extends IService<UserActivity> {

    Result<List<UserDTO>> getUserActivity(Integer id);

    Result<List<ActivityDTO>> getUserActivity(String  token);

    Result<List<UserActivity>> select();

    Result<Object> updateUserActivity(UserActivity userActivity);

    Result<Object> insert(UserActivity userActivity);

    Result<Object> deleteById(int id);

    Result<Object> ratifyInsert(Integer activityId);

    Result<Object> ratify(Integer activityId);

    Result<Object> ratifyOk(UserActivity userActivity);

    Result<Object> ratifyFalse(UserActivity userActivity);

    Result<Object> check(String code, Integer activityId);
}
