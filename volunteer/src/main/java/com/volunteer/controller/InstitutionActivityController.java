package com.volunteer.controller;


import com.volunteer.common.Result;
import com.volunteer.dto.InstitutionActivityDTO;
import com.volunteer.dto.UserDTO;
import com.volunteer.service.ActivityService;
import com.volunteer.service.InstitutionActivityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/institution-activity")
public class InstitutionActivityController {

    @Resource
    private InstitutionActivityService institutionActivityService;

    @GetMapping("/{institution_id}")
    public Result<List<InstitutionActivityDTO>> getInstitutionActivity(@PathVariable("institution_id") Integer id){
        return institutionActivityService.getInstitutionActivity(id);
    }

    @GetMapping("/{activity_id}")
    public Result<List<UserDTO>> getUserActivity(@PathVariable("activity_id") Integer id){
        return institutionActivityService.getUserActivity(id);
    }

}
