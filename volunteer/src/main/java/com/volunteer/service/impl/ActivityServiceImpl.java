package com.volunteer.service.impl;

import com.volunteer.entity.Activity;
import com.volunteer.mapper.ActivityMapper;
import com.volunteer.service.ActivityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 刘毅晨
 * @since 2023-03-22
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements ActivityService {

}
