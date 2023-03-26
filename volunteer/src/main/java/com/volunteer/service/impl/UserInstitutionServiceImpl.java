package com.volunteer.service.impl;

import com.alibaba.fastjson.JSON;
import com.volunteer.common.Result;
import com.volunteer.entity.Institution;
import com.volunteer.entity.UserInstitution;
import com.volunteer.mapper.UserInstitutionMapper;
import com.volunteer.service.UserInstitutionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.volunteer.utils.RedisConstants.CACHE_INSTITUTIONS_KEY;
import static com.volunteer.utils.RedisConstants.CACHE_USER_INSTITUTION_KEY;

@Service
public class UserInstitutionServiceImpl extends ServiceImpl<UserInstitutionMapper, UserInstitution> implements UserInstitutionService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public Result<Object> ratifyInsert(UserInstitution userInstitution) {
        stringRedisTemplate.opsForZSet().add(CACHE_USER_INSTITUTION_KEY + userInstitution.getInstitutionId(), JSON.toJSONString(userInstitution.getUserId()), System.currentTimeMillis());
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
        boolean save = save(userInstitution);
        if(!save){
            return Result.fail("加入组织审批不通过！");
        }
        return Result.success("加入组织审批成功，已添加至数据库！");
    }

    @Override
    public Result<Object> ratifyFalse(UserInstitution userInstitution) {
        String jsonString = JSON.toJSONString(userInstitution.getUserId());
        stringRedisTemplate.opsForZSet().remove(CACHE_USER_INSTITUTION_KEY + userInstitution.getInstitutionId(), jsonString);
        return Result.fail("审批完成，已驳回！");
    }
}
