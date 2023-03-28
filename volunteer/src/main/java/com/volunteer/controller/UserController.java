package com.volunteer.controller;


import com.volunteer.common.Result;
import com.volunteer.dto.UserDTO;
import com.volunteer.entity.User;
import com.volunteer.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/{code}")
    public Result<Object> login(@PathVariable("code") String code){
        return userService.login(code);
    }

    //排行榜查询
    @GetMapping("/ranking")
    public Result<List<UserDTO>> getRanking(){
        return userService.getRanking();
    }

    //个人主页查询
    @GetMapping("/home")
    public Result<UserDTO> getHome(@RequestHeader String token){
        return userService.getHome(token);
    }

    //查看个人资料
    @GetMapping("/person")
    public Result<User> getPerson(@RequestHeader String token){
        return userService.getPerson(token);
    }

    //修改个人资料
    @PutMapping
    public Result<Object> updateUser(@RequestBody User user,@RequestHeader String token){
        return userService.updateUser(user,token);
    }
}
