package com.volunteer.controller;


import com.volunteer.common.Result;
import com.volunteer.dto.InstitutionDTO;
import com.volunteer.entity.Institution;
import com.volunteer.service.InstitutionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/institution")
public class InstitutionController {

    @Resource
    private InstitutionService institutionService;

    //找组织全部查询
    @GetMapping
    public Result<List<InstitutionDTO>> getInstitution(){
        return institutionService.getInstitution();
    }

    //找组织搜索框查询
    @GetMapping("/{name}")
    public Result<List<InstitutionDTO>> getByInstitutionName(@PathVariable("name") String name){
        return institutionService.getByInstitutionName(name);
    }

    //后台查询
    @GetMapping("/back")
    public Result<List<Institution>> select(){
        return institutionService.select();
    }

    //后台修改
    @PostMapping("/back")
    public Result<Object> update(@RequestBody Institution institution){
        return institutionService.updateInstitution(institution);
    }

    //后台添加
    @PutMapping("/back")
    public Result<Object> insert(@RequestBody Institution institution){
        return institutionService.insert(institution);
    }

    //后台删除
    @DeleteMapping("/back/{id}")
    public Result<Object> deleteById(@PathVariable("id") int id){
        return institutionService.deleteById(id);
    }
}
