package com.volunteer.service;

import com.volunteer.common.Result;
import com.volunteer.entity.UserInstitution;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserInstitutionService extends IService<UserInstitution> {

    Result<Object> isAdmin(String token);

    Result<Object> ratifyInsert(UserInstitution userInstitution);

    Result<Object> ratify(Integer institutionId);

    Result<Object> ratifyOk(UserInstitution userInstitution);

    Result<Object> ratifyFalse(UserInstitution userInstitution);
}
