package com.volunteer.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.volunteer.common.Result;
import com.volunteer.dto.ActivityDTO;
import com.volunteer.entity.*;
import com.volunteer.mapper.InstitutionActivityMapper;
import com.volunteer.service.ActivityService;
import com.volunteer.service.InstitutionActivityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class InstitutionActivityServiceImpl extends ServiceImpl<InstitutionActivityMapper, InstitutionActivity> implements InstitutionActivityService {

    @Resource
    private ActivityService activityService;


    @Override
    public Result<List<ActivityDTO>> getInstitutionActivity(Integer id) {

        List<InstitutionActivity> list = query().eq("institution_id",id).list();
        Set<Integer> set = new HashSet<>();
        for (InstitutionActivity institutionActivity : list) {
            set.add(institutionActivity.getActivityId());
        }
        List<Integer> ids = new ArrayList<>();
        for (Integer id1 : set) {
            ids.add(id1);
        }
        List<Activity> institutionActivities = activityService.listByIds(ids);
        List<ActivityDTO> res = new ArrayList<>();
        for (Activity activity : institutionActivities) {
            res.add(BeanUtil.copyProperties(activity,ActivityDTO.class));
        }
        return Result.success(res);
    }

}
