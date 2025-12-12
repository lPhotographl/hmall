package com.hmall.user.config;

import cn.hutool.core.collection.CollUtil;
import com.hmall.common.utils.UserContext;
import com.hmall.user.config.AuthProperties;
import com.hmall.user.interceptor.LoginInterceptor;
import com.hmall.user.utils.JwtTool;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(AuthProperties.class)
public class MvcConfig implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 1. 注册一个全新的、极简的拦截器
        // 这个 LoginInterceptor 内部只做：getHeader("user-info") -> UserContext.set()
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**") // 拦截所有
                .excludePathPatterns(   // 【关键点】放行名单
                        "/users/login",    // 放行登录接口
                        "/users/register", // 放行注册接口
                        "/error",
                        "/favicon.ico",
                        "/swagger-resources/**",
                        "/webjars/**",
                        "/v3/**",
                        "/doc.html"
                ); // 仅放行极少数系统路径
    }


}
