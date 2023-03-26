package com.volunteer.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.volunteer.common.Result;
import com.volunteer.dto.InstitutionDTO;
import com.volunteer.entity.Activity;
import com.volunteer.entity.Institution;
import com.volunteer.entity.UserInstitution;
import com.volunteer.mapper.InstitutionMapper;
import com.volunteer.service.InstitutionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.volunteer.service.UserInstitutionService;
import io.swagger.models.auth.In;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.volunteer.utils.RedisConstants.CACHE_ACTIVITYS_KEY;
import static com.volunteer.utils.RedisConstants.CACHE_INSTITUTIONS_KEY;

@Service
public class InstitutionServiceImpl extends ServiceImpl<InstitutionMapper, Institution> implements InstitutionService {

    @Resource
    private UserInstitutionService userInstitutionService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public Result<List<InstitutionDTO>> wrap(List<Institution> institutions){
        List<InstitutionDTO> institutionDTOS = new ArrayList<>();
        for (Institution institution : institutions) {
            int number = userInstitutionService.count(new QueryWrapper<UserInstitution>()
                    .eq("institution_id",institution.getInstitutionId()));
            InstitutionDTO institutionDTO = new InstitutionDTO(
                    institution.getInstitutionName(),institution.getAddress(),number);
            institutionDTOS.add(institutionDTO);
        }
        return Result.success(institutionDTOS);
    }

    @Override
    public Result<List<InstitutionDTO>> getInstitution() {
        List<Institution> institutions = list();
        return wrap(institutions);
    }

    @Override
    public Result<List<InstitutionDTO>> getByInstitutionName(String name) {
        QueryWrapper<Institution> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("institution_name",name);
        List<Institution> institutions = list(queryWrapper);
        return wrap(institutions);
    }

    @Override
    public Result<List<Institution>> select() {
        List<Institution> list = list();
        return Result.success(list);
    }

    @Override
    public Result<Object> updateInstitution(Institution institution) {
        QueryWrapper<Institution> queryWrapper = new QueryWrapper<>(institution);
        boolean update = update(queryWrapper);
        if(!update){
            return Result.fail("更新失败");
        }
        return Result.success();
    }

    @Override
    public Result<Object> insert(Institution institution) {
        boolean save = save(institution);
        if(!save){
            return Result.fail("组织审批不通过！");
        }
        return Result.success("组织审批成功，已添加至数据库！");
    }

    @Override
    public Result<Object> deleteById(int id) {
        boolean remove = removeById(id);
        if(!remove){
            return Result.fail("删除失败");
        }
        return Result.success();
    }

    @Override
    public Result<Object> ratifyInsert(Institution institution) {
        stringRedisTemplate.opsForZSet().add(CACHE_INSTITUTIONS_KEY, JSON.toJSONString(institution), System.currentTimeMillis());
        return Result.success("组织申请已提交，正在审批中！");
    }

    @Override
    public Result<Object> ratify() {
        Set<String> range = stringRedisTemplate.opsForZSet().range(CACHE_INSTITUTIONS_KEY, 0l, Long.MAX_VALUE);
        List<Institution> institutions = new ArrayList<>();
        for (String s : range) {
            Institution institution = JSON.parseObject(s, Institution.class);
            institutions.add(institution);
        }
        return Result.success(institutions);
    }

    @Override
    public Result<Object> ratifyFalse(Institution institution) {
        String jsonString = JSON.toJSONString(institution);
        stringRedisTemplate.opsForZSet().remove(CACHE_INSTITUTIONS_KEY, jsonString);
        return Result.fail("审批完成，已驳回！");
    }

}
