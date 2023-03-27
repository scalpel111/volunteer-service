package com.volunteer.service;

import com.volunteer.common.Result;
import com.volunteer.dto.ActivityDTO;
import com.volunteer.dto.UserDTO;
import com.volunteer.entity.Institution;
import com.volunteer.entity.UserActivity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface UserActivityService extends IService<UserActivity> {

    Result<List<UserDTO>> getUserActivity(Integer id);

    Result<List<UserActivity>> getUserActivity(String  token);

    Result<List<UserActivity>> select();

    Result<Object> updateUserActivity(UserActivity userActivity);

    Result<Object> insert(UserActivity userActivity);

    Result<Object> deleteById(int id);
}
