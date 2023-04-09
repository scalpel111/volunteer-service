package com.volunteer.common;

import com.volunteer.utils.LoginInterceptor;
import com.volunteer.utils.RefreshTokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override       //添加拦截器
    public void addInterceptors(InterceptorRegistry registry) {
        // token刷新的拦截器
//        registry.addInterceptor(new RefreshTokenInterceptor(stringRedisTemplate))
//                .addPathPatterns("/**")
//                .order(0);
//        // 登录拦截器
//        registry.addInterceptor(new LoginInterceptor())
//                .excludePathPatterns(
//                        "/activity/**",
//                        "/institution-activity",
//                        "/institution/**",
//                        "/user-activity/**",
//                        "/user/**",
//                        "/user-institution/**"
//                ).order(1);
    }
}
