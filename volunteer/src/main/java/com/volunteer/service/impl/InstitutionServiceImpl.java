package com.volunteer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.volunteer.common.Result;
import com.volunteer.dto.InstitutionDTO;
import com.volunteer.entity.Institution;
import com.volunteer.entity.UserInstitution;
import com.volunteer.mapper.InstitutionMapper;
import com.volunteer.service.InstitutionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.volunteer.service.UserInstitutionService;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class InstitutionServiceImpl extends ServiceImpl<InstitutionMapper, Institution> implements InstitutionService {

    @Resource
    private UserInstitutionService userInstitutionService;

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
            return Result.fail("添加失败");
        }
        return Result.success();
    }

    @Override
    public Result<Object> deleteById(int id) {
        boolean remove = removeById(id);
        if(!remove){
            return Result.fail("删除失败");
        }
        return Result.success();
    }


}
