package com.volunteer.service;

import com.volunteer.common.Result;
import com.volunteer.dto.ActivityDTO;
import com.volunteer.entity.InstitutionActivity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface InstitutionActivityService extends IService<InstitutionActivity> {

    Result<List<ActivityDTO>> getInstitutionActivity(Integer id);
}
