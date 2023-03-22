package com.volunteer.service.impl;

import com.volunteer.entity.User;
import com.volunteer.mapper.UserMapper;
import com.volunteer.service.UserService;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
