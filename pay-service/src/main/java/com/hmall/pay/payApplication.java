package com.hmall.pay;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.junit.jupiter.api.condition.EnabledIfSystemProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@MapperScan("com.hmall.pay.mapper")
@SpringBootApplication
@EnableDiscoveryClient // 启用注册和发现功能
@SentinelResource
@EnableFeignClients(basePackages = "com.hmall.api.client") // 启用Feign客户端
public class payApplication {
    public static void main(String[] args) {
        SpringApplication.run(payApplication.class, args);
    }
}
