package com.volunteer.service;

import com.volunteer.common.Result;
import com.volunteer.dto.LoginDTO;
import com.volunteer.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserService extends IService<User> {

    Result<LoginDTO> login(String code);
}
