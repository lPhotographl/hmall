package com.hmall.api.config;

import com.hmall.api.fallback.itemClientFallback;
import com.hmall.common.utils.UserContext;
import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration; // 如果启动类上加了 defaultConfiguration，这里其实可以不加 @Configuration，但加了也没事

@Configuration
public class DefaultFeignConfig {

    // 3. 【新增】Feign 客户端 fallback 工厂 (处理服务调用失败)
    @Bean
    public itemClientFallback itemClientFallback() {
        return new itemClientFallback();
    }

    // 1. 保留你原来的日志配置 (调试用)
    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    // 2. 【新增】Feign 请求拦截器 (传递 UserID)
    @Bean
    public RequestInterceptor userInfoRequestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                // 从 ThreadLocal 获取用户 ID
                Long userId = UserContext.getUser();
                // 如果有 ID，就塞入 Header 传给下游微服务
                if (userId != null) {
                    template.header("user-info", userId.toString());
                }
            }
        };
    }
}