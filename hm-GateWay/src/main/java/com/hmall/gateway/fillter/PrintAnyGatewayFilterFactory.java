package com.hmall.gateway.fillter;

import lombok.Data;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class PrintAnyGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

    @Override
    public GatewayFilter apply(Object config) {
        return new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                log.info("执行前置过滤器printAnyFilter...");
                return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                    log.info("执行后置过滤器printAnyFilter...");
                }));
            }
        };
    }

    @Data
    public static class config{
        private String a ;
        private String b ;
        private String c ;
    }

    @Override
    // 定义配置类的字段顺序，用于从配置文件中读取对应的值
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("a","b","c");
    }

    //把config类作为参数传递给父类，父类会根据shortcutFieldOrder方法返回的字段，
    // 从配置文件中读取对应的值，填充到config类中
    public PrintAnyGatewayFilterFactory() {
        super(Object.class);
    }

}
