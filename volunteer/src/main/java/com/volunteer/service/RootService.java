package com.volunteer.service;

import com.volunteer.common.Result;
import com.volunteer.entity.Root;
import com.baomidou.mybatisplus.extension.service.IService;

public interface RootService extends IService<Root> {

    Result<Object> login(Root root);

    Result<Object> register(Root root);
}
