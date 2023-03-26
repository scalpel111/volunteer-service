package com.volunteer.service;

import com.volunteer.common.Result;
import com.volunteer.entity.UserInstitution;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserInstitutionService extends IService<UserInstitution> {

    Result<Object> isAdmin(String token);
}
