package com.volunteer.service.impl;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.volunteer.common.Result;
import com.volunteer.common.WechatUtil;
import com.volunteer.dto.LoginDTO;
import com.volunteer.entity.User;
import com.volunteer.mapper.UserMapper;
import com.volunteer.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public Result<LoginDTO> login(String code) {

        JSONObject sessionKeyOrOpenId = WechatUtil.getSessionKeyOrOpenId(code);
        String openId = sessionKeyOrOpenId.get("openid",String.class);
        String sessionKey = sessionKeyOrOpenId.get("sessionkey",String.class);
        User byId = getById(openId);
        if(byId == null){

        }
        LoginDTO loginDTO = new LoginDTO(openId,sessionKey);
        return Result.success(loginDTO);
    }
}
