package com.hmall.gateway.fillter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
@Component
@Slf4j
public class MyGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = (ServerHttpRequest) exchange.getRequest();
        log.info("执行了 过滤器 ");
        log.info("{} method", request.getMethodValue());

        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            log.info("执行了 过滤器 后置");
        }));
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
