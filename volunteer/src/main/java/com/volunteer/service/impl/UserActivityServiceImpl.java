package com.volunteer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.volunteer.common.Result;
import com.volunteer.dto.UserDTO;
import com.volunteer.entity.Institution;
import com.volunteer.entity.User;
import com.volunteer.entity.UserActivity;
import com.volunteer.mapper.UserActivityMapper;
import com.volunteer.service.UserActivityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.volunteer.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserActivityServiceImpl extends ServiceImpl<UserActivityMapper, UserActivity> implements UserActivityService {

    @Resource
    private UserService userService;
    @Override
    public Result<List<UserDTO>> getUserActivity(Integer id) {
        List<UserActivity> list = list(new QueryWrapper<UserActivity>()
                        .eq("activity_id", id));
        List<String> list1 = new ArrayList<>();
        for (UserActivity userActivity : list) {
            list1.add(userActivity.getOpenid());
        }
        List<User> users = userService.listByIds(list1);
        List<UserDTO> res = new ArrayList<>();
        for (User user : users) {
            res.add(new UserDTO(user.getIdentifyName(),user.getTellphone(),user.getDeclaration()));
        }
        return Result.success(res);
    }

    @Override
    public Result<List<UserActivity>> select() {
        List<UserActivity> list = list();
        return Result.success(list);
    }

    @Override
    public Result<Object> updateUserActivity(UserActivity userActivity) {
        QueryWrapper<UserActivity> queryWrapper = new QueryWrapper<>(userActivity);
        boolean update = update(queryWrapper);
        if(!update){
            return Result.fail("更新失败");
        }
        return Result.success();
    }

    @Override
    public Result<Object> insert(UserActivity userActivity) {
        boolean save = save(userActivity);
        if(!save){
            return Result.fail("添加失败");
        }
        return Result.success();
    }

    @Override
    public Result<Object> deleteById(int id) {
        boolean remove = removeById(id);
        if(!remove){
            return Result.fail("删除失败");
        }
        return Result.success();
    }
}
