package com.volunteer.service;

import com.volunteer.common.Result;
import com.volunteer.dto.InstitutionDTO;
import com.volunteer.dto.UserDTO;
import com.volunteer.entity.UserInstitution;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface UserInstitutionService extends IService<UserInstitution> {

    Result<Object> isAdmin(String token);

    Result<InstitutionDTO> getInstitution(String token);

    Result<List<UserDTO>> getUserByInstitutionId(Integer id);

    Result<Object> ratifyInsert(Integer institutionId);

    Result<Object> ratify(Integer institutionId);

    Result<Object> ratifyOk(UserInstitution userInstitution);

    Result<Object> ratifyFalse(UserInstitution userInstitution);
}
