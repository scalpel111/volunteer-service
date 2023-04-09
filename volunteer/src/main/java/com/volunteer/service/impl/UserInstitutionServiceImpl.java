package com.volunteer.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.volunteer.common.JWTUtil;
import com.volunteer.common.Result;
import com.volunteer.dto.InstitutionDTO;
import com.volunteer.dto.UserDTO;
import com.volunteer.entity.Institution;
import com.volunteer.entity.User;
import com.volunteer.entity.UserInstitution;
import com.volunteer.mapper.UserInstitutionMapper;
import com.volunteer.service.InstitutionService;
import com.volunteer.service.UserInstitutionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.volunteer.service.UserService;
import com.volunteer.utils.UserHolder;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.volunteer.utils.RedisConstants.CACHE_USER_INSTITUTION_KEY;

@Service
public class UserInstitutionServiceImpl extends ServiceImpl<UserInstitutionMapper, UserInstitution> implements UserInstitutionService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private InstitutionService institutionService;
    @Resource
    private UserService userService;
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

    @Override
    public Result<InstitutionDTO> getInstitution(String token) {
        DecodedJWT jwt = JWTUtil.getToken(token);
        String openid = jwt.getClaim("openid").asString();
        UserInstitution userInstitution = query().eq("openid", openid).eq("status", 1).one();
        if(userInstitution == null){
            return Result.fail("您还没有管理的组织");
        }
        Institution institution = institutionService.getById(userInstitution.getInstitutionId());
        return Result.success(new InstitutionDTO(institution.getInstitutionId(),institution.getInstitutionName(),
                institution.getAddress(), null,institution.getCount()));
    }

    @Override
    public Result<List<UserDTO>> getUserByInstitutionId(Integer id) {
        List<UserInstitution> list = query().eq("institution_id", id).list();
        List<String> openids = new ArrayList<>();
        for (UserInstitution userInstitution : list) {
            openids.add(userInstitution.getOpenid());
        }
        List<User> users = userService.listByIds(openids);
        List<UserDTO> res = new ArrayList<>();
        for (User user : users) {
            res.add(BeanUtil.copyProperties(user,UserDTO.class));
        }
        return Result.success(res);
    }
    @Override
    public Result<Object> ratifyInsert(Integer institutionId) {
        String openid = UserHolder.getUser().getOpenid();
        stringRedisTemplate.opsForZSet().add(CACHE_USER_INSTITUTION_KEY + institutionId, JSON.toJSONString(openid), System.currentTimeMillis());
        return Result.success("加入组织申请已提交，正在审批中！");
    }

    @Override
    public Result<Object> ratify(Integer institutionId) {
        Set<String> range = stringRedisTemplate.opsForZSet().range(CACHE_USER_INSTITUTION_KEY + institutionId, 0l, Long.MAX_VALUE);
        List<UserInstitution> userInstitutions = new ArrayList<>();
        for (String s : range) {
            UserInstitution userInstitution = JSON.parseObject(s, UserInstitution.class);
            userInstitutions.add(userInstitution);
        }
        return Result.success(userInstitutions);
    }

    @Override
    public Result<Object> ratifyOk(UserInstitution userInstitution) {
        userInstitution.setStatus(0);
        boolean save = save(userInstitution);
        if(!save){
            return Result.fail("加入组织审批不通过！");
        }
        userInstitution.setId(null);
        stringRedisTemplate.opsForZSet()
                .remove(CACHE_USER_INSTITUTION_KEY + userInstitution.getInstitutionId(),
                        JSON.toJSONString(userInstitution.getOpenid()));
        return Result.success("加入组织审批成功，已添加至数据库！");
    }

    @Override
    public Result<Object> ratifyFalse(UserInstitution userInstitution) {
        String jsonString = JSON.toJSONString(userInstitution.getOpenid());
        stringRedisTemplate.opsForZSet().remove(CACHE_USER_INSTITUTION_KEY + userInstitution.getInstitutionId(), jsonString);
        return Result.fail("审批完成，已驳回！");
    }
}
