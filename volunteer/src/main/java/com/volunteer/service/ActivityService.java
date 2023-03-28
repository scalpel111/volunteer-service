package com.volunteer.service;

import com.volunteer.common.Result;
import com.volunteer.dto.ActivityDTO;
import com.volunteer.entity.Activity;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ActivityService extends IService<Activity> {

    Result<List<Activity>> getActivity();

    Result<List<ActivityDTO>> getByAddress(String address);

    Result<List<ActivityDTO>> listByTheme(String theme);

    Result<List<ActivityDTO>> getByTip(String tip);

    Result<Object> update(Activity activity);

    Result<Object> deleteById(int id);

    Result<Activity> getByTheme(String theme);

    Result<Object> insert(Activity activity);

    Result<Object> ratify();

    Result<Object> add(Activity activity);

    Result<Object> ratifyFalse(Activity activity);

    Result<Object> ratifyUpdate();

//    Result<Object> updateOk(Activity activity);
}
