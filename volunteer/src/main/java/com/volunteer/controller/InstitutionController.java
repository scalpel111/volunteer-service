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

    //后台删除
    @DeleteMapping("/back/{id}")
    public Result<Object> deleteById(@PathVariable("id") int id){
        return institutionService.deleteById(id);
    }

    //组织申请，存入redis
    @PutMapping("/ratifyInsert")
    public Result<Object> ratifyInsert(@RequestBody Institution institution){
        return institutionService.ratifyInsert(institution);
    }

    //web端进行审批，从redis中取出申请信息
    @GetMapping("/ratify")
    public Result<Object> ratify(){
        return institutionService.ratify();
    }

    //web端审批通过，加入数据库
    @PutMapping("/ratifyOk")
    public Result<Object> insert(@RequestBody Institution institution){
        return institutionService.insert(institution);
    }

    //web端审批不通过，从redis之中移除
    @DeleteMapping("/ratifyFalse")
    public Result<Object> ratifyFalse(@RequestBody Institution institution) {
        return institutionService.ratifyFalse(institution);
    }
}
