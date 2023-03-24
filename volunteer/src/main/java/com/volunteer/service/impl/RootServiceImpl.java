package com.volunteer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.volunteer.common.Result;
import com.volunteer.entity.Root;
import com.volunteer.mapper.RootMapper;
import com.volunteer.service.RootService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RootServiceImpl extends ServiceImpl<RootMapper, Root> implements RootService {

    @Override
    public Result<Object> login(Root root) {
        Map<String,Object> map = new HashMap<>();
        map.put("admin",root.getAdmin());
        map.put("password",root.getPassowrd());
        QueryWrapper<Root> queryWrapper = new QueryWrapper<>();
        queryWrapper.allEq(map);
        Root root1 = getOne(queryWrapper);
        if(root1 == null){
            return Result.fail("登录失败");
        }
        return Result.success();
    }

    @Override
    public Result<Object> register(Root root) {
        Map<String,Object> map = new HashMap<>();
        map.put("admin",root.getAdmin());
        QueryWrapper<Root> queryWrapper = new QueryWrapper<>();
        queryWrapper.allEq(map);
        Root root1 = getOne(queryWrapper);
        if(root1 == null){
            return Result.fail("用户名已占用");
        }
        boolean save = save(root);
        if(!save){
            return Result.fail("注册失败");
        }
        return Result.success();
    }
}
