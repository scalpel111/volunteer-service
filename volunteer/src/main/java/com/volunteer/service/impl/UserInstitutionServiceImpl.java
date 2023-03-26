package com.volunteer.service.impl;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.volunteer.common.JWTUtil;
import com.volunteer.common.Result;
import com.volunteer.entity.UserInstitution;
import com.volunteer.mapper.UserInstitutionMapper;
import com.volunteer.service.UserInstitutionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserInstitutionServiceImpl extends ServiceImpl<UserInstitutionMapper, UserInstitution> implements UserInstitutionService {

    @Override
    public Result<Object> isAdmin(String token) {
        DecodedJWT jwt = JWTUtil.getToken(token);
        String openid = jwt.getClaim("openid").asString();
        UserInstitution userInstitution = query().eq("openid", openid).one();
        if(userInstitution == null || userInstitution.getStatus() == 0){

            return Result.fail("没有绑定机构");
        }
        return Result.success();
    }
}
