package com.volunteer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.volunteer.common.Result;
import com.volunteer.dto.InstitutionActivityDTO;
import com.volunteer.dto.UserDTO;
import com.volunteer.entity.Activity;
import com.volunteer.entity.InstitutionActivity;
import com.volunteer.entity.User;
import com.volunteer.entity.UserActivity;
import com.volunteer.mapper.InstitutionActivityMapper;
import com.volunteer.service.ActivityService;
import com.volunteer.service.InstitutionActivityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.volunteer.service.UserActivityService;
import com.volunteer.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class InstitutionActivityServiceImpl extends ServiceImpl<InstitutionActivityMapper, InstitutionActivity> implements InstitutionActivityService {

    @Resource
    private ActivityService activityService;

    @Override
    public Result<List<InstitutionActivityDTO>> getInstitutionActivity(Integer id) {

        List<InstitutionActivity> list = query().eq("institution_id",id).list();
        List<Integer> list1 = new ArrayList<>();
        for (InstitutionActivity institutionActivity : list) {
            list1.add(institutionActivity.getId());
        }
        List<Activity> activities = activityService.listByIds(list1);
        List<InstitutionActivityDTO> res = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            res.add(new InstitutionActivityDTO(activities.get(i).getTheme(),list.get(i)));
        }
        return Result.success(res);
    }


}
