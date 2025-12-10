package com.hmall.cart;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@MapperScan("com.hmall.cart.mapper")
@SpringBootApplication
@EnableDiscoveryClient // 启用注册和发现功能
@EnableFeignClients(basePackages = "com.hmall.api.client") // 启用Feign客户端
public class cartApplication {
    public static void main(String[] args) {
        SpringApplication.run(cartApplication.class, args);
    }
}