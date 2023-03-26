package com.volunteer.service;

import com.volunteer.common.Result;
import com.volunteer.entity.Activity;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ActivityService extends IService<Activity> {

    Result<List<Activity>> getActivity();

    Result<List<Activity>> getByAddress(String address);

    Result<List<Activity>> getByTheme(String theme);

    Result<List<Activity>> getByTip(String tip);

    Result<Object> insert(Activity activity);

    Result<Object> update(Activity activity);

    Result<Object> deleteById(int id);

    Result<Object> ratify();

    Result<Object> add(Activity activity);

    Result<Object> ratifyFalse(Activity activity);
}
