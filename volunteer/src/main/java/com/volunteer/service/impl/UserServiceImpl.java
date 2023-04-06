package com.volunteer.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.json.JSONObject;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.volunteer.common.JWTUtil;
import com.volunteer.common.Result;
import com.volunteer.common.WechatUtil;
import com.volunteer.dto.UserDTO;
import com.volunteer.entity.User;
import com.volunteer.mapper.UserMapper;
import com.volunteer.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.volunteer.utils.UserHolder;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;
import static com.volunteer.utils.RedisConstants.*;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public Result<Object> login(String code) {

        JSONObject sessionKeyOrOpenId = WechatUtil.getSessionKeyOrOpenId(code);
        String openId = sessionKeyOrOpenId.get("openid",String.class);
        String sessionKey = sessionKeyOrOpenId.get("sessionkey",String.class);

        User user = getOne(new QueryWrapper<User>().eq("openid",openId));
        // 保存用户信息到 redis中
        // 生成token，作为登录令牌
        Map<String,String> map = new HashMap<>();
        map.put("openid",openId);
        String token = JWTUtil.getToken(map);
        if(user == null){
            User user1 = new User();
            user1.setOpenid(openId);
            user1.setUsername(openId + sessionKey);
            user1.setSessionkey(sessionKey);
            save(user1);

            // 将User对象转为HashMap存储
            UserDTO userDTO1 = BeanUtil.copyProperties(user1, UserDTO.class);
            Map<String, Object> userMap = BeanUtil.beanToMap(userDTO1, new HashMap<>(),
            CopyOptions.create()
                    .setIgnoreNullValue(true)
                    .setFieldValueEditor((fieldName, fieldValue) -> fieldValue.toString()));
            // 存储
            String tokenKey1 = LOGIN_USER_KEY + token;
            stringRedisTemplate.opsForHash().putAll(tokenKey1, userMap);
            // 设置token有效期
            stringRedisTemplate.expire(tokenKey1, LOGIN_USER_TTL, TimeUnit.MINUTES);
            return Result.success(token);
        }
        // 将User对象转为HashMap存储
        UserDTO userDTO = BeanUtil.copyProperties(user, UserDTO.class);
        Map<String, Object> userMap = BeanUtil.beanToMap(userDTO, new HashMap<>(),
                CopyOptions.create()
                        .setIgnoreNullValue(true)
                        .setFieldValueEditor((fieldName, fieldValue) -> fieldValue.toString()));
        // 存储
        String tokenKey = LOGIN_USER_KEY + token;
        stringRedisTemplate.opsForHash().putAll(tokenKey, userMap);
        // 设置token有效期
        stringRedisTemplate.expire(tokenKey, LOGIN_USER_TTL, TimeUnit.MINUTES);
        return Result.success(token);

    }

    @Override
    public Result<List<UserDTO>> getRanking() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("service_time","identify_id");
        List<User> users = list(queryWrapper);
        List<UserDTO> res = new ArrayList<>();
        for (User user : users) {
            res.add(BeanUtil.copyProperties(user,UserDTO.class));
        }
        return Result.success(res);
    }

    @Override
    public Result<UserDTO> getHome(String token) {
        if(token == null){
            return Result.fail("未登录");
        }
        DecodedJWT jwt = JWTUtil.getToken(token);
        String openid = jwt.getClaim("openid").asString();
        User user = query().eq("openid", openid).one();
        if(user == null){
            return Result.fail("未登录");
        }
        UserDTO res = BeanUtil.copyProperties(user, UserDTO.class);
        return Result.success(res);
    }

    @Override
    public Result<User> getPerson(String token) {
        if(token == null){
            return Result.fail("未登录");
        }
        DecodedJWT jwt = JWTUtil.getToken(token);
        String openid = jwt.getClaim("openid").asString();
        User user = query().eq("openid", openid).one();
        return Result.success(user);
    }

    @Override
    public Result<Object> updateUser(User user,String token) {
        DecodedJWT jwt = JWTUtil.getToken(token);
        String openid = jwt.getClaim("openid").asString();
        user.setOpenid(openid);
        boolean update = updateById(user);
        if(!update){
            return Result.fail("修改失败");
        }
        return Result.success();
    }

    public Result<Object> getMessages() {
        // 1.获取当前用户
        String openid = UserHolder.getUser().getOpenid();
        // 2.查询收件箱 ZREVRANGEBYSCORE key Max Min LIMIT offset count
        String key = USER_MESSAGE_KEY + openid;
        Set<String> strings = stringRedisTemplate.opsForZSet().reverseRangeByScore(key, 0l, Long.MAX_VALUE);
        // 3.非空判断
        if (strings == null || strings.isEmpty()) {
            return Result.success();
        }
        // 4.传集合
        List<String> messages = new ArrayList<>();
        for (String message : strings) {
            messages.add(message);
        }
        return Result.success(messages);
    }
}
