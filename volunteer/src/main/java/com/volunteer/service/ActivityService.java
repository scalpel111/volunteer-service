package com.volunteer.service;

import com.volunteer.common.Result;
import com.volunteer.dto.ActivityDTO;
import com.volunteer.entity.Activity;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ActivityService extends IService<Activity> {

    Result<List<Activity>> getActivity();

    Result<List<ActivityDTO>> getByAddress(String address);

    Result<List<ActivityDTO>> listByTheme(String theme);

    Result<List<ActivityDTO>> getByTip(String tip);

    Result<Object> update(Activity activity);

    Result<Object> deleteById(int id);

    Result<Activity> getByActivityId(Integer id);

    Result<Object> insert(Activity activity, Integer institutionId);

    Result<Object> ratify();

    Result<Object> add(Activity activity, Integer institutionId);

    Result<Object> ratifyFalse(Activity activity, Integer institutionId);

    Result<Object> ratifyUpdate();

//    Result<Object> updateOk(Activity activity);
}
