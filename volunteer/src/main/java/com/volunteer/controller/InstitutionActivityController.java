package com.volunteer.controller;


import com.volunteer.common.Result;
import com.volunteer.dto.ActivityDTO;
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

    //组织查看已发布的活动
    @GetMapping("/{institution_id}")
    public Result<List<ActivityDTO>> getInstitutionActivity(@PathVariable("institution_id") Integer id){
        return institutionActivityService.getInstitutionActivity(id);
    }


}
