package com.volunteer.service.impl;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.volunteer.common.JWTUtil;
import com.volunteer.common.Result;
import com.volunteer.common.WechatUtil;
import com.volunteer.dto.LoginDTO;
import com.volunteer.entity.User;
import com.volunteer.mapper.UserMapper;
import com.volunteer.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public Result<Object> login(String code) {

        JSONObject sessionKeyOrOpenId = WechatUtil.getSessionKeyOrOpenId(code);
        String openId = sessionKeyOrOpenId.get("openid",String.class);
        String sessionKey = sessionKeyOrOpenId.get("sessionkey",String.class);

        User user = getOne(new QueryWrapper<User>().eq("openid",openId));
        if(user == null){
            User user1 = new User();
            user1.setOpenid(openId);
            user1.setUsername(openId + sessionKey);
            user1.setSessionkey(sessionKey);
            save(user1);
        }
        Map<String,String> map = new HashMap<>();
        map.put("openid",openId);

        String token = JWTUtil.getToken(map);
        return Result.success(token);
    }
}
