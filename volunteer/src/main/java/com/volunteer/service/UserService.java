package com.volunteer.service;

import com.volunteer.common.Result;
import com.volunteer.dto.UserDTO;
import com.volunteer.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface UserService extends IService<User> {

    Result<Object> login(String code);

    Result<List<UserDTO>> getRanking();

    Result<UserDTO> getHome(String token);

    Result<User> getPerson(String token);
}
